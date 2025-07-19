package ufc.dc.tp1.app.itens.vestuário;

import java.time.LocalDate;
import ufc.dc.tp1.app.exceptions.DevolucaoSemEmprestimoException;
import ufc.dc.tp1.app.exceptions.VestimentaJaEmprestadoException;
import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.ILavavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;
import ufc.dc.tp1.app.itens.enums.Conservacao;
import ufc.dc.tp1.app.itens.enums.Tamanho;

public class VestimentaSuperiorExterno extends Item implements IEmprestavel, ILavavel, VestimentaTamanhoEnum {
	private static final long serialVersionUID = 1L;
	
	private final Tamanho tamanho;
	private boolean emprestada = false;
	private LocalDate dataDeEmprestimo = null;
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
		dataDeEmprestimo = LocalDate.now();
	}

	@Override
	public int quantidadeDeDiasDesdeOEmprestimo() {
		if(emprestada == true) {
			return LocalDate.now().getDayOfYear() - getDataDeEmprestimo().getDayOfYear();
		}
		return 0;		
	}

	@Override
	public void registrarDevolucao() throws DevolucaoSemEmprestimoException {
		if(emprestada == false) throw new DevolucaoSemEmprestimoException(getId());
		emprestada = false;
		dataDeEmprestimo = null;
	}
	
	@Override
	public boolean isEmprestada() {
		return emprestada;
	}

	@Override
	public LocalDate getDataDeEmprestimo() {
		return dataDeEmprestimo;
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