package jogo;

import java.util.*;

import funcional.Renderer;
import AEstrela.*;
import DecisionTree.DecisionTree;
import GameStates.PlayState;

public class Wave {
	// Tempo de spawn
	private double spawn;
	private double maxSpawn;

	// Quantidade de monstro
	private int maxMonstros;
	private int qtdMonstros;

	// Lista dos monstros
	private ArrayList<Monstro> aux = new ArrayList<Monstro>();;

	// Arvore de decisao
	private DecisionTree arvoreDecisao;

	// Tempo para alterar wave
	private static double tempoEspera;
	private static double tempoAtual;

	// Tempo decorrido da wave
	private double tempoDecorrido;

	/** Uso na arvore de decisao */
	// Tempo
	private double tempoTotal;

	// Vida atual e perdida
	private double vidaAtual;
	private double vidaPerdida;

	// Vida adicional pros monstros
	private int vidaAdicional;
	// Velocidade adicional pros montros
	private double speedAdicional;
	/**                      */
	
	// Verificadores de estados
	private static boolean spawnar;
	private static boolean gerar;
	private static boolean hardcore;

	// Construtor
	public Wave() {
		// Inicializando variaveis
		this.spawn = 1;
		this.maxSpawn = 1; // tempo entre spawns
		this.maxMonstros = 5; // Maximo de monstros
		this.qtdMonstros = 0;
		Wave.spawnar = false;
		Wave.gerar = false;
		Wave.tempoEspera = 5; // tempo de espera entre waves
		Wave.tempoAtual = 0;
		this.vidaAdicional = 0;
		this.speedAdicional = 0;
		this.vidaAtual = HUD.getInstancia().getVidas();
		this.vidaPerdida = 0;
	}

	// Construtor com hardcore
	public Wave(boolean hardcore) {
		this();
		Wave.hardcore = hardcore;
		if (Wave.hardcore)
			Wave.tempoEspera = 0;
	}

	// Retorna tempo atual
	public static double getTempoAtual() {
		return tempoAtual;
	}

	// Altera tempo atual
	public void setTempoAtual(double x) {
		Wave.tempoAtual = x;
	}

	// Retorna tempo de espera
	public static double getTempoEspera() {
		return tempoEspera;
	}

	// Retorna spawnar
	public static boolean getSpawnar() {
		return spawnar;
	}

	// Retorna hardcore
	public static boolean getHardcore() {
		return hardcore;
	}

	// Gera monstro aleatoriamente
	private Monstro gerarMonstro() {
		Random rand = new Random();
		int monstroType = rand.nextInt(3);
		Monstro monstro = null;

		switch (monstroType) {
		case 0:
			monstro = new MonstroTerrestre(vidaAdicional, speedAdicional);
			break;
		case 1:
			monstro = new MonstroVoador(vidaAdicional, speedAdicional);
			break;
		case 2:
			monstro = new MonstroDestruidor(rand.nextInt(5) + 3, vidaAdicional, speedAdicional);
			break;
		}

		return monstro;
	}

	// Atualiza wave
	public synchronized void update(ArrayList<Monstro> monstros, ArrayList<Torre> torres, PathFinder finder, HUD hud) {
		// Tempo passado na wave
		tempoDecorrido += Renderer.deltaTime;
		// Caso spawnar true
		if (spawnar) {
			// Caso gerar true
			if (HUD.getInstancia().getWave() == 1) {
				if (gerar) {
					for (int x = aux.size(); x < maxMonstros; x++) {
						aux.add(new MonstroTerrestre(vidaAdicional, speedAdicional));
					}

					tempoDecorrido = 0.0;
					qtdMonstros = 0;

					// Reseta lista dos monstros
					monstros = new ArrayList<Monstro>();

					// Cancela o gerar de monstros
					gerar = false;
				}
			} else {
				if (gerar) {
					// Gera x novos monstros em cima da lista antiga
					vidaAtual = HUD.getInstancia().getVidas();
					for (int x = aux.size(); x < maxMonstros; x++) {
						aux.add(gerarMonstro());
					}

					tempoDecorrido = 0.0;
					qtdMonstros = 0;

					// Reseta lista dos monstros
					monstros = new ArrayList<Monstro>();

					// Cancela o gerar de monstros
					gerar = false;
				}
			}

			// Enquanto tiver monstor para adicionar
			if (qtdMonstros < maxMonstros) {
				// verifica se o tempo de spawn ja passou
				if (spawn < maxSpawn) {
					spawn += Renderer.deltaTime * PlayState.gameSpeed;
				} else { // senão
					// Adiciona os monstors em ordem na lista de monstros
					monstros.add((Monstro) aux.get(qtdMonstros).clone());
					// Atualiza o caminho do monstro
					monstros.get(monstros.size() - 1).atualizarCaminho(finder);
					qtdMonstros++;
					spawn = 0;
				}
			} else {
				// Cancela o spawn de monstros
				spawnar = false;
			}
		} else { // Caso spawnar false
			// Caso monstros morreram
			if (monstros.size() == 0) {
				// Enquanto não acabou o tempo de espera
				if (tempoAtual < tempoEspera) {
					tempoAtual += Renderer.deltaTime;
				} else { // Quando passar o tempo
					hud.addWave();

					if (HUD.getInstancia().getWave() != 1) {
						vidaPerdida = 1 - ((double) HUD.getInstancia().getVidas() / vidaAtual);
						tempoTotal = (torres.size() / HUD.getInstancia().getWave()) * 100;

						// Reinicia arvore de decisão
						arvoreDecisao = new DecisionTree(vidaPerdida, tempoDecorrido / tempoTotal);

						// Ações da arvore
						switch (arvoreDecisao.queryBinTree()) {
						case 4: // Vida <= 40%
							maxMonstros += 1;
							for (Monstro m : aux) {
								m.addVida(0.04);
								m.addSpeedBase(m.getSpeedBase() * 0.05);
							}
							vidaAdicional += 0.04;
							speedAdicional += 0.05;
							break;
						case 5: // Vida > 40%
							for (Monstro m : aux) {
								m.addSpeedBase(m.getSpeedBase() * 0.02);
							}
							speedAdicional += 0.02;
							break;
						case 7: // Tempo > 50%
							maxMonstros += 2;
							for (Monstro m : aux) {
								m.addVida(0.05);
								m.addSpeedBase(m.getSpeedBase() * 0.01);
							}
							vidaAdicional += 0.05;
							speedAdicional += 0.01;
							break;
						case 8: // Tempo <= 25%
							maxMonstros += 3;
							for (Monstro m : aux) {
								m.addVida(0.1);
								m.addSpeedBase(m.getSpeedBase() * 0.05);
							}
							vidaAdicional += 0.1;
							speedAdicional += 0.05;
							break;
						case 9: // Tempo > 25%
							maxMonstros += 2;
							for (Monstro m : aux) {
								m.addVida(0.35);
								m.addSpeedBase(m.getSpeedBase() * 0.03);
							}
							vidaAdicional += 0.08;
							speedAdicional += 0.03;
							break;
						}
						// Cancela arvorede decisao
						arvoreDecisao = null;
					}
					// Reseta tempo
					tempoAtual = 0;

					// Ativa spawn e geração de monstro
					spawnar = true;
					gerar = true;
				}
			}
		}
	}
}
