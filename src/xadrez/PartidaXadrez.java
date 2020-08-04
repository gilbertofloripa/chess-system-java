package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;


public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual; 
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro (8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCA;
		inicialPosicao();
	}
	
	public int getTurno() {
		return turno;
	}

	public Cor getjogadorAtual() {
		return jogadorAtual;
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
	
	public boolean[][] possivelMovimentos(PosicaoXadrez origemPosicao){
		// converte posicao de xadrez para matrix
		Posicao posicao = origemPosicao.paraPosicao();
		validaPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaXadrez moveXadrez(PosicaoXadrez origem, PosicaoXadrez destino) {
		// Converte as posicoes recebidas de xadrez para matrix
		Posicao orig = origem.paraPosicao();
		Posicao dest = destino.paraPosicao();
		validaPosicaoOrigem(orig);
		validaPosicaoDestino(orig, dest);
		Peca pecaCapturada = movePeca(orig, dest);
		proximoTurno();
		return (PecaXadrez) pecaCapturada;
	}
	
	private void validaPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temPeca(posicao)) {
			throw new XadrezException("Não existe peca na posicao de origem");
		}
		// Valida se a cor da peca e do jogador atual
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("Essa peca não e sua");
		}
		
		// valida se existe algum posicao valida
		if (!tabuleiro.peca(posicao).ePossivelMovimentar()) {
			throw new XadrezException("Essa peca não pode ser movimentada");
		}
	}
	
	private void validaPosicaoDestino(Posicao orig, Posicao dest) {
		// Verifica a peca na posicao origem se e possivel mov para o destino
		if (!tabuleiro.peca(orig).movimentoPossivel(dest)) {
			throw new XadrezException("A peca escolhida não pode mover-se para a posicao destino");
		}
	}

	private Peca movePeca(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);// remove a peca da origem e guarda a peca em p
		Peca pecaCapturada = tabuleiro.removePeca(destino);// remove a peca do destino se houver e guarda
		tabuleiro.colocarPeca(p, destino);// coloca a peca p na posicao destino
		return pecaCapturada;
	}
	
	private void proximoTurno() {
		turno++;
		// Caso cor do jogador for branca muda para preta senao muda para branca
		// alterna a cor
		jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
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
