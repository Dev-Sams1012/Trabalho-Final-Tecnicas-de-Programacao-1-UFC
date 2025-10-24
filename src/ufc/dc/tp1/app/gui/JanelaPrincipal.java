package ufc.dc.tp1.app.gui;

import java.awt.*;
import javax.swing.*;
  
public class JanelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	public JanelaPrincipal() {      
        setTitle("Gestor de Vestuário Pessoal (GVP)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null);
        
        PainelEmprestimo painelEmprestimo = new PainelEmprestimo();
        PainelLavagem painelLavagem = new PainelLavagem();
        PainelItem painelItem = new PainelItem(painelLavagem);
        PainelLook painelLook = new PainelLook(painelItem, painelLavagem);

        JTabbedPane abas = new JTabbedPane();
        
        abas.addTab("Itens", painelItem);
        abas.addTab("Looks", painelLook);
        abas.addTab("Emprestimos", painelEmprestimo);
        abas.addTab("Lavagens", painelLavagem);
        
        add(abas, BorderLayout.CENTER);
        
        setVisible(true);
    }
	 
    public static void main(String[] args) {
        JanelaPrincipal janela = new JanelaPrincipal();
        janela.setVisible(true);
    }
}
