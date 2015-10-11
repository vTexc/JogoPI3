package global;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Janela extends JFrame {
	private JTextField txt_nome = new JTextField(20);
	private Renderer renderer;
	
	public Janela() {
		renderer = new Renderer();
		init();
	}
	
	public void init() {
		this.add(renderer);
		this.addMouseListener(renderer);
		this.addMouseMotionListener(renderer);
		
		this.setResizable(true);
		this.pack();
		
		this.setTitle("Jogo PI");
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
