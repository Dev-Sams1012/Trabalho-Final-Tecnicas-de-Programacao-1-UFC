package ufc.dc.tp1.app.itens;

public class UtilizaçãoDeLook {
	private String data;
	private String descricao;
	
	public UtilizaçãoDeLook(String data, String descricao) {
		if (data == null || data.isBlank()) throw new IllegalArgumentException("Data não pode ser vazia.");
		if (descricao == null || descricao.isBlank()) throw new IllegalArgumentException("Descrição não pode ser vazia.");

		this.data = data;
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "UtilizaçãoDeLook [data= " + data + ", descrição= " + descricao + "]";
	}
	

}
