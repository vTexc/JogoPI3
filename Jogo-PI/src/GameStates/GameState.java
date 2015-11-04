/**
 * Interface definindo os metodos para os estados do jogo
 */
package GameStates;

import java.awt.event.MouseEvent;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void mouseDragged(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
	public abstract void mouseClicked(MouseEvent e);
	public abstract void mousePressed(MouseEvent e);
	public abstract void mouseReleased(MouseEvent e);
	public abstract void mouseEntered(MouseEvent e);
	public abstract void mouseExited(MouseEvent e);
}
