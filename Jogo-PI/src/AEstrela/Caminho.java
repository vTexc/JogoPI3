package AEstrela;

import java.util.ArrayList;

public class Caminho {
	// Lista dos passos a ser dado
	private ArrayList<Passo> passos;

	// Construtor
	public Caminho() {
		passos = new ArrayList<Passo>();
	}

	// Retorna tamanho da lista
	public int getLength() {
		return passos.size();
	}

	// Retorna passo em determinado lugar da lista
	public Passo getStep(int index) {
		return (Passo) passos.get(index);
	}

	// Retorna x de determinado passo
	public int getX(int index) {
		return passos.get(index).getX();
	}

	// Retorna y de determinado passo
	public int getY(int index) {
		return passos.get(index).getY();
	}

	// Insere um passo no final da lista
	public void appendStep(int x, int y) {
		passos.add(new Passo(x, y));
	}

	// Seta nova lista de passos
	public void setSteps(ArrayList passos) {
		this.passos = passos;
	}

	// Insere um passo no começo da lista
	public void prependStep(int x, int y) {
		passos.add(0, new Passo(x, y));
	}

	// Verifica se a lista contem tal passo
	public boolean contains(int x, int y) {
		return passos.contains(new Passo(x, y));
	}

	// Verifica se lista esta vazia
	public boolean isEmpty() {
		return passos.isEmpty();
	}
}
