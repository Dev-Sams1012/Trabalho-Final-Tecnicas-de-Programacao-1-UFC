package ufc.dc.tp1.app.itens;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Lavagem {
	private LocalDate data;
	private List<ILavavel> itensLavados;
	
	
	public Lavagem(LocalDate data) {
        if (data == null) throw new IllegalArgumentException("Data da lavagem não pode ser nula.");
            
        this.data = data;
        
        itensLavados = new ArrayList<>();
	}
	
	public Lavagem() {     
		this.data = LocalDate.now();
		
		this.itensLavados = new ArrayList<>();
	}
	
	public List<Item> lavarItens(Item... itens ) {
		if(itens == null || itens.length == 0) throw new IllegalArgumentException("Os itens não podem ser nulos.");
		
        List<Item> itensNaoLavaveis = new ArrayList<>();

        for (Item item : itens) {
            if (item instanceof ILavavel lavavel) {
                lavavel.registrarLavagem();
                itensLavados.add(lavavel);
            } else {
                itensNaoLavaveis.add(item);
            }
        }

        return itensNaoLavaveis;
    }
	
	public LocalDate getData() {
		return data;
	}
	
	public String getDataToString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		
		return data.format(formatter);
	}
	
	public List<ILavavel> getListaDeLavagem(){
		return itensLavados;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		
	    StringBuilder sb = new StringBuilder();
	    sb.append("Lavagem realizada em: ").append(data.format(formatter)).append("\n");
	    sb.append("Itens lavados:\n");

	    if (itensLavados.isEmpty()) {
	        sb.append("  Nenhum item lavado ainda.\n");
	    } else {
	        for (ILavavel item : itensLavados) {
	            sb.append("  - ").append(item).append("\n");
	        }
	    }

	    return sb.toString();
	}

}