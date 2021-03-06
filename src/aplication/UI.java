package aplication;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class UI {
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	// codigo de cores para o terminal git
	// cores das letras
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	// cores do fundo
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void limpaTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}	

	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			char col = s.charAt(0);
			int lin = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(col, lin);
		}
		catch (RuntimeException e){
			throw new XadrezException("Erro na Posicao do Xadrez, tem que se de a1 a h8");
		}
	}
	
	public static void imprimePartida(PartidaXadrez partidaXadrez, List<PecaXadrez> capturadas) {
		imprimeTab(partidaXadrez.getPecas());
		System.out.println();
		imprimePecasCap(capturadas);
		System.out.println("Turno: " + partidaXadrez.getTurno());
		System.out.println("Aguardando jogador cor: " + partidaXadrez.getjogadorAtual());
		if (!partidaXadrez.getCheckMate()) {
			if (partidaXadrez.getCheck()) {
				System.out.println("****** CHECK! ******");
			}
		}
		else {
			System.out.println("CHECK MATE!");
			System.out.println("Vencedor: " + partidaXadrez.getjogadorAtual());
		}
			
	}
	public static void imprimeTab(PecaXadrez[][] pecas) {
		for (int i=0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j=0; j < pecas.length; j++) {
				imprimePeca(pecas[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void imprimeTab(PecaXadrez[][] pecas, boolean[][] moviPossiveis) {
		for (int i=0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j=0; j < pecas.length; j++) {
				imprimePeca(pecas[i][j], moviPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void imprimePeca(PecaXadrez peca) {
		if (peca == null) {
			System.out.print("-");
		}
		else {
            if (peca.getCor() == Cor.BRANCA) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	private static void imprimePeca(PecaXadrez peca, boolean corFundo){
		if (corFundo) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		}
		else {
            if (peca.getCor() == Cor.BRANCA) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	private static void imprimePecasCap(List<PecaXadrez> capurada) {
		//Cria uma lista a partira da lista geral com filtor so brancas
		List<PecaXadrez> branca = capurada.stream().filter(x -> x.getCor() == Cor.BRANCA).collect(Collectors.toList());
		List<PecaXadrez> preta = capurada.stream().filter(x -> x.getCor() == Cor.PRETA).collect(Collectors.toList());
		System.out.println("Pe�as capturadas:");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(branca.toArray()));
		System.out.print(ANSI_RESET);

		System.out.print("Pretas: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(preta.toArray()));
		System.out.print(ANSI_RESET);
	}

}
