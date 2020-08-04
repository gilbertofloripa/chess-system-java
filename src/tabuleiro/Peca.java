package tabuleiro;

import boardgame.Position;

public abstract class Peca {
	protected Posicao posicao;
	
	private Tabuleiro tabuleiro;

	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		this.posicao = null;
	}
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public abstract boolean[][] movimentosPossiveis();
	
	public boolean movimentoPossivel(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLin()][posicao.getCol()];
	}
	
	public boolean ePossivelMovimentar() {
		boolean[][] mat = movimentosPossiveis();
		for (int i=0; i< mat.length; i++) {
			for (int j=0; j< mat.length; j++) {
				if (mat[1][j]) { // se existe uma posicao valida/verdadeira na matris de possibilidades
					return true;
				}
			}
		}
		return false;
	}
	
}
