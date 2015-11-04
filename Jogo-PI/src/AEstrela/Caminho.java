package AEstrela;

import java.util.ArrayList;

public class Caminho {
	private ArrayList<Passo> passos = new ArrayList<Passo>();

	public Caminho() {

	}
	
	public int getLength() {
		return passos.size();
	}
	
	public Passo getStep(int index) {
		return (Passo) passos.get(index);
	}

	public int getX(int index) {
		return passos.get(index).getX();
	}

	public int getY(int index) {
		return passos.get(index).getY();
	}

	public void appendStep(int x, int y) {
		passos.add(new Passo(x, y));
	}
	
	public void setSteps(ArrayList passos) {
		this.passos = passos;
	}
	
	public void prependStep(int x, int y) {
		passos.add(0, new Passo(x, y));
	}

	public boolean contains(int x, int y) {
		return passos.contains(new Passo(x, y));
	}
	
	public boolean isEmpty() {
		return passos.isEmpty();
	}
}
