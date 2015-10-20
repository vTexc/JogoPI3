package funcional;

public class Grafo {
	private int matrizAdjacencia[][];
	private int vertices;
	
	public Grafo(int v){
		matrizAdjacencia = new int[v][v];
		vertices = v;
		for (int j = 0; j < v; j++){
			for (int k = 0; k < v; k++){
				matrizAdjacencia[j][k] = 0;
			}
		}
	}
	
	public void inserirAresta (int v1, int v2){
		if (v1 < vertices && v2 < vertices){
			matrizAdjacencia[v1][v2] = 1;
		} else {
			System.out.println("Inserção em vertice inexistente.");
		}
	}
	
	public int grauVertice (int v){
		int grau = 0;
		
		for (int x = 0; x < vertices; x++){
			if (matrizAdjacencia[v][x] == 1){
				grau += 1;
			}
		}
		
		return grau;
	}
	
	private boolean adj(int a, int b) {
		if(matrizAdjacencia[a][b] != 0)
			return true;
		else
			return false;
	}
	
	public void buscaEmProfundidade() {
		int verificado[] = new int[vertices];
		int caminho[] = new int[vertices];
		
		for(int x = 0; x < vertices; x++) {
			verificado[x] = 0;
		}
		
		for(int x = 0; x < vertices; x++) {
			if(verificado[x] == 0) {
				buscaEmProfundidadeVisita(x, verificado, caminho);
			}
		}
		
		System.out.println("Acabou.");
	}
	
	private void buscaEmProfundidadeVisita(int u, int verificado[], int caminho[]) {
		verificado[u] = -1;
		
		for(int x = 0; x < vertices; x++) {
			if(verificado[x] == 0 && adj(u, x)) {
				caminho[x] = u;
				buscaEmProfundidadeVisita(x, verificado, caminho);
			}
		}
		verificado[u] = 1;
	}
}
