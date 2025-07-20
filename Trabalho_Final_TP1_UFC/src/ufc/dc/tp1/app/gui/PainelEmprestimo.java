package ufc.dc.tp1.app.gui;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import ufc.dc.tp1.app.itens.Emprestimo;


public class PainelEmprestimo extends JPanel {
    private static final long serialVersionUID = 1L;

    private List<Emprestimo> listaEmprestimo = new ArrayList<>();
    private final File arquivo = new File("emprestimo.dat"); 
    private final JPanel painelCartoes;
    private JDialog dialogAdicionar;

    public PainelEmprestimo() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        setBackground(new Color(175, 255, 175));
    
        JButton botaoAdicionarItem = new JButton("+ Registrar Empréstimo");
        botaoAdicionarItem.setFocusPainted(false);
        botaoAdicionarItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoAdicionarItem.addActionListener(e -> mostrarDialogAdicionarItem());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(new Color(175, 255, 175));
        painelBotoes.add(botaoAdicionarItem);
        add(painelBotoes, BorderLayout.NORTH);

        painelCartoes = new JPanel(new GridLayout(2, 8, 15, 15));
        JScrollPane scrollPane = new JScrollPane(painelCartoes);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        
    }

    private void mostrarDialogAdicionarItem() {

    }
    
}
