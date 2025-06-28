package ufc.dc.tp1.app.itens.vestuário;

import ufc.dc.tp1.app.itens.ILavavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.enums.Conservação;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;
import ufc.dc.tp1.app.itens.enums.Tamanho;

public class Íntimo extends Item implements ILavavel {
	
	private Tamanho tamanho;
    private int numeroLavagens = 0;

	public Íntimo(String id, String cor, String loja, Conservação conservacao, Tamanho tamanho) {
		super(id, cor, loja, conservacao, CategoriaRoupa.ÍNTIMO);
		this.tamanho = tamanho;
	}
	
	public Tamanho getTamanho() {
		return tamanho;
	}

	@Override
	public void registrarLavagem() {
		numeroLavagens++;
		
	}

	@Override
	public int getNumeroLavagens() {
		return numeroLavagens;
	}
	
	@Override
	public String toString() {
		return "Item [id=" + getId() + ", cor=" + getCor() + ", lojaOrigem=" + getLojaOrigem() + ", conservacao=" + getConservacao()
				+ ", parteDoCorpo=" + getParteDoCorpo() + ", tamanho= " + getTamanho() + "]";
	}
	
}
