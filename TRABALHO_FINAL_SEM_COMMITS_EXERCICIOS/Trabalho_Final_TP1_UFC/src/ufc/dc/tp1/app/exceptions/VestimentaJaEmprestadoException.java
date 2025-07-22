package ufc.dc.tp1.app.exceptions;

public class VestimentaJaEmprestadoException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public VestimentaJaEmprestadoException(String id) {
        super("O item com ID '" + id + "' já está emprestado e não pode ser reemprestado.");
    }
}