package ufc.dc.tp1.app.itens;

import java.time.LocalDate;
import ufc.dc.tp1.app.exceptions.DevolucaoSemEmprestimoException;
import ufc.dc.tp1.app.exceptions.VestimentaJaEmprestadoException;

public interface IEmprestavel {
	public void registrarEmprestimo() throws VestimentaJaEmprestadoException;
	
	public int quantidadeDeDiasDesdeOEmprestimo();

	public LocalDate getDataDeEmprestimo();
	
	public void registrarDevolucao() throws DevolucaoSemEmprestimoException;
	
	public boolean isEmprestada();
} 