package ufc.dc.tp1.app.itens.vestuário;

import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.enums.Conservação;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;

public abstract class VestimentaAcessório extends Item implements IEmprestavel {
	
	private int diasDeEmprestimo = 0;
    private boolean emprestada = false;

	public VestimentaAcessório(String id, String cor, String loja, Conservação conservacao) {
		super(id, cor, loja, conservacao, CategoriaRoupa.ACESSÓRIO);
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
