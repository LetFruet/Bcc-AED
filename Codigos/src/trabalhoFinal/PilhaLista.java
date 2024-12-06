package trabalhoFinal;
public class PilhaLista<T> implements Pilha<T> {

	private ListaEncadeada<T> lista;
	
	public PilhaLista() {
		this.lista = new ListaEncadeada<T>();
	}
	
	@Override
	public void push(T info){
		lista.inserir(info);
	}

	@Override
	public T pop() throws PilhaVaziaException {
		if (estaVazia()) {
			throw new PilhaVaziaException();
		}
		
		T valor;
		valor = peek();
		lista.retirar(valor);
		return valor;
	}

	@Override
	public T peek() throws PilhaVaziaException {
		if (estaVazia()) {
			throw new PilhaVaziaException();
		}
		return (T) lista.getPrimeiro().getInfo();
	}
	
	@Override
	public boolean estaVazia() {
		if (lista.getPrimeiro() == null) {
			return true;
		}
		return false;
	}

	@Override
	public void liberar() {
		lista.liberar();
	}
	
	public String toString() {
		return lista.toString();
	}
}