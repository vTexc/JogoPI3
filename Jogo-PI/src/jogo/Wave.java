package jogo;

import java.util.*;

import funcional.Renderer;
import AEstrela.*;

public class Wave {
	private double spawn;
	private double maxSpawn;
	private int maxMonstros;
	private int qtdMonstros;
	
	private double tempoEspera;
	private double tempoAtual;
	
	private boolean gerar;
	private boolean hardcore;
	
	public Wave() {
		this.spawn = 1;
		this.maxSpawn = 1;
		this.maxMonstros = 5;
		this.qtdMonstros = 0;
		this.gerar = true;
		this.tempoEspera = 5;
		this.tempoAtual = 0;
	}
	
	public Wave(boolean hardcore) {
		this();
		this.hardcore = true;
		this.tempoEspera = 0;
	}
	
	public void update(ArrayList<Monstro> monstros, PathFinder finder, HUD hud) {
		if (gerar) {
			if (qtdMonstros < maxMonstros) {
				if (spawn < 1) {
					spawn += Renderer.deltaTime/1000;
				} else {
					qtdMonstros++;
					monstros.add(new MonstroTerrestre());
					monstros.get(monstros.size() - 1).atualizarCaminho(finder);
					spawn = 0;
				}
			} else {
				gerar = false;
			}
		} else {
			if(monstros.size() == 0) {
				if(tempoAtual < tempoEspera) {
					tempoAtual += Renderer.deltaTime/1000;
				} else {
					gerar = true;
					tempoAtual = 0;
					qtdMonstros = 0;
					maxMonstros++;
					hud.addWave();
				}
			}
		}
		
	}
}
