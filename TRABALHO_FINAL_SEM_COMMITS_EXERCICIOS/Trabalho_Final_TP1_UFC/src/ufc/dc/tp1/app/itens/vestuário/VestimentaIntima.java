package ufc.dc.tp1.app.itens.vestuário;


import ufc.dc.tp1.app.itens.ILavavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;
import ufc.dc.tp1.app.itens.enums.Conservacao;
import ufc.dc.tp1.app.itens.enums.Tamanho;

public class VestimentaIntima extends Item implements ILavavel, VestimentaTamanhoEnum {
	private static final long serialVersionUID = 1L;
	
	private boolean lavada = true;
    private int numeroLavagens = 0;
    private final Tamanho tamanho;

	public VestimentaIntima(String id, String cor, String loja, Conservacao conservacao, Tamanho tamanho) {
		super(id, cor, loja, conservacao, CategoriaRoupa.ÍNTIMO);
		this.tamanho = tamanho;
	}
	
	@Override
	public Tamanho getTamanho() {
		return tamanho;
	}
	
	@Override
	public void registrarLavagem() {
		numeroLavagens++;
		lavada = true;
	}

	@Override
	public int getNumeroLavagens() {
		return numeroLavagens;
	}
	
	@Override
	public void registrarUso() {
		super.registrarUso();
		lavada = false;
	}
	
	@Override
	public boolean isLavada() {
		return lavada;
	}
	
	@Override
	public String toString() {
		return "Item [id= " + getId() + ", cor= " + getCor() + ", lojaOrigem= " + getLojaOrigem() + ", conservacao= " + getConservacao()
				+ ", parteDoCorpo= " + getCategoria() + "]";
	}


}