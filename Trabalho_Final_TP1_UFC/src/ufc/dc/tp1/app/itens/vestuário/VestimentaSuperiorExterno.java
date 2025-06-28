package ufc.dc.tp1.app.itens.vestuário;

import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.ILavavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.enums.Conservação;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;
import ufc.dc.tp1.app.itens.enums.Tamanho;

public abstract class VestimentaSuperiorExterno extends Item implements ILavavel, IEmprestavel {
	
	private Tamanho tamanho;
	private int diasDeEmprestimo = 0;
    private int numeroLavagens = 0;
    private boolean emprestada = false;

	public VestimentaSuperiorExterno(String id, String cor, String loja, Conservação conservacao, Tamanho tamanho) {
		super(id, cor, loja, conservacao, CategoriaRoupa.SUPERIOR_EXTERNO);
		this.tamanho = tamanho;
	}
	
	public Tamanho getTamanho() {
		return tamanho;
	}

	@Override
	public void registrarEmprestimo() {
		emprestada = true;
		diasDeEmprestimo = 0;
	}

	@Override
	public int quantidadeDeDiasDesdeOEmprestimo() {
		if(emprestada == true) {
			return diasDeEmprestimo;
		}
		return -1;		
	}

	@Override
	public void registrarDevolucao() {
		emprestada = false;
		diasDeEmprestimo = 0;
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
		return "Item [id= " + getId() + ", cor= " + getCor() + ", lojaOrigem= " + getLojaOrigem() + ", conservacao= " + getConservacao()
				+ ", parteDoCorpo= " + getParteDoCorpo() + ", tamanho= " + getTamanho() + "]";
	}
	
	
}
