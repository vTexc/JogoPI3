package AEstrela;

import java.util.ArrayList;

public class Path {
	private ArrayList<Step> steps = new ArrayList<Step>();

	public Path() {

	}
	
	public int getLength() {
		return steps.size();
	}
	
	public Step getStep(int index) {
		return (Step) steps.get(index);
	}

	public int getX(int index) {
		return steps.get(index).getX();
	}

	public int getY(int index) {
		return steps.get(index).getY();
	}

	public void appendStep(int x, int y) {
		steps.add(new Step(x, y));
	}
	
	public void setSteps(ArrayList steps) {
		this.steps = steps;
	}
	
	public void prependStep(int x, int y) {
		steps.add(0, new Step(x, y));
	}

	public boolean contains(int x, int y) {
		return steps.contains(new Step(x, y));
	}
	
	public boolean isEmpty() {
		return steps.isEmpty();
	}
}
