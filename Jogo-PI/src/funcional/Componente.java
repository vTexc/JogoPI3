package funcional;

import java.awt.*;
import java.awt.geom.*;

public abstract class Componente {
	// Posição do componente
	private double x, y;
	
	// Altura e largura do componente
	private int width, height;
	
	// Constructors
	public Componente() {
		
	}
	
	public Componente (int w, int h) {
		setWidth(w);
		setHeigth(h);
	}
	
	// Seta valor para x
	public void setX(int x) {
		this.x = x;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void addX(double x) {
		this.x += x;
	}
	
	// Retorna valor de x
	public int getX() {
		return (int) x;
	}
	
	public double getXDouble() {
		return x;
	}
	
	// Seta valor para y
	public void setY(int y) {
		this.y = y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void addY(double y) {
		this.y += y;
	}
	
	// Retorna valor de x
	public int getY() {
		return (int) y;
	}

	public double getYDouble() {
		return y;
	}
	
	// Set largura
	public void setWidth(int width) { 
		this.width = width;
	}
	
	// Retorna largura
	public int getWidth() { 
		return width;
	}

	// Seta altura
	public void setHeigth(int height) { 
		this.height = height;
	}

	// Retorna altura
	public int getHeight() { 
		return height;
	}
	
	// Verifica se tal ponto esta dentro deste componente
	public boolean contains(int x, int y) {
		return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
	}
	
	public boolean contains(Point p) {
		return (p.x >= this.x && p.x <= this.x + this.width && p.y >= this.y && p.y <= this.y + this.height);
	}

	// Set bounds
	public void setBounds(Shape shape) {
		this.x = shape.getBounds().getX();
		this.y = shape.getBounds().getY();
		this.width = (int) shape.getBounds().getWidth();
		this.height = (int) shape.getBounds().getHeight();
	}
	
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	// Retorna caixa de colisão
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}
	
	// Verifica interseção
	public boolean intersects(Shape shape) {
		return (new Rectangle((int) x, (int) y, width, height).intersects(shape.getBounds2D()));
	}
}
