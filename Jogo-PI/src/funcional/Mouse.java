package funcional;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface Mouse extends MouseListener, MouseMotionListener {
	
	@Override
	public default void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("Arastou");
	}

	@Override
	public default void mouseMoved(MouseEvent e) {
//		System.out.println(e.getX() + " " + e.getY());
	}

	@Override
	public default void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("Clicou");
	}

	@Override
	public default void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("Pressionou");
	}

	@Override
	public default void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("Soltou");
	}

	@Override
	public default void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("Entrou");
	}

	@Override
	public default void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("Saiu");
	}

}
