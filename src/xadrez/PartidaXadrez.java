package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;


public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual; 
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	
	private List<Peca> pecasTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	
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
	
	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
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
		if (testeCheck(jogadorAtual)) {
			desfazMovi(orig, dest, pecaCapturada);
			throw new XadrezException("Posição ilegal, voce não pode se colocar em CHECK!");
		}
		// verifica se o oponete ficou em check apos a jogada
		check = (testeCheck(oponente(jogadorAtual))) ? true : false;
		
		// verifica se e check mate
		if (testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
			proximoTurno();
		}
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
		if (pecaCapturada != null) {
			pecasTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}
	
	private void desfazMovi(Posicao origem, Posicao destino, Peca pecaCapturada){
		// desfaz o movimento quando se movimenta para uma posica de xeque
		Peca p = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, origem); // volta a peca para origem
		
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino); // volta a peca capturada
			pecasCapturadas.remove(pecaCapturada);
			pecasTabuleiro.add(pecaCapturada);
		}
	}
	
	private void proximoTurno() {
		turno++;
		// Caso cor do jogador for branca muda para preta senao muda para branca
		// alterna a cor
		jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCA ? Cor.PRETA : Cor.BRANCA);
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasTabuleiro.stream()
			.filter(x -> ((PecaXadrez)x).getCor() == cor)
			.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) { // busca na lista de pecas a que tem instancia da classe rei
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe Rei da cor " + cor + " no tabuleiro, erro de sistema");  
	}
	
	private boolean testeCheck(Cor cor) {
		// pega a posicao do rei da cor e converte para posica matrix
		Posicao reiPosicao = rei(cor).getPosicaoXadrez().paraPosicao();
		// Lista de pecas do oponente
		List<Peca> pecasOponente = pecasTabuleiro.stream()
				.filter(x -> ((PecaXadrez)x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		// para cada peca do oponente verifica seus possiveis movimentos
		for (Peca p : pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			//Se for essa posicao for verdadeiro (possivel movimento do oponente)
			if (mat[reiPosicao.getLin()][reiPosicao.getCol()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testeCheckMate(Cor cor) {
		if (!testeCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasTabuleiro.stream()
				.filter(x -> ((PecaXadrez)x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i=0; i< tabuleiro.getLins(); i++) {
				for (int j=0; j< tabuleiro.getCols(); j++) {
					if (mat[i][j]) { // e uma posica possivel?
						// Peca a posicao do rei
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().paraPosicao();
						// pega a posica possivel da lista
						Posicao destino = new Posicao(i, j);
						Peca pecaCap = movePeca(origem, destino);//Movimenta a peca para testar se esta em check
						boolean testaCheck = testeCheck(cor);
						desfazMovi(origem, destino, pecaCap);
						if (!testaCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	
	public void novaPeca(char col, int lin, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(col, lin).paraPosicao());
		pecasTabuleiro.add(peca);// acrescenta na lista de pecas no tabuleiro
	}
	public void inicialPosicao() {
		novaPeca('h', 7, new Torre(tabuleiro, Cor.BRANCA));
		novaPeca('d', 1, new Torre(tabuleiro, Cor.BRANCA));
		novaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCA));

        novaPeca('b', 8, new Torre(tabuleiro, Cor.PRETA));
        novaPeca('a', 8, new Rei(tabuleiro, Cor.PRETA));

	}
}
