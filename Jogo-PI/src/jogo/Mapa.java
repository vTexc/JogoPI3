package jogo;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

import javax.imageio.ImageIO;

public class Mapa {
	private int[][] mapa;
	private BufferedImage bg;
	
	private ArrayList<Torre> torres;
	private ArrayList<Monstro> monstros;

	public Mapa() {
		mapa = new int[13][19];
		monstros = new ArrayList<Monstro>();
		torres = new ArrayList<Torre>();
	}
	
	public void loadImage() {
	}
	
	public void reset() {
		monstros.clear();
		torres.clear();
	}
	
	public void draw(Graphics g) {
	}
}
