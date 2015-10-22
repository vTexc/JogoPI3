package jogo;

import java.awt.Rectangle;

public class TorreTerrestre extends Torre {
	
	public TorreTerrestre(int x, int y, int k) {
		this.setImagem(new Rectangle(x, y, 50, 50));
		this.setArrayIndex(k);
	}

}
