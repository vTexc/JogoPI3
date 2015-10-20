package global;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Janela extends JFrame {
	private Renderer renderer;
	
	public Janela() {
		renderer = new Renderer();
		init();
	}
	
	public void init() {
		this.add(renderer);
		this.addMouseListener(renderer);
		this.addMouseMotionListener(renderer);
		this.addComponentListener(renderer);
		
		this.setResizable(true);
		this.pack();
		
		this.setTitle("Troia");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame janela = new Janela();
				janela.setVisible(true);
			}
		});
	}
}
