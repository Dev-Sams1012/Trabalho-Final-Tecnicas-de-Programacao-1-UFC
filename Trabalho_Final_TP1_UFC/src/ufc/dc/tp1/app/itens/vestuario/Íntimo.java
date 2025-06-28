package ufc.dc.tp1.app.itens.vestuario;

import ufc.dc.tp1.app.itens.Conservacao;
import ufc.dc.tp1.app.itens.ILavavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.ParteDoCorpo;
import ufc.dc.tp1.app.itens.Tamanho;

public class Íntimo extends Item implements ILavavel {
	
	private Tamanho tamanho;
    private int numeroLavagens = 0;

	public Íntimo(String id, String cor, String loja, Conservacao conservacao, Tamanho tamanho) {
		super(id, cor, loja, conservacao, ParteDoCorpo.ÍNTIMO);
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
	
}
