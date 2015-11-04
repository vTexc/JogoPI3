package AEstrela;

import java.util.*;

import funcional.TileBasedMap;

public class AStarPathFinder implements PathFinder {
	// Conjunto de nós explorados
	private ArrayList fechado = new ArrayList();

	// Conjunto de nós parcilamente explorados
	private ListaOrdenada aberto = new ListaOrdenada();

	// Mapa base da procura
	private TileBasedMap mapa;

	// Maximo de tiles para buscar
	private int distanciaMaxima;

	// Nós no mapa
	private Node[][] nodes;

	// Permite diagonal ou não
	private boolean movimentoDiagonal;

	// Heuristica usada na A*
	private AStarHeuristic heuristica;

	// Construtor
	public AStarPathFinder(TileBasedMap mapa, int distanciaMaxima, boolean movimentoDiagonal) {
		this(mapa, distanciaMaxima, movimentoDiagonal, new ClosestHeuristic());
	}

	// Construtor
	public AStarPathFinder(TileBasedMap mapa, int distanciaMaxima, boolean movimentoDiagonal, AStarHeuristic heuristica) {
		this.heuristica = heuristica;
		this.mapa = mapa;
		this.distanciaMaxima = distanciaMaxima;
		this.movimentoDiagonal = movimentoDiagonal;

		nodes = new Node[mapa.getWidthInTiles()][mapa.getHeightInTiles()];
		for (int x = 0; x < mapa.getWidthInTiles(); x++) {
			for (int y = 0; y < mapa.getHeightInTiles(); y++) {
				nodes[x][y] = new Node(x, y);
			}
		}
	}

	/**
	 * @see PathFinder#findPath(Mover, int, int, int, int)
	 */
	public Caminho findPath(int mover, int sx, int sy, int tx, int ty) {
		// Verifica se o ponto final esta bloqueado ou não
		if (mapa.blocked(mover, tx, ty)) {
			return null;
		}
		// Inicializa variaveis para A*
		nodes[sx][sy].custo = 0;
		nodes[sx][sy].profundidade = 0;
		fechado.clear();
		aberto.limpa();
		aberto.add(nodes[sx][sy]);

		nodes[tx][ty].parente = null;

		// Enquanto não terminou a profundidade de busca
		int profundidadeMaxima = 0;
		while ((profundidadeMaxima < distanciaMaxima) && (aberto.size() != 0)) {

			Node current = getFirstInOpen();
			if (current == nodes[tx][ty]) {
				break;
			}

			removeFromOpen(current);
			addToClosed(current);
			// Verifica todos os vizinhos do no atual
			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					// Se for nó atual
					if ((x == 0) && (y == 0)) {
						continue;
					}
					// Verifica se diagonal esta permitida
					if (!movimentoDiagonal) {
						if ((x != 0) && (y != 0)) {
							continue;
						}
					}
					// Determina a localização do vizinho e o avalia
					int xp = x + current.x;
					int yp = y + current.y;

					if (isValidLocation(mover, sx, sy, xp, yp)) {
						// Custo de movimento para este node
						float nextStepCost = current.custo + getMovementCost(mover, current.x, current.y, xp, yp);
						Node neighbour = nodes[xp][yp];
						mapa.pathFinderVisited(xp, yp);
						// Caso o custo seja menor
						if (nextStepCost < neighbour.custo) {
							if (inOpenList(neighbour)) {
								removeFromOpen(neighbour);
							}
							if (inClosedList(neighbour)) {
								removeFromClosed(neighbour);
							}
						}
						// Adiciona node na lista aberta caso não tenha sido
						// descartado
						if (!inOpenList(neighbour) && !(inClosedList(neighbour))) {
							neighbour.custo = nextStepCost;
							neighbour.heuristica = getHeuristicCost(mover, xp, yp, tx, ty);
							profundidadeMaxima = Math.max(profundidadeMaxima, neighbour.setParente(current));
							addToOpen(neighbour);
						}
					}
				}
			}
		}
		// Caso o final não possua parente, retorna null ( não existe caminho)
		if (nodes[tx][ty].parente == null) {
			return null;
		}
		// Cria o caminho a partir dos nodes encontrados
		Caminho path = new Caminho();
		Node target = nodes[tx][ty];
		while (target != nodes[sx][sy]) {
			path.prependStep(target.x, target.y);
			target = target.parente;
		}
		path.prependStep(sx, sy);

		return path;
	}

	// Retorna o primeiro elemento da lista aberta
	protected Node getFirstInOpen() {
		return (Node) aberto.inicio();
	}

	// Adiciona um nó à lista aberta
	protected void addToOpen(Node node) {
		aberto.add(node);
	}

	// Verifica se tal nó esta na lista aberta
	protected boolean inOpenList(Node node) {
		return aberto.contains(node);
	}

	// Remove o nó da lista aberta
	protected void removeFromOpen(Node node) {
		aberto.remove(node);
	}

	// Adiciona nó à lista fechada
	protected void addToClosed(Node node) {
		fechado.add(node);
	}

	// Verifica se tal nó esta na lista fechada
	protected boolean inClosedList(Node node) {
		return fechado.contains(node);
	}

	// Remove tal nó da lista fechada
	protected void removeFromClosed(Node node) {
		fechado.remove(node);
	}

	// Verifica se a localição é valida, a partir do tipo do monstro
	protected boolean isValidLocation(int mover, int sx, int sy, int x, int y) {
		boolean invalid = (x < 0) || (y < 0) || (x >= mapa.getWidthInTiles()) || (y >= mapa.getHeightInTiles());

		if ((!invalid) && ((sx != x) || (sy != y))) {
			invalid = mapa.blocked(mover, x, y);
		}

		return !invalid;
	}

	// Retorna o custo para dada localização
	public float getMovementCost(int mover, int sx, int sy, int tx, int ty) {
		return mapa.getCost(mover, sx, sy, tx, ty);
	}

	// Retorna o custo da heuristica para detemrinada localização
	public float getHeuristicCost(int mover, int x, int y, int tx, int ty) {
		return heuristica.getCost(mapa, mover, x, y, tx, ty);
	}

	// Lista Ordenada
	private class ListaOrdenada {
		// Lista de elementos
		private ArrayList lista = new ArrayList();

		// Retorna o primeiro elemntos
		public Object inicio() {
			return lista.get(0);
		}

		// Esvazia lista
		public void limpa() {
			lista.clear();
		}

		// Adiciona um elemento da lista, mantendo ordenada
		public void add(Object o) {
			try {
				synchronized (lista) {
					lista.add(o);
					Collections.sort(lista);
				}
			} catch (NullPointerException e) {
				return;
			}
		}

		// Remove um elemento da lista
		public void remove(Object o) {
			lista.remove(o);
		}

		// Retorna quantidade de lementos na lista
		public int size() {
			return lista.size();
		}

		// Verifica se elemento exist ena lista
		public boolean contains(Object o) {
			return lista.contains(o);
		}
	}

	// Classe Nó
	private class Node implements Comparable {
		// Posição na matriz
		private int x;
		private int y;
		
		// Custo do caminho para este nó
		private float custo;
		
		// Parente deste nó (encontrado na busca)
		private Node parente;
		
		// Custo da heuristica deste nó
		private float heuristica;
		
		// Profundidade do nó
		private int profundidade;

		// Construtor
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		// Seta o parente do nó
		public int setParente(Node parente) {
			profundidade = parente.profundidade + 1;
			this.parente = parente;

			return profundidade;
		}

		// Comparador
		// Verifica se o custo do no atual é ou não maior que dado Nó
		public int compareTo(Object other) {
			Node o = (Node) other;

			float f = heuristica + custo;
			float of = o.heuristica + o.custo;

			if (f < of) {
				return -1;
			} else if (f > of) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
