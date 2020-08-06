package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez{

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "Q";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLins()][getTabuleiro().getCols()];
		
		Posicao p = new Posicao(0, 0);

		// ********* VERIFICA PARA CIMA ********************
		// posiciona verificacao a cima da torre
		p.setPosicao(posicao.getLin() - 1, posicao.getCol());
		
		// executa enquanto a posicao existe e não tem peca
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLin()][p.getCol()] = true;
			p.setLin(p.getLin() - 1); // diminui 1 da matrix temp p
		}
		// marca true se a posicao existe e é um peca do oponente
		if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
			mat[p.getLin()][p.getCol()] = true;
		}
		
		// ********* VERIFICA PARA BAIXO ********************
		p.setPosicao(posicao.getLin() + 1, posicao.getCol());
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLin()][p.getCol()] = true;
			p.setLin(p.getLin() + 1); // diminui 1 da matrix temp p
		}
		if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
			mat[p.getLin()][p.getCol()] = true;
		}
		
		// ********* VERIFICA PARA ESQUERDA ********************
		p.setPosicao(posicao.getLin(), posicao.getCol() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLin()][p.getCol()] = true;
			p.setCol(p.getCol() - 1); // diminui 1 da matrix temp p
		}
		if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
			mat[p.getLin()][p.getCol()] = true;
		}
		
		// ********* VERIFICA PARA DIREITA ********************
		p.setPosicao(posicao.getLin(), posicao.getCol() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLin()][p.getCol()] = true;
			p.setCol(p.getCol() + 1); // diminui 1 da matrix temp p
		}
		if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
			mat[p.getLin()][p.getCol()] = true;
		}
		
		// ********* VERIFICA PARA DIAGONAL DIREITA CIMA ********************
		p.setPosicao(posicao.getLin() - 1, posicao.getCol() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLin()][p.getCol()] = true;
			p.setLin(p.getLin() - 1);
			p.setCol(p.getCol() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
			mat[p.getLin()][p.getCol()] = true;
		}

		// ********* VERIFICA PARA DIAGONAL ESQUERDA CIMA ********************
		p.setPosicao(posicao.getLin() - 1, posicao.getCol() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLin()][p.getCol()] = true;
			p.setLin(p.getLin() - 1);
			p.setCol(p.getCol() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
			mat[p.getLin()][p.getCol()] = true;
		}

		// ********* VERIFICA PARA DIAGONAL DIREITA BAIXO ********************
		p.setPosicao(posicao.getLin() + 1, posicao.getCol() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLin()][p.getCol()] = true;
			p.setLin(p.getLin() + 1);
			p.setCol(p.getCol() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
			mat[p.getLin()][p.getCol()] = true;
		}
		
		// ********* VERIFICA PARA DIAGONAL ESQUERDA BAIXO ********************
		p.setPosicao(posicao.getLin() + 1, posicao.getCol() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLin()][p.getCol()] = true;
			p.setLin(p.getLin() + 1);
			p.setCol(p.getCol() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
			mat[p.getLin()][p.getCol()] = true;
		}
		
		return mat;
	}

}
