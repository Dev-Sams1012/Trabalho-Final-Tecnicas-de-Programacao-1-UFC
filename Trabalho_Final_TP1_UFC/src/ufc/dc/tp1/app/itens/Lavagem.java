package ufc.dc.tp1.app.itens;

import java.util.ArrayList;
import java.util.List;

public class Lavagem {
	private String data;
	private List<ILavavel> itensLavados;
	
	public Lavagem(String data) {
        if (data == null || data.isBlank()) {
            throw new IllegalArgumentException("Data da lavagem não pode ser nula ou vazia.");
        }
        this.data = data;
        this.itensLavados = new ArrayList<>();
	}
	
	public List<Item> lavarItens(List<Item> itens) {
        List<Item> itensNaoLavaveis = new ArrayList<>();

        for (Item item : itens) {
            if (item instanceof ILavavel) {
                ILavavel lavavel = (ILavavel) item;
                lavavel.registrarLavagem();
                itensLavados.add(lavavel);
            } else {
                itensNaoLavaveis.add(item);
            }
        }

        return itensNaoLavaveis;
    }

	
	public String getData() {
		return data;
	}
	
	public List<ILavavel> getListaDeLavagem(){
		return itensLavados;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Lavagem realizada em: ").append(data).append("\n");
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
