package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLins()][getTabuleiro().getCols()];
		
		Posicao p = new Posicao(0, 0);
		
		//******* ACIMA ********
		p.setPosicao(posicao.getLin() - 1, posicao.getCol());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		//******* ABAIXO ********
		p.setPosicao(posicao.getLin() + 1, posicao.getCol());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		//******* ESQUERDA ********
		p.setPosicao(posicao.getLin(), posicao.getCol() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		//******* DIREITA ********
		p.setPosicao(posicao.getLin(), posicao.getCol() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		//******* ACIMA E ESQUERDA ********
		p.setPosicao(posicao.getLin() - 1, posicao.getCol() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		//******* ABAIXO E ESQUERDA ********
		p.setPosicao(posicao.getLin() + 1, posicao.getCol() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		//******* ACIMA E DIREITA ********
		p.setPosicao(posicao.getLin() - 1, posicao.getCol() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		//******* ABAIXO E DIREITA ********
		p.setPosicao(posicao.getLin() + 1, posicao.getCol() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLin()][p.getCol()] = true;
		}
		
		return mat;
	}
}
