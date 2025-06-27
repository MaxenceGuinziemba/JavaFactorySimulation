package fr.tp.inf112.projects.canvas.view;

import javax.swing.JMenu;

@SuppressWarnings("serial")
public class AnimateMenu extends JMenu {
	
	public AnimateMenu(final CanvasViewer canvasViewer) {
		super("Animation");

		add(new StartAnimationMenuItem(canvasViewer));
		add(new StopAnimationMenuItem(canvasViewer));
	}
	
	@Override
	public void repaint() {
		super.repaint();
		
		for (int index = 0; index < getItemCount(); index++) {
			getItem( index ).repaint();
		}
	}
}
