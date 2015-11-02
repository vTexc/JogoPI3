package jogo;

import java.awt.*;
import java.util.*;

public class TorreTerrestre extends Torre {
	
	public TorreTerrestre(int x, int y) {
		super(x, y, 15, 1, 75, 1, Color.green);
	}
	
	public Monstro calculateRange(ArrayList<Monstro> monstros) {
		for(Monstro m: monstros) {
			int dx = (int) ((m.getPosicaoX() + m.getWidth()) - (this.getX() + this.getWidth()/2));
			int dy = (int) ((m.getPosicaoY() + m.getHeight()) - (this.getY() + this.getHeight()/2));
			
			if(Math.sqrt((dx*dx) + (dy*dy)) <= this.getRange()) {
				return m;
			}
		}
		return null;
	}
}
