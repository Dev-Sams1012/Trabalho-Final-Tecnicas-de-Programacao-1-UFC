package ufc.dc.tp1.app.itens.vestuário;

import java.time.LocalDate;
import ufc.dc.tp1.app.exceptions.DevolucaoSemEmprestimoException;
import ufc.dc.tp1.app.exceptions.VestimentaJaEmprestadoException;
import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;
import ufc.dc.tp1.app.itens.enums.Conservacao;

public class VestimentaCabeca extends Item implements IEmprestavel {
	private LocalDate dataDeEmprestimo = null;
    private boolean emprestada = false;

	public VestimentaCabeca(String id, String cor, String loja, Conservacao conservacao) {
		super(id, cor, loja, conservacao, CategoriaRoupa.CABEÇA);
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

}