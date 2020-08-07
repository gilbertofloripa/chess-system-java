package aplication;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
		List<PecaXadrez> capturadas = new ArrayList<>();
		
		while (!partidaXadrez.getCheckMate()) {
			try{
				UI.limpaTela();
				UI.imprimePartida(partidaXadrez, capturadas);
				
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
				if (pecaCapturada != null) { // pecas capturadas
					capturadas.add(pecaCapturada);
				}
				
				// Promocao de pecas
				if (partidaXadrez.getPromocao() != null) {
					System.out.print("Entre com a peca para promover Q/B/C/T: ");
					String tipo = sc.nextLine().toUpperCase();
					while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")) {
						System.out.print("Opção invalida, Entre com a peca para promover Q/B/C/T: ");
						tipo = sc.nextLine().toUpperCase();
					}

					partidaXadrez.trocaPecaPromovida(tipo);
				}
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
		UI.limpaTela();
		UI.imprimePartida(partidaXadrez, capturadas);
	}

}
