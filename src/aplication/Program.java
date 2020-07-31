package aplication;

import java.util.Locale;
import java.util.Scanner;

import tabuleiro.Tabuleiro;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		Tabuleiro tabuleiro = new Tabuleiro(8, 8);
		
				sc.close();
		
	}

}
