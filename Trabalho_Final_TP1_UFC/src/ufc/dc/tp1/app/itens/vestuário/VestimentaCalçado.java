package ufc.dc.tp1.app.itens.vestuário;

import ufc.dc.tp1.app.exceptions.DevolucaoSemEmprestimoException;
import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.enums.Conservação;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;

public abstract class VestimentaCalçado extends Item implements IEmprestavel {
	
	private int tamanho;
	private int diasDeEmprestimo = 0;
    private boolean emprestada = false;

	public VestimentaCalçado(String id, String cor, String loja, Conservação conservacao, int tamanho) {
		super(id, cor, loja, conservacao, CategoriaRoupa.CALÇADO);
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
	public void registrarDevolucao() throws DevolucaoSemEmprestimoException {
		if(emprestada == false) throw new DevolucaoSemEmprestimoException(getId());
		emprestada = false;
		diasDeEmprestimo = 0;
	}
	
	@Override
	public String toString() {
		return "Item [id=" + getId() + ", cor=" + getCor() + ", lojaOrigem=" + getLojaOrigem() + ", conservacao=" + getConservacao()
				+ ", parteDoCorpo=" + getCategoria() + ", tamanho= " + getTamanho() + "]";
	}
	
}
