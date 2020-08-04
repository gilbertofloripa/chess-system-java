package xadrez;

import tabuleiro.Posicao;

public class PosicaoXadrez {
	private char col;
	private int lin;
	
	public PosicaoXadrez(char col, int lin) {
		if (col <'a' || col > 'h' || lin < 1 || lin > 8) {
			throw new XadrezException("Erro na Posicao do Xadrez, tem que se de a1 a h8");
}
		this.col = col;
		this.lin = lin;
	}

	public char getCol() {
		return col;
	}

	public int getLin() {
		return lin;
	}
	protected Posicao paraPosicao() {
		// Transforma a posicao do tabuleiro para matriz Ex: a1 = 0,0
		// ex: a1-> a - a = 0, b - a = 1.....
		return new Posicao(8 - lin, col - 'a');
	}
	
	protected static PosicaoXadrez dePosicao(Posicao posicao) {
		// Transforma da a posicao matriz para xadrez EX: 0,0 = a1
		// O (char) na frente converte o resultado para caracter
		return new PosicaoXadrez((char)('a' + posicao.getCol()), 8 - posicao.getLin());
	}
	
	@Override
	public String toString() {
		return "" + col + lin;
	}
}
