package aplication;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

//import tabuleiro.Tabuleiro;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while (true) {
			try{
				UI.limpaTela();
				UI.imprimePartida(partidaXadrez);
				
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean[][] moviPossiveis = partidaXadrez.possivelMovimentos(origem);
				UI.limpaTela();
				UI.imprimeTab(partidaXadrez.getPecas(), moviPossiveis);
				
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				
				PecaXadrez pecaCapturada = partidaXadrez.moveXadrez(origem, destino);
			}
			catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();// pausa apos msg
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();// pausa apos msg
			}
		}
		
	}

}
