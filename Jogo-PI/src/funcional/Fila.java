package funcional;

public class Fila {
	int fila[];
	int comeco;
	int ultimo;
	int total;
	int qntd_elem;
	
	public Fila(int n){
		fila = new int[n];
		ultimo = -1;
		comeco = 0;
		total = n;
		qntd_elem = 0;
	}
	
	public boolean vazia(){
		if (qntd_elem == 0)
			return true;
		return false;
	}
	
	public boolean cheia(){
		if (qntd_elem == total)
			return true;
		return false;
	}
	
	public void adicionar(int x){
		if (!cheia()) {
			if (ultimo == total - 1)
				ultimo = -1;
		
			ultimo += 1;
			fila[ultimo] = x;
			qntd_elem += 1;
		}
	}
	
	public void remover(){
		if (!vazia()) {
			comeco += 1;
			
			if (comeco == total)
				comeco = 0;
			
			qntd_elem -= 1;
		}
	}
	
	public void exibir(){
		for (int cont = 0, x = comeco; cont < qntd_elem; cont++, x++){
			System.out.print(fila[x] + ", ");
			
			if (x == total)
				x = 0;
		}
	}
	
	public int getIndice(int x){
		return fila[x];
	}
	
	public int inicio() {
		return fila[comeco];
	}
}
