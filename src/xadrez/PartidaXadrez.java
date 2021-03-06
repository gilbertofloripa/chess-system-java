package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;


public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual; 
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez enPassant;
	private PecaXadrez promocao;
	
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
	
	public PecaXadrez getEnPassant() {
		return enPassant;
	}

	public PecaXadrez getPromocao() {
		return promocao;
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
			throw new XadrezException("Posi��o ilegal, voce n�o pode se colocar em CHECK!");
		}

		// guarda a peca movimentada
		PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(dest);
		// Promocao
		promocao = null;
		if (pecaMovida instanceof Peao) {
			if ((pecaMovida.getCor() == Cor.BRANCA && dest.getLin() == 0)
			|| (pecaMovida.getCor() == Cor.PRETA && dest.getLin() == 7)) {
				promocao = ((PecaXadrez)tabuleiro.peca(dest));
				promocao = trocaPecaPromovida("Q");
			}
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
		// Verfica se o Piao andou duas casas, ai ele fica vulneravel a enpassant
		if (pecaMovida instanceof Peao && (destino.getLin() == origem.getLin() - 2
				|| destino.getLin() == origem.getLin() + 2 )) {
			enPassant = pecaMovida;
		}
		else {
			enPassant = null;
		}
		return (PecaXadrez) pecaCapturada;
	}
	
	public PecaXadrez trocaPecaPromovida(String tipo) {
		if (promocao == null) {
			throw new IllegalStateException("N�o tem a peca para ser promovida"); 
		}
		if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")) {
			//throw new InvalidParameterException ("Tipo invalido para promocao");
			return promocao;
		}
		Posicao pos = promocao.getPosicaoXadrez().paraPosicao();
		Peca peca = tabuleiro.removePeca(pos);
		pecasTabuleiro.remove(peca);
		PecaXadrez novaPeca = novaPeca(tipo, promocao.getCor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecasTabuleiro.add(novaPeca);
		return novaPeca;
	}
	
	private PecaXadrez novaPeca(String tipo, Cor cor) {
		if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if (tipo.equals("T")) return new Torre(tabuleiro, cor);
		if (tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		return new Rainha(tabuleiro, cor);
	}
	
	private void validaPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temPeca(posicao)) {
			throw new XadrezException("N�o existe peca na posicao de origem");
		}
		// Valida se a cor da peca e do jogador atual
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("Essa peca n�o e sua");
		}
		
		// valida se existe algum posicao valida
		if (!tabuleiro.peca(posicao).ePossivelMovimentar()) {
			throw new XadrezException("Essa peca n�o pode ser movimentada");
		}
	}
	
	private void validaPosicaoDestino(Posicao orig, Posicao dest) {
		// Verifica a peca na posicao origem se e possivel mov para o destino
		if (!tabuleiro.peca(orig).movimentoPossivel(dest)) {
			throw new XadrezException("A peca escolhida n�o pode mover-se para a posicao destino");
		}
	}

	private Peca movePeca(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(origem);// remove a peca da origem e guarda a peca em p
		p.adicContadorMovi();
		Peca pecaCapturada = tabuleiro.removePeca(destino);// remove a peca do destino se houver e guarda
		tabuleiro.colocarPeca(p, destino);// coloca a peca p na posicao destino
		if (pecaCapturada != null) {
			pecasTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		// Movimento de Rock
		if (p instanceof Rei && destino.getCol() == origem.getCol() +2) {
			Posicao origemT = new Posicao(origem.getLin(), origem.getCol() + 3);
			Posicao destinoT = new Posicao(origem.getLin(), origem.getCol() + 1);
			PecaXadrez rock = (PecaXadrez)tabuleiro.removePeca(origemT);
			tabuleiro.colocarPeca(rock, destinoT); // coloca a torre no novo lugar rock
			rock.adicContadorMovi();
		}
		
		// enpassant
		if (p instanceof Peao) {
			// Se o peao andou na vertical e nao e captura e um enpassant
			if (origem.getCol() != destino.getCol() && pecaCapturada == null) {
				Posicao peaoPosicao;
				// Captura o peao oponente
				if (p.getCor() == Cor.BRANCA) {
					peaoPosicao = new Posicao(destino.getLin() + 1, destino.getCol());
				}
				else {
					peaoPosicao = new Posicao(destino.getLin() - 1, destino.getCol());
				}		
				pecaCapturada = tabuleiro.removePeca(peaoPosicao);
				pecasCapturadas.add(pecaCapturada);
				pecasTabuleiro.remove(pecaCapturada);
			}
		}
		
		return pecaCapturada;
	}
	
	private void desfazMovi(Posicao origem, Posicao destino, Peca pecaCapturada){
		// desfaz o movimento quando se movimenta para uma posica de xeque
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(destino);
		p.subContadorMovi();
		tabuleiro.colocarPeca(p, origem); // volta a peca para origem
		
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino); // volta a peca capturada
			pecasCapturadas.remove(pecaCapturada);
			pecasTabuleiro.add(pecaCapturada);
		}
		
		// enpassant
		if (p instanceof Peao) {
			// Se o peao andou na vertical e nao e captura e um enpassant
			if (origem.getCol() != destino.getCol() && pecaCapturada == enPassant) {
				PecaXadrez peao = (PecaXadrez)tabuleiro.removePeca(destino);
				Posicao peaoPosicao;
				// Captura o peao oponente
				if (p.getCor() == Cor.BRANCA) {
					peaoPosicao = new Posicao(3, destino.getCol());
				}
				else {
					peaoPosicao = new Posicao(4, destino.getCol());
				}
				tabuleiro.colocarPeca(peao, peaoPosicao);
			}
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
		throw new IllegalStateException("N�o existe Rei da cor " + cor + " no tabuleiro, erro de sistema");  
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
		novaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCA));
		novaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		novaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCA));
		novaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCA));
		novaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCA, this));
		novaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCA));
		novaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		novaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCA));
		novaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		novaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		novaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		novaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		novaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		novaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		novaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		//novaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCA, this));

        novaPeca('a', 8, new Torre(tabuleiro, Cor.PRETA));
		novaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
		novaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETA));
		novaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETA));
        novaPeca('e', 8, new Rei(tabuleiro, Cor.PRETA, this));
		novaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETA));
		novaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
        //novaPeca('h', 8, new Torre(tabuleiro, Cor.PRETA));
        novaPeca('a', 7, new Peao(tabuleiro, Cor.PRETA, this));
        novaPeca('b', 7, new Peao(tabuleiro, Cor.PRETA, this));
        novaPeca('c', 7, new Peao(tabuleiro, Cor.PRETA, this));
        novaPeca('d', 7, new Peao(tabuleiro, Cor.PRETA, this));
        novaPeca('e', 7, new Peao(tabuleiro, Cor.PRETA, this));
        novaPeca('f', 7, new Peao(tabuleiro, Cor.PRETA, this));
        novaPeca('g', 7, new Peao(tabuleiro, Cor.PRETA, this));
        //novaPeca('h', 7, new Peao(tabuleiro, Cor.PRETA, this));
		novaPeca('h', 7, new Peao(tabuleiro, Cor.BRANCA, this));
	}
}
