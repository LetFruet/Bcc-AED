package trabalhoFinal;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ManipulandoArquivos<T> {

	public PilhaLista<String> pilha;
	public String nomeTags[] = new String[110];
	public int contagemTags[] = new int[110];

	public String[] getNomeTags() {
		return nomeTags;
	}

	public int[] getContagemTags() {
		return contagemTags;
	}

	public ManipulandoArquivos(File arquivo) throws FileNotFoundException {
		Scanner leitor = new Scanner(arquivo);

		String s = "";
		while (leitor.hasNextLine()) {
			s += leitor.nextLine() + "\n";
		}
		leitor.close();

		pilha = new PilhaLista<String>();
		String inserir = "", retirar = "";

		percorrerArquivo(s, pilha, inserir, retirar, nomeTags, contagemTags);

		mergeSort(nomeTags, contagemTags, 0, nomeTags.length - 1);
	}

	private void percorrerArquivo(String s, PilhaLista<String> pilha, String inserir, String retirar, String[] nomeTags,
			int[] contagemTags) {
		String tagEsperada = null;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '<') {
				int end = s.indexOf('>', i);

				if (s.charAt(i + 1) == '/') {
					retirar = s.substring(i + 2, end);

					if (!pilha.estaVazia() && pilha.peek().equalsIgnoreCase(retirar)) {
						pilha.pop();
					} else {
						if (pilha.estaVazia()) {
							throw new TagsException(
									"Foi encontrada uma tag final inesperada, sem abertura. \nEsperava-se uma abertura de "
											+ retirar + " mas foi encontrada somente /" + retirar + ".");
						} else {
							tagEsperada = pilha.peek();
							throw new TagsException("Foi encontrada uma tag final inesperada. Esperava-se "
									+ tagEsperada + " mas foi encontrada " + retirar + ".");
						}
					}
				} else {
					inserir = s.substring(i + 1, end).split("\\s+")[0];
					if (confereSingletonTags(inserir)) {
						pilha.push(inserir);
						armazenarTags(nomeTags, contagemTags, inserir);

					} else {
						armazenarTags(nomeTags, contagemTags, inserir);
					}
				}
				i = end;
			}
		}
	}

	private boolean confereSingletonTags(String inserir) {
		return (!inserir.equalsIgnoreCase("meta") && !inserir.equalsIgnoreCase("base")
				&& !inserir.equalsIgnoreCase("br") && !inserir.equalsIgnoreCase("col")
				&& !inserir.equalsIgnoreCase("command") && !inserir.equalsIgnoreCase("embed")
				&& !inserir.equalsIgnoreCase("hr") && !inserir.equalsIgnoreCase("img")
				&& !inserir.equalsIgnoreCase("input") && !inserir.equalsIgnoreCase("link")
				&& !inserir.equalsIgnoreCase("param") && !inserir.equalsIgnoreCase("source")
				&& !inserir.equalsIgnoreCase("!doctype"));
	}

	private void armazenarTags(String[] nomeTags, int[] contagemTags, String inserir) {
		for (int contTamDireita = 0; contTamDireita < contagemTags.length; contTamDireita++) {
			if (nomeTags[contTamDireita] == null) {
				nomeTags[contTamDireita] = inserir;
				contagemTags[contTamDireita] = 1;
				break;
			} else if (nomeTags[contTamDireita].equalsIgnoreCase(inserir)) {
				contagemTags[contTamDireita]++;
				break;
			}
		}
	}

	private void mergeSort(String[] nomeTags, int[] contagemTags, int inicio, int fim) {
		if (inicio < fim) {
			int meio = (inicio + fim) / 2;
			mergeSort(nomeTags, contagemTags, inicio, meio);
			mergeSort(nomeTags, contagemTags, meio + 1, fim);
			merge(nomeTags, contagemTags, inicio, meio, fim);
		}
	}

	private void merge(String[] nomeTags, int[] contagemTags, int inicio, int meio, int fim) {
		int tamEsquerda = meio - inicio + 1, tamDireita = fim - meio, cEsq = 0, cDir = 0, cTemp = inicio;
		String[] esquerda = new String[tamEsquerda], direita = new String[tamDireita];
		int[] contEsquerda = new int[tamEsquerda], contDireita = new int[tamDireita];

		for (int i = 0; i < tamEsquerda; ++i) {
			esquerda[i] = nomeTags[inicio + i];
			contEsquerda[i] = contagemTags[inicio + i];
		}

		for (int j = 0; j < tamDireita; ++j) {
			direita[j] = nomeTags[meio + 1 + j];
			contDireita[j] = contagemTags[meio + 1 + j];
		}

		while (cEsq < tamEsquerda && cDir < tamDireita) {
			if (esquerda[cEsq] == null || (direita[cDir] != null && esquerda[cEsq].compareToIgnoreCase(direita[cDir]) <= 0)) {
				nomeTags[cTemp] = esquerda[cEsq];
				contagemTags[cTemp] = contEsquerda[cEsq];
				cEsq++;
			} else {
				nomeTags[cTemp] = direita[cDir];
				contagemTags[cTemp] = contDireita[cDir];
				cDir++;
			}
			cTemp++;
		}

		while (cEsq < tamEsquerda) {
			nomeTags[cTemp] = esquerda[cEsq];
			contagemTags[cTemp] = contEsquerda[cEsq];
			cEsq++;
			cTemp++;
		}

		while (cDir < tamDireita) {
			nomeTags[cTemp] = direita[cDir];
			contagemTags[cTemp] = contDireita[cDir];
			cDir++;
			cTemp++;
		}
	}

	public String imprimirQtdeTags(String[] nomeTags, int[] contagemTags) {
		String dados = "--- Quantidade de tags processadas ---\n";
		for (int z = 0; z < nomeTags.length; z++) {
			if (nomeTags[z] != null) {
				dados += nomeTags[z] + " - " + contagemTags[z] + "\n";
			}
		}
		return dados;
	}

	public String verificarFormatacao(PilhaLista<String> pilha) {
		if (pilha.estaVazia()) {
			return "O arquivo está bem formatado. A pilha está vazia.";
		} else {
			String tagEsperada = pilha.toString();
			throw new TagsException("Faltam tags finais. A(s) tag(s) de fechamento esperada(s) " + tagEsperada
					+ " não foi/foram encontrada(s).");
		}
	}
}