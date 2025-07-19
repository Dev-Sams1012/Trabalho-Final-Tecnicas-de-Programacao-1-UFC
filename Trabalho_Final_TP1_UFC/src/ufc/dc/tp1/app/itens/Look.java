package ufc.dc.tp1.app.itens;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;

public class Look {
	private Map<CategoriaRoupa, Item> roupas;
	private List<UtilizacaoDeLook> utilizacao;
	
	public Look() {
		this.roupas = new HashMap<>();
		this.utilizacao = new ArrayList<>();
	}
	
	public void montarLook(Item... itens) {
	    if (itens == null || itens.length == 0) return;

	    for (Item item : itens) {
	        if (item != null && item.getCategoria() != null) {
	            if (roupas.containsKey(item.getCategoria()) == false) {
	                roupas.put(item.getCategoria(), item);
	            }
	        }
	    }
	}
	
	public boolean modificarItem(Item item) {
		if(item == null || !roupas.containsKey(item.getCategoria())) return false;
		this.roupas.put(item.getCategoria(), item);
		return true;
	}
	
	public boolean removerItem(CategoriaRoupa parte) {
        return roupas.remove(parte) != null;
    }
	
	public void registrarUso(LocalDate data, String descricao) {
	    if (data != null && descricao != null) {
	    	utilizacao.add(new UtilizacaoDeLook(data, descricao));
	    }
	}
	
	public void registrarUso(String descricao) {
	    if (descricao != null) {
	    	utilizacao.add(new UtilizacaoDeLook(LocalDate.now(), descricao));
	    }
	}

	public int getNumeroDeUsos() {
		return utilizacao.size();
	}
	
	public List<UtilizacaoDeLook> getHistoricoDeUsos() {
        return new ArrayList<>(utilizacao);
    }

}
 