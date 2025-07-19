package ufc.dc.tp1.app.gui;
import java.awt.*;
import javax.swing.*;    
public class JanelaPrincipal extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JanelaPrincipal() {
      
        setTitle("Gestor de Vestuário Pessoal (GVP)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); 

        JTabbedPane abas = new JTabbedPane();
        
        abas.addTab("itens", new PainelItem());
        abas.addTab("looks", new PainelLook());
        
        add(abas, BorderLayout.CENTER);
        
        setVisible(true);
    }
	
    public static void main(String[] args) {
        JanelaPrincipal janela = new JanelaPrincipal();
        janela.setVisible(true);
    }
}
