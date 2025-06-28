package ufc.dc.tp1.app.itens.vestuario;

import ufc.dc.tp1.app.itens.Conservacao;
import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.ILavavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.ParteDoCorpo;
import ufc.dc.tp1.app.itens.Tamanho;

public class Casaco extends Item implements ILavavel, IEmprestavel {
	
	private Tamanho tamanho;
	private int diasDeEmprestimo = 0;
    private int numeroLavagens = 0;
    private boolean emprestada = false;

	public Casaco(String id, String cor, String loja, Conservacao conservacao, Tamanho tamanho) {
		super(id, cor, loja, conservacao, ParteDoCorpo.SUPERIOR);
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
	
}
