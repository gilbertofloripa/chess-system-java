package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca{
	private Cor cor;
	private int contadorMovi;
	
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContadorMovi() {
		return contadorMovi;
	}
	
	public void adicContadorMovi() {
		contadorMovi++;
	}
	
	public void subContadorMovi() {
		contadorMovi--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		// converto a posicao atual de matrix para xadrez
		return PosicaoXadrez.dePosicao(posicao); 
	}
	
	
	// verifica se a peca na posicao e do oponente
	protected boolean ePecaOponente(Posicao posicao){
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		// e peca oponente se <> null e a cor for <> na minha peca
		return p != null && p.getCor() != cor;
	}
}
