package jogo;

import global.HUD;

import javax.swing.JLabel;

public class Menu {
	private static Menu instancia;
	
	private Menu() {
	}
	
	public static Menu getInstancia() {
		if(instancia == null) {
			initInstancia();
		}
		return instancia;
	}
	
	public static synchronized void initInstancia() {
		if(instancia == null) {
			instancia = new Menu();
		}
	}
}
