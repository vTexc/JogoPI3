/**
 * Interface para juntar as interfaces MouseListener e MouseMotionListener
 * Tem como maior finalidade o debug do programa em tempo real
 **/
package funcional;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface Mouse extends MouseListener, MouseMotionListener {

	public default void mouseDragged(MouseEvent e) {
//		System.out.println("Arastou");
	}

	public default void mouseMoved(MouseEvent e) {
//		System.out.println(e.getX() + " " + e.getY());
	}

	public default void mouseClicked(MouseEvent e) {
//		System.out.println("Clicou");
	}

	public default void mousePressed(MouseEvent e) {
//		System.out.println("Pressionou");
	}

	public default void mouseReleased(MouseEvent e) {
//		System.out.println("Soltou");
	}

	public default void mouseEntered(MouseEvent e) {
//		System.out.println("Entrou");
	}

	public default void mouseExited(MouseEvent e) {
//		System.out.println("Saiu");
	}

}
