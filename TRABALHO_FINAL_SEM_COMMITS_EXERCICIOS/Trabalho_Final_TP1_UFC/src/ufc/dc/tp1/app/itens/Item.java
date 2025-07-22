package ufc.dc.tp1.app.itens;

import java.io.Serializable;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;
import ufc.dc.tp1.app.itens.enums.Conservacao;

public abstract class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String cor;
	private String lojaOrigem;
	private Conservacao conservacao;
	private CategoriaRoupa categoria;
	private String caminhoImagem; 
	private int quantidadeUsos = 0;

	public Item(String id, String cor, String loja, Conservacao conservacao, CategoriaRoupa categoria) {
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

	public Item(String id, String cor, String loja, Conservacao conservacao, CategoriaRoupa categoria, String caminhoImagem) {
		if (id == null || id.isBlank()) throw new IllegalArgumentException("ID não pode ser nulo ou vazio.");
		if (cor == null || cor.isBlank()) throw new IllegalArgumentException("Cor não pode ser nula ou vazia.");
		if (loja == null || loja.isBlank()) throw new IllegalArgumentException("Loja não pode ser nula ou vazia.");
		if (conservacao == null) throw new IllegalArgumentException("Conservação não pode ser nula.");
		if (categoria == null) throw new IllegalArgumentException("Categoria de Roupa não pode ser nula.");
		if (caminhoImagem == null || caminhoImagem.isBlank()) throw new IllegalArgumentException("Caminho da imagem não pode ser nulo ou vazio.");
		
		this.id = id;
		this.cor = cor;
		this.lojaOrigem = loja;
		this.conservacao = conservacao;
		this.categoria = categoria;
		this.caminhoImagem = caminhoImagem;
	}

	public String getId() {
		return id;
	}

	public String getCor() {
		return cor;
	}
	
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public String getLojaOrigem() {
		return lojaOrigem;
	}
	
	public void setLojaOrigem(String lojaOrigem) {
		this.lojaOrigem = lojaOrigem;
	}

	public Conservacao getConservacao() {
		return conservacao;
	}
	
	public void setConservacao(Conservacao conservacao) {
		this.conservacao = conservacao;
	}
	
	public CategoriaRoupa getCategoria() {
		return categoria;
	}
	
	public void setCategoriaRoupa(CategoriaRoupa categoria) {
		this.categoria = categoria;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}
	
	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public int getQuantidadeUsos(){
		return quantidadeUsos;
	}

	public void registrarUso(){
		this.quantidadeUsos += 1;
	}
	
	@Override
    public String toString() {
        return id + " (" + categoria + ")";
    }
	
}