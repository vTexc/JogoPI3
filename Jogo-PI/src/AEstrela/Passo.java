package AEstrela;

public class Passo {
	// X e Y na matriz
	private int x;
	private int y;

	// Construtor
	public Passo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	// Retorna x
	public int getX() {
		return x;
	}

	// Retorna y
	public int getY() {
		return y;
	}

	// Retorna hashCode
	public int hashCode() {
		return x * y;
	}

	// Verifica se eh igual a determinado objeto
	public boolean equals(Object other) {
		if (other instanceof Passo) {
			Passo o = (Passo) other;

			return (o.x == x) && (o.y == y);
		}

		return false;
	}
}