package ufc.dc.tp1.app.main;

import ufc.dc.tp1.app.itens.Look;
import ufc.dc.tp1.app.itens.enums.Conservação;
import ufc.dc.tp1.app.itens.enums.Tamanho;
import ufc.dc.tp1.app.itens.vestuário.*;

public class Main {

	public static void main(String[] args) {
		Camisa camisa = new Camisa("camisa_red", "red", "nike", Conservação.BOA, Tamanho.M);
		Casaco casaco = new Casaco("casaco_blue", "blue", "C&A", Conservação.REGULAR, Tamanho.M);
		Calça calça = new Calça("calça_brown", "brown", "adidas", Conservação.EXCELENTE, 42);
		Relógio relogio = new Relógio("relogio_prata", "prata", "renner", Conservação.EXCELENTE);
		Boné boné = new Boné("boné_classic", "black", "riachuelo", Conservação.EXCELENTE);
		
		Look look1 = new Look();
		
		look1.montarLook(camisa,calça, relogio, casaco, boné);
		
		look1.registrarUso("10/12/2004", "Aniversário de Samuel");
		
		System.out.println(look1.toString());
		
		
	}

}
