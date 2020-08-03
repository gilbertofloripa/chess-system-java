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
		novaPosicaoPeca('c', 1, new Torre(tabuleiro, Cor.BRANCA));
		novaPosicaoPeca('c', 2, new Torre(tabuleiro, Cor.BRANCA));
		novaPosicaoPeca('d', 2, new Torre(tabuleiro, Cor.BRANCA));
        novaPosicaoPeca('e', 2, new Torre(tabuleiro, Cor.BRANCA));
        novaPosicaoPeca('e', 1, new Torre(tabuleiro, Cor.BRANCA));
        novaPosicaoPeca('d', 1, new Rei(tabuleiro, Cor.BRANCA));

        novaPosicaoPeca('c', 7, new Torre(tabuleiro, Cor.PRETA));
        novaPosicaoPeca('c', 8, new Torre(tabuleiro, Cor.PRETA));
        novaPosicaoPeca('d', 7, new Torre(tabuleiro, Cor.PRETA));
        novaPosicaoPeca('e', 7, new Torre(tabuleiro, Cor.PRETA));
        novaPosicaoPeca('e', 8, new Torre(tabuleiro, Cor.PRETA));
        novaPosicaoPeca('d', 8, new Rei(tabuleiro, Cor.PRETA));

	}
}
