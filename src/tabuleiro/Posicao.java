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

	public void setLin(int lin) {
		this.lin = lin;
	}
	
	public Integer getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public void setPosicao(int lin, int col) {
		this.lin = lin;
		this.col = col;
	}
	@Override
	public String toString() {
		return lin + ", " + col;
	}

}
