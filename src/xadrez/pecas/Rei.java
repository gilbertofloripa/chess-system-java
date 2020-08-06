package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{
	private PartidaXadrez partidaXadrez;
			
	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testaRockTorre(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		// Verifica se a torre pode fazer o rock
		return p != null && p instanceof Torre && p.getCor() == getCor() 
				&& p.getContadorMovi() == 0;
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
		
		//******* VERIFICA ROCK ********
		if (getContadorMovi() == 0 && !partidaXadrez.getCheck()) {
			//posicao da torre a direita
			Posicao posT1 = new Posicao(posicao.getLin(), posicao.getCol() + 3);
			if (testaRockTorre(posT1)) { 
				// verifica as posicoes libre a direita do rei
				Posicao p1 = new Posicao(posicao.getLin(), posicao.getCol() + 1);
				Posicao p2 = new Posicao(posicao.getLin(), posicao.getCol() + 2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) { 
					mat[posicao.getLin()][posicao.getCol() + 2] = true;
				}
			}

			//posicao da torre a esquerda
			Posicao posT2 = new Posicao(posicao.getLin(), posicao.getCol() - 4);
			if (testaRockTorre(posT2)) { // verifica se a torre a direita pode fazer rock
				// verifica as posicoes libre a direita do rei
				Posicao p1 = new Posicao(posicao.getLin(), posicao.getCol() - 1);
				Posicao p2 = new Posicao(posicao.getLin(), posicao.getCol() - 2);
				Posicao p3 = new Posicao(posicao.getLin(), posicao.getCol() - 3);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null 
						&& getTabuleiro().peca(p3) == null) {
					mat[posicao.getLin()][posicao.getCol() - 2] = true;
				}
			}
		}
		
		
		return mat;
	}
}
