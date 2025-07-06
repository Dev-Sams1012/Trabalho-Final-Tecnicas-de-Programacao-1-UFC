package ufc.dc.tp1.app.itens;

import ufc.dc.tp1.app.exceptions.DevolucaoSemEmprestimoException;

public interface IEmprestavel {
	public void registrarEmprestimo();
	
	public int  quantidadeDeDiasDesdeOEmprestimo();
	
	public void registrarDevolucao() throws DevolucaoSemEmprestimoException;
}
