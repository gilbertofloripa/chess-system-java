package xadrez.pecas;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez{

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLins()][getTabuleiro().getCols()];
		
		Posicao p = new Posicao(0, 0);

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
