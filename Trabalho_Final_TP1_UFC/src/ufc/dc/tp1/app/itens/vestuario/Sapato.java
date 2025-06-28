package ufc.dc.tp1.app.itens.vestuario;

import ufc.dc.tp1.app.itens.Conservacao;
import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.ParteDoCorpo;

public class Sapato extends Item implements IEmprestavel {
	
	private int tamanho;
	private int diasDeEmprestimo = 0;
    private boolean emprestada = false;

	public Sapato(String id, String cor, String loja, Conservacao conservacao, int tamanho) {
		super(id, cor, loja, conservacao, ParteDoCorpo.CALÇADO);
		this.tamanho = tamanho;
	}
	
	public int getTamanho() {
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
	
}
