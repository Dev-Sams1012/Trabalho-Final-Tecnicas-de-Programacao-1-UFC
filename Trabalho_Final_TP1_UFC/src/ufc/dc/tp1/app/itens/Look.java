package ufc.dc.tp1.app.itens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;

public class Look {
	private Map<CategoriaRoupa, Item> roupas;
	private List<UtilizaçãoDeLook> utilização;
	
	public Look() {
		this.roupas = new HashMap<>();
		this.utilização = new ArrayList<>();
	}
	
	public void montarLook(Item... itens) {
	    if (itens == null || itens.length == 0) return;

	    for (Item item : itens) {
	        if (item != null && item.getParteDoCorpo() != null) {
	            if (!roupas.containsKey(item.getParteDoCorpo())) {
	                roupas.put(item.getParteDoCorpo(), item);
	            }
	        }
	    }
	}
	
	public boolean modificarItem(Item item) {
		if(item == null || !roupas.containsKey(item.getParteDoCorpo())) return false;
		this.roupas.put(item.getParteDoCorpo(), item);
		return true;
	}
	
	public boolean removerItem(CategoriaRoupa parte) {
        return roupas.remove(parte) != null;
    }
	
	public void registrarUso(String data, String descricao) {
	    if (data != null && descricao != null && !data.isBlank()) {
	        utilização.add(new UtilizaçãoDeLook(data, descricao));
	    }
	}

	public int getNumeroDeUsos() {
		return utilização.size();
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Look:\n");

	    if (roupas.isEmpty()) {
	        sb.append("  (Nenhuma peça cadastrada neste look)\n");
	    } else {
	        for (Map.Entry<CategoriaRoupa, Item> entry : roupas.entrySet()) {
	            sb.append("  - ").append(entry.getKey()).append(": ")
	              .append(entry.getValue()).append("\n");
	        }
	    }

	    sb.append("\nUtilizações: ").append(utilização.size()).append("\n");

	    if (!utilização.isEmpty()) {
	        for (UtilizaçãoDeLook uso : utilização) {
	            sb.append("  • ").append(uso).append("\n");
	        }
	    }

	    return sb.toString();
	}
	
	
}
 