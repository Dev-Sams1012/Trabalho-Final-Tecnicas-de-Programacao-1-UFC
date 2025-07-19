package ufc.dc.tp1.app.itens.vestuário;

import java.io.Serializable;

import ufc.dc.tp1.app.exceptions.DevolucaoSemEmprestimoException;
import ufc.dc.tp1.app.exceptions.VestimentaJaEmprestadoException;
import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.ILavavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.enums.Conservacao;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;
import ufc.dc.tp1.app.itens.enums.Tamanho;

public class VestimentaSuperiorExterno extends Item implements IEmprestavel, ILavavel, VestimentaTamanhoEnum, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Tamanho tamanho;
	private boolean emprestada = false;
	private int diasDeEmprestimo = 0;
	private boolean lavada = true;
    private int numeroLavagens = 0;

	public VestimentaSuperiorExterno(String id, String cor, String loja, Conservacao conservacao, Tamanho tamanho) {
		super(id, cor, loja, conservacao, CategoriaRoupa.SUPERIOR_EXTERNO);
		this.tamanho = tamanho;
	}
	
	@Override
	public Tamanho getTamanho() {
		return tamanho;
	}

	@Override
	public void registrarEmprestimo() throws VestimentaJaEmprestadoException {
		if(emprestada == true) throw new VestimentaJaEmprestadoException(getId());
		
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
	public void registrarDevolucao() throws DevolucaoSemEmprestimoException {
		if(emprestada == false) throw new DevolucaoSemEmprestimoException(getId());
		emprestada = false;
		diasDeEmprestimo = 0;
	}
	
	@Override
	public boolean isEmprestada() {
		return emprestada;
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
	public void usouItem() {
		lavada = false;
	}
	
	@Override
	public boolean isLavada() {
		return lavada;
	}
	
	@Override
	public String toString() {
		return "Item [id= " + getId() + ", cor= " + getCor() + ", lojaOrigem= " + getLojaOrigem() + ", conservacao= " + getConservacao()
				+ ", parteDoCorpo= " + getCategoria() + ", tamanho= " + getTamanho() + "]";
	}
	
	
}