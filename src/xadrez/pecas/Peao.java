package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{

	PartidaXadrez partidaXadrez;
	
	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLins()][getTabuleiro().getCols()];
		
		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCA) {
			//******  TESTA 1 CASA PARA CIMA PECA BRANCA *******
			//pega a posicao atual da peca e se for branca subtrai 1 para testar se e posicao legal
			p.setPosicao(posicao.getLin() - 1, posicao.getCol());
			// Se a posicao p existe e nao tem peca, posicao legal
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)){
				mat[p.getLin()][p.getCol()] = true;
			}

			//******  TESTA 2 CASA PARA CIMA PECA BRANCA *******
			// se for o primeiro mov pode andar duas casa
			p.setPosicao(posicao.getLin() - 2, posicao.getCol());
			Posicao p2 = new Posicao(posicao.getLin() - 1, posicao.getCol());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p) &&
				getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temPeca(p2) &&
				getContadorMovi() == 0){

				mat[p.getLin()][p.getCol()] = true;
			}

			//******  TESTA 1 CASA PARA DIAGONAL ESQUERDA PECA BRANCA *******
			p.setPosicao(posicao.getLin() - 1, posicao.getCol() - 1);
			if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
				mat[p.getLin()][p.getCol()] = true;
			}

			//******  TESTA 1 CASA PARA DIAGONAL DIREITA PECA BRANCA *******
			p.setPosicao(posicao.getLin() - 1, posicao.getCol() + 1);
			if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
				mat[p.getLin()][p.getCol()] = true;
			}
			
			// Enpassan piao Brancas a esquerda (somente na linha 3)
			if (posicao.getLin() == 3) {
				// ESQUERDA
				// verifica se tem um peao oponente ao lado esquerdo e esta vulneravela enpassant
				Posicao esquerda = new Posicao(posicao.getLin(), posicao.getCol() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && ePecaOponente(esquerda)
					&& getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassant()){
					mat[esquerda.getLin() - 1][esquerda.getCol()] = true;
				}
				// DIREITA
				// verifica se tem um peao oponente ao lado direita e esta vulneravela enpassant
				Posicao direita = new Posicao(posicao.getLin(), posicao.getCol() + 1);
				if (getTabuleiro().posicaoExiste(direita) && ePecaOponente(direita)
					&& getTabuleiro().peca(direita) == partidaXadrez.getEnPassant()){
					mat[direita.getLin() - 1][direita.getCol()] = true;
				}
			}
		}
		else {
			//******  TESTA 1 CASA PARA BAIXO PECA PRETA *******
			//pega a posicao atual da peca e se for branca subtrai 1 para testar se e posicao legal
			p.setPosicao(posicao.getLin() + 1, posicao.getCol());
			// Se a posicao p existe e nao tem peca, posicao legal
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)){
				mat[p.getLin()][p.getCol()] = true;
			}

			//******  TESTA 2 CASA PARA BAIXO PECA PRETA *******
			// se for o primeiro mov pode andar duas casa
			p.setPosicao(posicao.getLin() + 2, posicao.getCol());
			Posicao p2 = new Posicao(posicao.getLin() + 1, posicao.getCol());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p) &&
				getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temPeca(p2) &&
				getContadorMovi() == 0){
				
				mat[p.getLin()][p.getCol()] = true;
			}

			//******  TESTA 1 CASA PARA DIAGONAL ESQUERDA PECA PRETA *******
			p.setPosicao(posicao.getLin() + 1, posicao.getCol() + 1);
			if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
				mat[p.getLin()][p.getCol()] = true;
			}

			//******  TESTA 1 CASA PARA DIAGONAL DIREITA PECA PRETA *******
			p.setPosicao(posicao.getLin() + 1, posicao.getCol() - 1);
			if (getTabuleiro().posicaoExiste(p) && ePecaOponente(p)){
				mat[p.getLin()][p.getCol()] = true;
			}

			// Enpassan piao PRETO a esquerda (somente na linha 3)
			if (posicao.getLin() == 4) {
				// ESQUERDA
				// verifica se tem um peao oponente ao lado esquerdo e esta vulneravela enpassant
				Posicao esquerda = new Posicao(posicao.getLin(), posicao.getCol() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && ePecaOponente(esquerda)
					&& getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassant()){
					mat[esquerda.getLin() + 1][esquerda.getCol()] = true;
				}
				// DIREITA
				// verifica se tem um peao oponente ao lado direita e esta vulneravela enpassant
				Posicao direita = new Posicao(posicao.getLin(), posicao.getCol() + 1);
				if (getTabuleiro().posicaoExiste(direita) && ePecaOponente(direita)
					&& getTabuleiro().peca(direita) == partidaXadrez.getEnPassant()){
					mat[direita.getLin() + 1][direita.getCol()] = true;
				}
			}
		}
		
		return mat;
	}
	@Override
	public String toString() {
		return "P";
	}
}
