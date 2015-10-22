package GameStates;

import java.util.ArrayList;

public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;

	public static final int NUMSTATES = 4;
	public static final int MENU = 0;
	public static final int OPTIONS = 1;
	public static final int CREDITS = 2;
	public static final int PLAY = 3;

	public GameStateManager() {
		gameStates = new GameState[NUMSTATES];

		currentState = PLAY;
		loadState(currentState);

	}

	private void loadState(int state) {
		if(state == MENU)
			gameStates[state] = new MenuState(this);
		if(state == OPTIONS)
			gameStates[state] = new OptionState(this);
		if(state == CREDITS)
			gameStates[state] = new CreditState(this);
		if(state == PLAY)
			gameStates[state] = new PlayState(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//gameStates[currentState].init();
	}
	
	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
	public void mouseClicked(int k, int x, int y) {
		gameStates[currentState].mouseClicked(k, x, y);
	}
}