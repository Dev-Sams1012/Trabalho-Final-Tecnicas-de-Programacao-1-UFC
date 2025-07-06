package ufc.dc.tp1.app.itens;

import ufc.dc.tp1.app.itens.enums.Conservação;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;

public abstract class Item {
	private String id;
	private String cor;
	private String lojaOrigem;
	private Conservação conservacao;
	private CategoriaRoupa categoria;

	public Item(String id, String cor, String loja, Conservação conservacao, CategoriaRoupa categoria) {
		if (id == null || id.isBlank()) throw new IllegalArgumentException("ID não pode ser nulo ou vazio.");
		if (cor == null || cor.isBlank()) throw new IllegalArgumentException("Cor não pode ser nula ou vazia.");
		if (loja == null || loja.isBlank()) throw new IllegalArgumentException("Loja não pode ser nula ou vazia.");
		if (conservacao == null) throw new IllegalArgumentException("Conservação não pode ser nula.");
		if (categoria == null) throw new IllegalArgumentException("Categoria de Roupa não pode ser nula.");
		
		this.id = id;
		this.cor = cor;
		this.lojaOrigem = loja;
		this.conservacao = conservacao;
		this.categoria = categoria;
	}

	public String getId() {
		return id;
	}

	public String getCor() {
		return cor;
	}
	
	public String getLojaOrigem() {
		return lojaOrigem;
	}

	public Conservação getConservacao() {
		return conservacao;
	}
	
	public CategoriaRoupa getCategoria() {
		return categoria;
	}

	@Override
	public String toString() {
		return "Item [id= " + id + ", cor= " + cor + ", lojaOrigem= " + lojaOrigem + ", conservacao= " + conservacao
				+ ", parteDoCorpo= " + categoria + "]";
	}

	
}
