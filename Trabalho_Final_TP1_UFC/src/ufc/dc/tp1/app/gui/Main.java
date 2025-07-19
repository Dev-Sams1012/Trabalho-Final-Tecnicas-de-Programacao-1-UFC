package ufc.dc.tp1.app.main;

import java.util.ArrayList;
import java.util.List;


import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.Lavagem;
import ufc.dc.tp1.app.itens.Look;
import ufc.dc.tp1.app.itens.enums.Conservação;
import ufc.dc.tp1.app.itens.enums.Tamanho;
import ufc.dc.tp1.app.itens.vestuário.*;

public class Main {

	public static void main(String[] args) {
		// Criando itens
        Camisa camisa = new Camisa("camisa_red", "red", "nike", Conservação.BOA, Tamanho.M);
        Calça calca = new Calça("calca_brown", "brown", "adidas", Conservação.EXCELENTE, 42);
        Relógio relogio = new Relógio("relogio_prata", "prata", "renner", Conservação.EXCELENTE);
        
        // Lista de itens para lavagem
        List<Item> itensParaLavar = new ArrayList<>();
        itensParaLavar.add(camisa);    // lavável
        itensParaLavar.add(calca);     // lavável
        itensParaLavar.add(relogio);   // não lavável
        
        // Criando objeto Lavagem
        Lavagem lavagem = new Lavagem("28/06/2025");
        
        // Lavar itens e capturar os não laváveis
        List<Item> naoLavaveis = lavagem.lavarItens(itensParaLavar);
        
        // Exibindo resultados
        System.out.println("=== Itens lavados ===");
        System.out.println(lavagem.toString());
        
        if (!naoLavaveis.isEmpty()) {
            System.out.println("=== Itens não laváveis ===");
            for (Item item : naoLavaveis) {
                System.out.println(" - " + item.getId());
            }
        } else {
            System.out.println("Todos os itens foram lavados com sucesso!");
        }
    }
      
       
}


