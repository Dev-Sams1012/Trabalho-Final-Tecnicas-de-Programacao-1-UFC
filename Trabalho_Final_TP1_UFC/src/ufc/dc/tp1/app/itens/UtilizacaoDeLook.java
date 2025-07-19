package ufc.dc.tp1.app.itens;

import java.time.LocalDate;

public class UtilizacaoDeLook {
	private LocalDate data;
	private String descricao;
	
	
	public UtilizacaoDeLook(LocalDate data, String descricao) {
		if (data == null) throw new IllegalArgumentException("Data não pode ser vazia.");
		if (descricao == null || descricao.isBlank()) throw new IllegalArgumentException("Descrição não pode ser vazia.");

		this.data = data;
		this.descricao = descricao;
	}
	
	public void setDescricao(String descricao) {
		if (descricao == null || descricao.isBlank()) throw new IllegalArgumentException("Descrição não pode ser vazia.");
		
		this.descricao = descricao;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return "UtilizaçãoDeLook [data= " + data + ", descrição= " + descricao + "]";
	}
	

}
