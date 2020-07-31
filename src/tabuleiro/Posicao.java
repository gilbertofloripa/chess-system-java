package tabuleiro;

public class Posicao {
	private int lin;
	private int col;
	
	public Posicao() {
	}

	public Posicao(int lin, int col) {
		this.lin = lin;
		this.col = col;
	}

	public Integer getLin() {
		return lin;
	}

	public Integer getCol() {
		return col;
	}

	@Override
	public String toString() {
		return lin + ", " + col;
	}

}
