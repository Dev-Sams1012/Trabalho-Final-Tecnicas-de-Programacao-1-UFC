package ufc.dc.tp1.app.itens;

public abstract class Item {
	private String id;
	private String cor;
	private String loja_origem;
	private Conservacao conservacao;
	private ParteDoCorpo parte_do_corpo;

	public Item(String id, String cor, String loja, Conservacao conservacao, ParteDoCorpo parte_do_corpo) {
		this.id = id;
		this.cor = cor;
		this.loja_origem = loja;
		this.conservacao = conservacao;
		this.parte_do_corpo = parte_do_corpo;
	}

	public String getId() {
		return id;
	}

	public String getCor() {
		return cor;
	}
	
	public String getLojaOrigem() {
		return loja_origem;
	}

	public Conservacao getConservacao() {
		return conservacao;
	}
	
	public ParteDoCorpo getParteDoCorpo() {
		return parte_do_corpo;
	}

	
}
