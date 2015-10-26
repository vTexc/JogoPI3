/**
 * Classe que cria a janela do jogo (Não o painel de desenho, apenas a janela)
 * 
 * Informaçoes uteis:
 *	Linha "this.setTitle("Troia");" > "Troia" é o nome superior que aparece na janela
 * 		
 **/
package funcional;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Janela extends JFrame {
	//Objeto usado para desenhar na janela
	private Renderer renderer;
	
	//Construtor
	public Janela() {
		renderer = new Renderer();
		init();
	}
	
	//Inicializador
	public void init() {
		this.setTitle("Troia"); //Nome da janela
		this.setContentPane(renderer); //Seleciona o painel que carregara os compenentes
		
		this.setResizable(false); //Torna a janela imutavel, com tamanho fixo
		this.pack();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Clicar no "x" da janela encerra o programa
		this.setLocationRelativeTo(null); //Inicializa a janela no meio tela
	}
	
	//Classe principal que o java ira ler
	public static void main(String[] args) {
		//Thread para executar a janela
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame janela = new Janela();
				janela.setVisible(true);
			}
		});
	}
}
