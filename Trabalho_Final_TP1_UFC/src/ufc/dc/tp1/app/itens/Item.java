package ufc.dc.tp1.app.itens;

import ufc.dc.tp1.app.itens.enums.Conservação;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;

public abstract class Item {
	private String id;
	private String cor;
	private String lojaOrigem;
	private Conservação conservacao;
	private CategoriaRoupa parteDoCorpo;

	public Item(String id, String cor, String loja, Conservação conservacao, CategoriaRoupa parte_do_corpo) {
		this.id = id;
		this.cor = cor;
		this.lojaOrigem = loja;
		this.conservacao = conservacao;
		this.parteDoCorpo = parte_do_corpo;
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
	
	public CategoriaRoupa getParteDoCorpo() {
		return parteDoCorpo;
	}

	@Override
	public String toString() {
		return "Item [id= " + id + ", cor= " + cor + ", lojaOrigem= " + lojaOrigem + ", conservacao= " + conservacao
				+ ", parteDoCorpo= " + parteDoCorpo + "]";
	}

	
}
