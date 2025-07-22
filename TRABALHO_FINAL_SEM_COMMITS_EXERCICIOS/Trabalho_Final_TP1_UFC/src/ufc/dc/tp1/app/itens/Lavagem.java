package ufc.dc.tp1.app.itens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Lavagem implements Serializable {
    private LocalDate data;
    private final List<ILavavel> itensSelecionados;
    private final List<ILavavel> itensLavados;   

    public Lavagem() {
        this.data = null;
        this.itensSelecionados = new ArrayList<>();
        this.itensLavados = new ArrayList<>();
    }

    public boolean adicionarItem(Item item) {
        if (item instanceof ILavavel lavavel) {
            if (itensLavados.contains(lavavel)) return false;
            return itensSelecionados.add(lavavel);
        }
        return false;
    }

    public void executarLavagem() {
        if (itensSelecionados.isEmpty()) return;

        this.data = LocalDate.now();
        for (ILavavel item : itensSelecionados) {
            item.registrarLavagem();
            itensLavados.add(item);
        }

        itensSelecionados.clear();
    }

    public LocalDate getData() {
        return data;
    }

    public String getDataToString() {
        if (data == null) return "Lavagem ainda não realizada";
        return data.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
    }

    public List<ILavavel> getItensSelecionados() {
        return itensSelecionados;
    }

    public List<ILavavel> getItensLavados() {
        return itensLavados;
    }

}
