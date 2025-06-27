package fr.tp.inf112.projects.canvas.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.tp.inf112.projects.canvas.controller.CanvasViewerController;
import fr.tp.inf112.projects.canvas.controller.Observer;
import fr.tp.inf112.projects.canvas.model.Canvas;
import fr.tp.inf112.projects.canvas.model.CanvasChooser;
import fr.tp.inf112.projects.canvas.model.CanvasPersistenceManager;

@SuppressWarnings("serial")
public class CanvasViewer extends JFrame implements Observer {

    private static final Logger LOGGER = Logger.getLogger(CanvasViewer.class.getName());

    private final CanvasViewerController controller;

    private final Canvas canvasModel;

    private final CanvasPanel canvasPanel;

    private boolean modified;

    public CanvasViewer(final Canvas canvasModel) {
        this(null, canvasModel);
    }

    public CanvasViewer(final CanvasViewerController controller) {
        this(controller, null);
    }

    private CanvasViewer(final CanvasViewerController controller,
                        final Canvas canvasModel) {
        super();

        this.canvasModel = canvasModel;
        this.controller = controller;

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(final WindowEvent event) {
                if (quit()) {
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
                else {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        } );

        setJMenuBar(new CanvasViewerMenuBar(this));

        canvasPanel = new CanvasPanel(this);
        setContentPane(canvasPanel);

        initCanvasModel(getCanvasModel());

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double screenWidth = screenSize.getWidth();
        final double screenHeight = screenSize.getHeight();
        final int windowWidth = (int) (screenWidth / 1.2);
        final int windowHeight = (int) (screenHeight / 1.2);
        setPreferredSize(new Dimension(windowWidth, windowHeight ));
        setLocation((int) ((screenWidth - windowWidth)/2.0), (int) ((screenHeight - windowHeight) / 2.0));

        pack();

        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);

        setVisible(true);
    }

    public Canvas getCanvasModel() {
        return controller == null ? canvasModel : controller.getCanvas();
    }

    @Override
    public void modelChanged() {
        setModified(true);

        getJMenuBar().repaint();
        canvasPanel.repaint();
    }

    public void startAnimation() {
        if (checkController("animated")) {
            new ControllerWorker(this, "starting animation") {

                @Override
                public Short doInBackground() {
                    controller.startAnimation();

                    return 0;
                }
            }.execute();
        }
    }

    public void stopAnimation() {
        if (checkController("stopped")) {
            new ControllerWorker(this, "stopping animation") {

                @Override
                public Short doInBackground() {
                    controller.stopAnimation();

                    return 0;
                }
            }.execute();
        }
    }

    public boolean isAnimationRunning() {
        if (controller == null) {
            return false;
        }

        return controller.isAnimationRunning();
    }

    public boolean quit() {
        if (isModified()) {
            final int response = JOptionPane.showOptionDialog(this,
                                                              "Canvas not saved. Save it ?",
                                                              "Quit Application", 
                                                              JOptionPane.YES_NO_CANCEL_OPTION,
                                                              JOptionPane.WARNING_MESSAGE,
                                                              null,
                                                              null,
                                                              null);
            switch (response) {
                case JOptionPane.CANCEL_OPTION:
                    return false;
                case JOptionPane.OK_OPTION:
                    saveCanvas(false);
                    break;
                case JOptionPane.NO_OPTION:
                    break;
            }
        }

        dispose();
        return true;
    }

    public void openCanvas() {
        if (checkController("opened")) {
            final CanvasPersistenceManager persistenceManager = controller.getPersistenceManager();
            new ControllerWorker(this, "opening canvas") {
                @Override
                public Short doInBackground()
                throws IOException {
                    final String chosenCanvasId = persistenceManager.getCanvasChooser().choseCanvas();
                    if (chosenCanvasId != null)  {
                        initCanvasModel(persistenceManager.read(chosenCanvasId));
                    }
                    return 0;
                }
            }.execute();
        }
    }
    private void initCanvasModel(final Canvas canvasModel) {
        if (controller != null) {
            controller.removeObserver(this);
            controller.setCanvas(canvasModel);
            controller.addObserver(this);
        }
        //canvasPanel.setCanvasViewer(this);
        setTitle("Viewing " + (canvasModel == null ? " empty canvas" : canvasModel.getName()));
        modelChanged();
        setModified(false);
    }
    public void saveCanvas(final boolean runInBackground) {
        if (checkController("saved")) {
            final Canvas canvasModel = controller.getCanvas();
            if (canvasModel.getId() == null) {
                final CanvasChooser chooser = controller.getPersistenceManager().getCanvasChooser();
                try {
                    final String canvasId = chooser.newCanvasId();
                    if (canvasId != null) {
                        canvasModel.setId(canvasId);
                    }
                }
                catch (final IOException ex) {
                    final String message = "A problem occur while computing a new identifier for the canvas to be saved.";
                    LOGGER.log(Level.SEVERE, message, ex);
                    JOptionPane.showMessageDialog(this, message + " Please contact support.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (canvasModel.getId() != null) {
                final ControllerWorker worker = new ControllerWorker(this, "saving canvas") {
                    @Override
                    public Short doInBackground() 
                    throws IOException {
                        controller.getPersistenceManager().persist(controller.getCanvas());
                        setModified(false);
                        return 0;
                    }
                };
                if (runInBackground) {
                    worker.execute();
                }
                else {
                    worker.run();
                    setModified(false);
                }
            }
        }
    }
    private boolean checkController(final String sourceAction) {
        if (controller == null) {
            JOptionPane.showMessageDialog(this, "No controller has been set. The canvas cannot be " + sourceAction + ".");
            return false;
        }
        return true;
    }

    @Override
    public void dispose() {
        if (controller != null) {
            controller.removeObserver(this);
        }
        super.dispose();
    }
    public boolean canControlModel() {
        return controller != null && controller.getCanvas() != null;
    }
    public boolean canSaveCanvas() {
        return controller != null && controller.getCanvas() != null && (isModified() || controller.getCanvas().getId() == null);
    }
    public boolean canOpenCanvas() {
        return controller != null && controller.getPersistenceManager() != null && controller.getPersistenceManager().getCanvasChooser() != null;
    }

    public boolean isModified() {
        return canControlModel() && modified;
    }
    private void setModified(final boolean modified) {
        this.modified = modified;
    }
}
