package trabalhoFinal;
@SuppressWarnings("serial")
public class PilhaVaziaException extends RuntimeException {

	public String getMessage() {
		return "Erro! A fila está vazia...";
	}
}