package ufc.dc.tp1.app.itens;

public class UtilizaçãoDeLook {
	private String data;
	private String descrição;
	
	public UtilizaçãoDeLook(String data, String descrição) {
		this.data = data;
		this.descrição = descrição;
	}

	@Override
	public String toString() {
		return "UtilizaçãoDeLook [data= " + data + ", descrição= " + descrição + "]";
	}
	

}
