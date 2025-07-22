package ufc.dc.tp1.app.exceptions;

public class DevolucaoSemEmprestimoException extends Exception {
  
	private static final long serialVersionUID = 1L;

	public DevolucaoSemEmprestimoException(String id) {
        super("O item com ID '" + id + "' não está emprestado e não pode ser devolvido.");
    }
}
