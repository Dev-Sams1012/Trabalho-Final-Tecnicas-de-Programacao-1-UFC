package ufc.dc.tp1.app.itens.vestuário;

import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.enums.Conservacao;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;

import java.io.Serializable;

import ufc.dc.tp1.app.exceptions.DevolucaoSemEmprestimoException;
import ufc.dc.tp1.app.exceptions.VestimentaJaEmprestadoException;

public class VestimentaAcessorio extends Item implements IEmprestavel, Serializable {
	private static final long serialVersionUID = 1L;
	
	private int diasDeEmprestimo = 0;
    private boolean emprestada = false;

	public VestimentaAcessorio(String id, String cor, String loja, Conservacao conservacao) {
		super(id, cor, loja, conservacao, CategoriaRoupa.ACESSÓRIO);
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

}