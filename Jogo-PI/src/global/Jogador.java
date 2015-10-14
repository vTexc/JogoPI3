package global;

import javax.swing.JLabel;

public class Jogador {
	private int recursos;
	private JLabel labelRecursos;
	private static Jogador instancia;
	
	private Jogador() {
		this.recursos = 0;
		this.labelRecursos = new JLabel();
	}
	
	public static Jogador getInstancia() {
		if(instancia == null) {
			initInstancia();
		}
		return instancia;
	}
	
	public static synchronized void initInstancia() {
		if(instancia == null) {
			instancia = new Jogador();
		}
	}
	
	public int getRecursos() {
		return recursos;
	}

	public void setRecursos(int recursos) {
		this.recursos = recursos;
	}
	
	public void addRecursos(int recursos) {
		this.recursos += recursos;
	}
	
	public void subRecursos(int recursos) {
		this.recursos -= recursos;
	}
	
	public void nullRecursos() {
		this.recursos = 0;
	}

	public JLabel getLabelRecursos() {
		this.labelRecursos.setText("Recursos : " + recursos);
		return labelRecursos;
	}

	public void setLabelRecursos(JLabel labelRecursos) {
		this.labelRecursos = labelRecursos;
	}
	
	public void setLabelBounds(int x, int y, int w, int h) {
		this.labelRecursos.setBounds(x, y, w, h);
	}
	
}
