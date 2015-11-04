/**
 * Gerenciador de estados do jogo Controla qual a tela atual a ser manipulada.
 * 
 * Metodos: Construtor - Inicializa a classe LoadState - Inicializa um estado de
 * jogo determinado UnloadState - Descarta determinado estado de jogo SetState -
 * Define o estado de jogo atual Update - Atualiza as informaçoes do estado
 * atual Draw - Desenha o estado atual
 */
package GameStates;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameStateManager {
	// Gerenciar os estados
	private GameState[] gameStates;
	private int currentState;
	private boolean hc;

	// Numero maximo de estados
	public static final int NUMSTATES = 4;
	// Define todos os estados que o jogo pode ter
	public static final int MENU = 0;
	public static final int OPTIONS = 1;
	public static final int CREDITS = 2;
	public static final int PLAY = 3;

	// Inicializa o gerenciador, selecionando um estado como inicial
	public GameStateManager() {
		gameStates = new GameState[NUMSTATES];

		currentState = MENU;
		loadState(currentState);
	}

	// Inicia determinado estado
	private void loadState(int state) {
		if (state == MENU)
			gameStates[state] = new MenuState(this);
		if (state == OPTIONS)
			gameStates[state] = new OptionState(this);
		if (state == CREDITS)
			gameStates[state] = new CreditState(this);
		if (state == PLAY)
			gameStates[state] = new PlayState(this, this.hc);
	}

	// Descarrega determinado estado
	private void unloadState(int state) {
		gameStates[state] = null;
	}

	// Seleciona determinado estado
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	// Seleciona determinado estado passando um boolean
	public void setState(int state, boolean hc) {
		this.hc = hc;
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	// Atualiza o estado atual
	public void update() {
		try {
			gameStates[currentState].update();
		} catch (Exception e) {
		}
	}

	// Desenha o estado atual
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch (Exception e) {
		}
	}

	/** Listeners Overrides **/
	public void mouseDragged(MouseEvent e) {
		gameStates[currentState].mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) {
		gameStates[currentState].mouseMoved(e);
	}

	public void mouseClicked(MouseEvent e) {
		gameStates[currentState].mouseClicked(e);
	}

	public void mousePressed(MouseEvent e) {
		gameStates[currentState].mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		gameStates[currentState].mouseReleased(e);
	}

	public void mouseEntered(MouseEvent e) {
		gameStates[currentState].mouseEntered(e);
	}

	public void mouseExited(MouseEvent e) {
		gameStates[currentState].mouseExited(e);
	}
}