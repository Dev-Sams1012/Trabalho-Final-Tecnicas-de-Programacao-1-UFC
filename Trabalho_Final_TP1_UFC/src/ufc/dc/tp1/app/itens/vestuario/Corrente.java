package ufc.dc.tp1.app.itens.vestuario;

import ufc.dc.tp1.app.itens.Conservacao;
import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.ParteDoCorpo;

public class Corrente extends Item implements IEmprestavel {
	
	private int diasDeEmprestimo = 0;
    private boolean emprestada = false;

	public Corrente(String id, String cor, String loja, Conservacao conservacao) {
		super(id, cor, loja, conservacao, ParteDoCorpo.ACESSÓRIO);
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
	
}
