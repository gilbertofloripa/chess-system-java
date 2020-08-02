package xadrez;


import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;


public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro (8, 8);
		inicialPosicao();
	}
	
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLins()][tabuleiro.getCols()];
		for (int i = 0; i < tabuleiro.getLins(); i++) {
			for (int j  =0; j < tabuleiro.getCols(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	public void novaPosicaoPeca(char col, int lin, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(col, lin).paraPosicao());
	}
	public void inicialPosicao() {
		novaPosicaoPeca('b', 6, new Torre(tabuleiro, Cor.PRETA));
		novaPosicaoPeca('c', 6, new Rei(tabuleiro, Cor.PRETA));
		novaPosicaoPeca('f', 7, new Torre(tabuleiro, Cor.BRANCA));

		
		//tabuleiro.colocarPeca(new Torre(tabuleiro, Cor.PRETA), new Posicao(2, 1));
		//tabuleiro.colocarPeca(new Rei(tabuleiro, Cor.PRETA), new Posicao(1, 2));
		//tabuleiro.colocarPeca(new Torre(tabuleiro, Cor.BRANCA), new Posicao(7, 0));
		//tabuleiro.colocarPeca(new Rei(tabuleiro, Cor.BRANCA), new Posicao(7, 2));
		
	}
}
