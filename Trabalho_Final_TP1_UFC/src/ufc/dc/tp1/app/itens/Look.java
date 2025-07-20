package ufc.dc.tp1.app.itens;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;

public class Look implements Serializable {
	private final Map<CategoriaRoupa, Item> roupas;
	private final List<UtilizacaoDeLook> utilizacao;
	private final String nome;
	
	public Look(String nome) {
		this.roupas = new HashMap<>();
		this.utilizacao = new ArrayList<>();
		this.nome = nome;
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

	public void montarLook(List<Item> itens) {
		if (itens == null || itens.isEmpty()) return;

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

	public Map<CategoriaRoupa, Item> getRoupas() {
		return new HashMap<>(roupas);
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
    	return "Look #" + hashCode();
	}

}
 