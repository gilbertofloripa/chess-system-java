package tabuleiro;

public class Tabuleiro {
	private int lins;
	private int cols;
	private Peca[][] pecas;
	
	public Tabuleiro(int lins, int cols) {
		if (lins < 1 || cols < 1) {
			throw new TabuleiroException("Erro na criacao do Tabuleiro, linhas e colunas tem que ser > 0");
		}
		this.lins = lins;
		this.cols = cols;
		pecas = new Peca[lins][cols];
		
	}

	public int getLins() {
		return lins;
	}


	public int getCols() {
		return cols;
	}

	public Peca peca(int lin, int col) {
		if (!posicaoExiste(lin, col)) {
			throw new TabuleiroException("Essa posição não existe");
		}
		return pecas[lin][col];
	}
	
	public Peca peca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("Essa posição não existe");
		}
		return pecas[posicao.getLin()][posicao.getCol()];
		
	}
	
	public void colocarPeca(Peca peca, Posicao posicao) {
		if (temPeca(posicao)) {
			throw new TabuleiroException("Já existe uma peca nessa posição " + posicao);
		}
		pecas[posicao.getLin()][posicao.getCol()] = peca;
		peca.posicao = posicao;
	}
	
	private boolean posicaoExiste(int lin, int col) {
		return lin >= 0 && lin <= lins && col >=0 && col <= cols;
	}
	
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLin(), posicao.getCol());
	}
	public boolean temPeca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("Essa posição não existe");
		}
		return peca(posicao) != null;
	}
}
