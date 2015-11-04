package AEstrela;

public class Passo {
	private int x;
	private int y;

	public Passo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int hashCode() {
		return x * y;
	}

	public boolean equals(Object other) {
		if (other instanceof Passo) {
			Passo o = (Passo) other;

			return (o.x == x) && (o.y == y);
		}

		return false;
	}
}