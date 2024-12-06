package trabalhoFinal;
public class ListaEncadeada<T> {

	private NoLista<T> primeiro;
	
	public ListaEncadeada() {
		this.primeiro = null;
	}
	
	public NoLista<T> getPrimeiro() {
		return primeiro;
	}

	public void inserir(T info) {
		NoLista<T> novoNo = new NoLista<T>();
		novoNo.setInfo(info);
		novoNo.setProximo(this.primeiro);
		this.primeiro = novoNo;
	}
	
	public boolean estaVazia() {
		if(this.primeiro == null) {
			return true;
		}
		return false;
	}
	
	public NoLista<T> buscar(T info) {
		NoLista<T> p = this.primeiro;
		while(p != null ) {
			if (p.getInfo().equals(info)) {
				return p;
			}
			p = p.getProximo();
		}
		return null;
	}
	
	public void retirar(T info) {
		NoLista<T> anterior = null;
		NoLista<T> p = this.primeiro;
		while(p != null && p.getInfo() != info) {
			anterior = p;
			p = p.getProximo();
		}
		
		if(p != null) {
			if(p.equals(this.primeiro)) {
				this.primeiro = p.getProximo();
			} else {
				anterior.setProximo(p.getProximo());
			}
		}
	}

	public int obterComprimento() {
		int cont = 0;
		NoLista<T> p = this.primeiro;
		while (p != null) {
			p = p.getProximo();
			cont++;
		}
		return cont;
	}
	
	public NoLista<T> obterNo(int idx) {
		NoLista<T> p = this.primeiro;
		int cont = 0;
		
		if(idx < 0 || idx > (obterComprimento()) - 1) {
			throw new IndexOutOfBoundsException();
		}
		while(p != null) {
			if (cont == idx) {
				return p;
			}
			cont++;
			p = p.getProximo();
		}
		return null;
	}
	
	public void liberar() {
		NoLista<T> p = this.primeiro, temporario;
		
		while(p.getProximo() != null) {
			temporario = p.getProximo();
			p.setProximo(null);
			p = temporario;
		}
	
		if (this.primeiro != null) {
			this.primeiro = null;
		}
		
		temporario = null;
	}

	public String toString() {
		String dados = "";
		NoLista<T> p = this.primeiro;
		while (p != null) {
			dados += p.getInfo() + ",";
			p = p.getProximo();
		}
		return dados;
	}
}