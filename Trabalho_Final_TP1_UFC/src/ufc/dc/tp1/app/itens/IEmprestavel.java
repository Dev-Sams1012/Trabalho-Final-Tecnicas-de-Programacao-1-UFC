package ufc.dc.tp1.app.itens;

public interface IEmprestavel {
	public void registrarEmprestimo();
	
	public int  quantidadeDeDiasDesdeOEmprestimo();
	
	public void registrarDevolucao();
}
