package DecisionTree;

public class DecisionTree {
	// Raiz da arvore
	private BinTree raiz = null;
	
	// Variaveis para uso
	private double vidaPerdida;
	private double tempoDecorrido;

	// Construtor
	public DecisionTree(double vida, double tempo) {
		this.vidaPerdida = vida;
		this.tempoDecorrido = tempo;

		// Perguntas
		String perguntaVida1 = (vidaPerdida > 0.0) ? "Yes" : "No";
		String perguntaVida2 = (vidaPerdida <= 0.4) ? "Yes" : "No";
		String perguntaTempo1 = (tempoDecorrido <= 0.5) ? "Yes" : "No";
		String perguntaTempo2 = (tempoDecorrido <= 0.25) ? "Yes" : "No";
		
		// Cria arvore
		init(1, perguntaVida1);
		addTrueNode(1, 2, perguntaVida2);
		addFalseNode(1, 3, perguntaTempo1);
		addTrueNode(2, 4, "Vida <= 40");
		addFalseNode(2, 5, "Vida > 40");
		addTrueNode(3, 6, perguntaTempo2);
		addFalseNode(3, 7, "Tempo > 50%");
		addTrueNode(6, 8, "Tempo <= 25%");
		addFalseNode(6, 9, "Tempo > 25%");
	}

	// Inicia arvore
	public void init(int novoNoID, String pergunta) {
		raiz = new BinTree(novoNoID, pergunta);
	}

	// Adiciona nó no lado true
	public void addTrueNode(int noIDExistente, int novoNoID, String pergunta) {
		if (raiz != null) {
			if (procurarLugarTrueArvore(raiz, noIDExistente, novoNoID, pergunta)) {
			} else {

			}
		}
	}

	// Procura o ultimo lugar true disponivel
	private boolean procurarLugarTrueArvore(BinTree noAtual, int noIDExistente, int novoNoID, String pergunta) {
		if (noAtual.nodeID == noIDExistente) {
			// Encontrou o nó
			if (noAtual.trueArvore == null)
				noAtual.trueArvore = new BinTree(novoNoID, pergunta);
			else {
			}
			return true;
		} else {
			// Tentar arvore "true"
			if (noAtual.trueArvore != null) {
				if (procurarLugarTrueArvore(noAtual.trueArvore, noIDExistente, novoNoID, pergunta)) {
					return (true);
				} else {
					// Tentar arvore "false"
					if (noAtual.falseArvore != null) {
						return (procurarLugarTrueArvore(noAtual.falseArvore, noIDExistente, novoNoID, pergunta));
					} else
						return false; // Não encontrou aqui
				}
			}
			return false; // Não encontrou aqui
		}
	}

	// Adiciona nó no lado false
	public void addFalseNode(int noIDExistente, int novoNoID, String pergunta) {
		if (raiz != null) {
			if (procurarLugarFalseArvore(raiz, noIDExistente, novoNoID, pergunta)) {
			} else {
			}
		}
	}

	// Procura o ultimo lugar false disponivel
	private boolean procurarLugarFalseArvore(BinTree noAtual, int noIDExistente, int novoNoID, String pergunta) {
		if (noAtual.nodeID == noIDExistente) {
			// Encontrou nó
			if (noAtual.falseArvore == null)
				noAtual.falseArvore = new BinTree(novoNoID, pergunta);
			else {
			}
			return true;
		} else {
			// Tentar arvore "true"
			if (noAtual.trueArvore != null) {
				if (procurarLugarFalseArvore(noAtual.trueArvore, noIDExistente, novoNoID, pergunta)) {
					return true;
				} else {
					// Tentar arvore "false"
					if (noAtual.falseArvore != null) {
						return (procurarLugarFalseArvore(noAtual.falseArvore, noIDExistente, novoNoID, pergunta));
					} else
						return false; // Não encontrou aqui
				}
			} else
				return false; // Não encontrou aqui
		}
	}

	// Pergunta para aarvore
	public int queryBinTree() {
		return queryBinTree(raiz);
	}

	// Percorre a arvore
	private int queryBinTree(BinTree noAtual) {
		// Testa para nós folhas
		if (noAtual.trueArvore == null) {
			if (noAtual.falseArvore == null) {
				return noAtual.nodeID;
			}
		}
		return perguntar(noAtual);
	}

	// Pega a resposta das perguntas do construtor, cujo qual foram
	// armazenadas no Nó
	private int perguntar(BinTree noAtual) {
		String answer = noAtual.resposta;

		if (answer.equals("Yes"))
			return queryBinTree(noAtual.trueArvore);
		else {
			if (answer.equals("No"))
				return queryBinTree(noAtual.falseArvore);
			else {
				return perguntar(noAtual);
			}
		}
	}

	// Classe da arvore
	private class BinTree {

		// Numero do nó
		private int nodeID;
		
		// Resposta
		private String resposta = null;
		
		// Nós filhos
		private BinTree trueArvore = null;
		private BinTree falseArvore = null;

		// Construtor
		public BinTree(int newNodeID, String newQuestAns) {
			nodeID = newNodeID;
			resposta = newQuestAns;
		}
	}
}