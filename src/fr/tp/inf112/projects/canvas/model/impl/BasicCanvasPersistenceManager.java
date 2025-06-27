package fr.tp.inf112.projects.canvas.model.impl;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import fr.tp.inf112.projects.canvas.view.FileCanvasChooser;
import fr.tp.inf112.projects.canvas.model.Canvas;

/**
 * A basic persistence manager that provides functionality to save, load, and delete
 * canvas data files. This class interacts with the file system to persist the state
 * of a {@link Canvas} object.
 * 
 * <p>
 * It uses {@link FileCanvasChooser} to generate file paths for saving and loading
 * canvas data. The class ensures that canvas data is serialized and deserialized
 * properly using Java's object streams.
 * </p>
 * 
 * <p>
 * This class extends {@link AbstractCanvasPersistenceManager}.
 * </p>
 * 
 * @author team-24
 */
public class BasicCanvasPersistenceManager extends AbstractCanvasPersistenceManager {

    /**
     * Constructs a new {@code BasicCanvasPersistenceManager} with a file chooser
     * configured for RobotSim Factory Data files.
     */
    public BasicCanvasPersistenceManager() {
        super(new FileCanvasChooser("rsim", "RobotSim Factory Data file"));
    }

    /**
     * Reads a {@link Canvas} object from a file specified by its ID.
     * 
     * @param canvasId The file path of the canvas to be read.
     * @return The deserialized {@link Canvas} object, or {@code null} if an error occurs.
     * @throws IOException If an I/O error occurs during file reading.
     */
    public Canvas read(String canvasId) throws IOException {
        try (FileInputStream fileIn = new FileInputStream(canvasId);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            return (Canvas) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Persists a {@link Canvas} object to a file. The file path is determined
     * using the {@link FileCanvasChooser}.
     * 
     * @param canvasModel The {@link Canvas} object to be saved.
     * @throws IOException If an I/O error occurs during file writing or if the save
     *                     operation is canceled by the user.
     */
    public void persist(Canvas canvasModel) throws IOException {
        String filePath = getCanvasChooser().newCanvasId();

        if (filePath == null) {
            throw new IOException("Canvas save operation cancelled by user.");
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(canvasModel);
            canvasModel.setId(filePath);
        }
    }

    /**
     * Deletes the file associated with the given {@link Canvas} object.
     * 
     * @param canvasModel The {@link Canvas} object whose associated file is to be deleted.
     * @return {@code true} if the file was successfully deleted, {@code false} if the file
     *         does not exist or the file path is {@code null}.
     * @throws IOException If the file exists but cannot be deleted.
     */
    public boolean delete(Canvas canvasModel) throws IOException {
        String filePath = canvasModel.getId();

        if (filePath == null) {
            return false;
        }

        File file = new File(filePath);

        if (!file.exists()) {
            return false;
        }

        if (file.delete()) {
            return true;
        } else {
            throw new IOException("Failed to delete canvas file: " + filePath);
        }
    }
}
