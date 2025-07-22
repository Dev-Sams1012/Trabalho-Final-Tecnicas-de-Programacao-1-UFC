package ufc.dc.tp1.app.itens;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import ufc.dc.tp1.app.exceptions.VestimentaJaEmprestadoException;

public class Emprestimo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private LocalDate data;
	private String receptor;
	private List<IEmprestavel> itensEmprestados;
	
	public Emprestimo(LocalDate data, String receptor) {
		if (data == null) throw new IllegalArgumentException("Data do emprestimo não pode ser nula.");
		if(receptor == null || receptor.isBlank()) throw new IllegalArgumentException("O nome do receptor do empréstimo não pode ser nulo nem vazio.");
		
		this.data = data;
		this.receptor = receptor;
		
		itensEmprestados = new ArrayList<>();
	}
	
	public Emprestimo(String receptor) {
		if(receptor == null || receptor.isBlank()) throw new IllegalArgumentException("O nome do receptor do empréstimo não pode ser nulo nem vazio.");
		
		this.data = LocalDate.now();
		this.receptor = receptor;
		
		itensEmprestados = new ArrayList<>();
	}
	
	public List<Item> emprestar(Item... itens) throws VestimentaJaEmprestadoException{
		if(itens == null || itens.length == 0) throw new IllegalArgumentException("Os itens não podem ser nulos.");
		
		List<Item> itensNaoEmprestáveis = new ArrayList<>();
		
		for(Item item: itens ) {
			if(item instanceof IEmprestavel emprestavel) {
				emprestavel.registrarEmprestimo();
				itensEmprestados.add(emprestavel);
			} else {
				itensNaoEmprestáveis.add(item);
			}
		}
		
		return itensNaoEmprestáveis;
	}
	
	public List<Item> emprestar(List<Item> itens) throws VestimentaJaEmprestadoException{
		if(itens == null || itens.isEmpty() ) throw new IllegalArgumentException("Os itens não podem ser nulos.");
		
		List<Item> itensNaoEmprestáveis = new ArrayList<>();
		
		for(Item item: itens ) {
			if(item instanceof IEmprestavel emprestavel) {
				emprestavel.registrarEmprestimo();
				itensEmprestados.add(emprestavel);
			} else {
				itensNaoEmprestáveis.add(item);
			}
		}
		
		return itensNaoEmprestáveis;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public String getDataToString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		
		return data.format(formatter);
	}

	public int getDiasDeEmprestimo() {
		return (int) ChronoUnit.DAYS.between(this.data,LocalDate.now());
	}
	
	public String getReceptor() {
		return receptor;
	}
	
	public List<IEmprestavel> getListaDeEmprestimo(){
		return itensEmprestados;
	}
	
	
	
}