package ufc.dc.tp1.app.itens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Lavagem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final LocalDate data;
    private final List<ILavavel> itensLavados;   

    public Lavagem() {
        this.data = LocalDate.now();
        this.itensLavados = new ArrayList<>();
    }

    public void lavarItens(List<ILavavel> itens) {
        if(itens.isEmpty()) return;

        for(ILavavel sujo : itens){
            sujo.registrarLavagem();
            itensLavados.add(sujo);
        }
    }

    public LocalDate getData() {
        return data;
    }

    public String getDataToString() {
        if (data == null) return "Lavagem ainda não realizada";
        return data.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
    }

    public List<ILavavel> getItensLavados() {
        return itensLavados;
    }
    
}
