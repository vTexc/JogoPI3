package global;

import javax.swing.JLabel;

public class HUD {
	private int recursos;
	private JLabel labelRecursos;
	private static HUD instancia;
	private int wave;
	private JLabel labelWave;
	
	private HUD() {
		this.wave = 1;
		this.labelWave = new JLabel("Wave : " + wave);
		this.recursos = 0;
		this.labelRecursos = new JLabel("Recursos : " + recursos);
	}
	
	public static HUD getInstancia() {
		if(instancia == null) {
			initInstancia();
		}
		return instancia;
	}
	
	public static synchronized void initInstancia() {
		if(instancia == null) {
			instancia = new HUD();
		}
	}
	
	public int getRecursos() {
		return recursos;
	}

	public void setRecursos(int recursos) {
		this.recursos = recursos;
		this.labelRecursos.setText("Recursos : " + recursos);
	}
	
	public void addRecursos(int recursos) {
		this.recursos += recursos;
		this.labelRecursos.setText("Recursos : " + recursos);
	}
	
	public void subRecursos(int recursos) {
		this.recursos -= recursos;
		this.labelRecursos.setText("Recursos : " + recursos);
	}
	
	public void nullRecursos() {
		this.recursos = 0;
	}
	
	public int getWave() {
		return wave;
	}

	public void setWave(int wave) {
		this.wave = wave;
		this.labelWave.setText("Wave : " + wave);
	}

	public JLabel getLabelRecursos() {
		return labelRecursos;
	}
	
	public JLabel getLabelWave() {
		return labelWave;
	}
	
	public void setLabelRecursosBounds(int x, int y, int w, int h) {
		this.labelRecursos.setBounds(x, y, w, h);
	}
			
	public void setLabelWaveBounds(int x, int y, int w, int h) {
		this.labelWave.setBounds(x, y, w, h);
	}
}
