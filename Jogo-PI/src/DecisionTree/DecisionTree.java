package DecisionTree;

public class DecisionTree {
	private BinTree raiz = null;
	private double vidaPerdida;
	private double tempoDecorrido;


	public DecisionTree(double vida, double tempo) {
		this.vidaPerdida = vida;
		this.tempoDecorrido = tempo;

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

	public void init(int novoNoID, String pergunta) {
		raiz = new BinTree(novoNoID, pergunta);
	}

	public void addTrueNode(int noIDExistente, int novoNoID, String pergunta) {
		if (raiz != null) {
			if (procurarLugarTrueArvore(raiz, noIDExistente, novoNoID, pergunta)) {
			} else {

			}
		}
	}

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

	public void addFalseNode(int noIDExistente, int novoNoID, String pergunta) {
		if (raiz != null) {
			if (procurarLugarFalseArvore(raiz, noIDExistente, novoNoID, pergunta)) {
			} else {
			}
		}
	}

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

	public int queryBinTree() {
		return queryBinTree(raiz);
	}

	private int queryBinTree(BinTree noAtual) {
		// Testa para nós folhas
		if (noAtual.trueArvore == null) {
			if (noAtual.falseArvore == null) {
				return noAtual.nodeID;
			}
		}
		return perguntar(noAtual);
	}

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

	private class BinTree {

		private int nodeID;
		private String resposta = null;
		private BinTree trueArvore = null;
		private BinTree falseArvore = null;

		public BinTree(int newNodeID, String newQuestAns) {
			nodeID = newNodeID;
			resposta = newQuestAns;
		}
	}
}