package jogo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import global.HUD;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Mapa {
	private ArrayList<Torre> torres;
	private ArrayList<Monstro> monstros;

	public Mapa() {
		monstros = new ArrayList<Monstro>();
		torres = new ArrayList<Torre>();
	}
	
	public void reset() {
		monstros = new ArrayList<Monstro>();
		torres = new ArrayList<Torre>();
	}
	
	public void draw(Graphics g) {
	}
}
