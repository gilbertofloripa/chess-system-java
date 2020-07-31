package tabuleiro;

public class Tabuleiro {
	private int lins;
	private int cols;
	private Peca[][] pecas;
	
	public Tabuleiro(int lins, int cols) {
		this.lins = lins;
		this.cols = cols;
		pecas = new Peca[lins][cols];
		
	}

	public int getLins() {
		return lins;
	}

	public void setLins(int lins) {
		this.lins = lins;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}
	
	
}
