package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez{

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "C";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLins()][getTabuleiro().getCols()];
		
		Posicao p = new Posicao(0, 0);
		//******* ACIMA 2 DIREITA 1 ********
		p.setPosicao(posicao.getLin() - 2, posicao.getCol() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		//******* CIMA 2 ESQUERDA 1 ********
		p.setPosicao(posicao.getLin() - 2, posicao.getCol() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}

		//******* DIREITA 2 ACIMA 1 ********
		p.setPosicao(posicao.getLin() - 1, posicao.getCol() + 2);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		//******* ESQUERDA 2 ACIMA 1********
		p.setPosicao(posicao.getLin() - 1, posicao.getCol() - 2);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}

		//******* DIREITA 2 ABAIXO 1 ********
		p.setPosicao(posicao.getLin() + 1, posicao.getCol() + 2);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		//******* ESQUERDA 2 ABAIXO 1 ********
		p.setPosicao(posicao.getLin() + 1, posicao.getCol() - 2);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}

		//******* ABAIXO 2 DIREITA 1 ********
		p.setPosicao(posicao.getLin() + 2, posicao.getCol() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		//******* ABAIXO 2 ESQUERDA 1 ********
		p.setPosicao(posicao.getLin() + 2, posicao.getCol() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		
		return mat;
	}
}
