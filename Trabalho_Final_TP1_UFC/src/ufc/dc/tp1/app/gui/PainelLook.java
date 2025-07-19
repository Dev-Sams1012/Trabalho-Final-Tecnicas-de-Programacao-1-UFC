package ufc.dc.tp1.app.gui;

import javax.swing.*;
import java.awt.*;

public class PainelLook extends JPanel {
	private static final long serialVersionUID = 1L;

	public PainelLook() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        setBackground(new Color(240, 240, 240));

        JButton btnAdicionar = new JButton("+ Adicionar Look");
        btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTopo.setBackground(new Color(240, 240, 240));
        painelTopo.add(btnAdicionar);
        add(painelTopo, BorderLayout.NORTH);

        JPanel painelLooks = new JPanel();
        painelLooks.setLayout(new GridLayout(0, 3, 20, 20));  
        painelLooks.setBackground(new Color(240, 240, 240));  

        JScrollPane scrollPane = new JScrollPane(painelLooks);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel criarCardLook(String nome, String descricao, String estilo) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(250, 300));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblImagem = new JLabel(new ImageIcon("caminho/para/imagem.jpg"));
        lblImagem.setPreferredSize(new Dimension(220, 160));
        lblImagem.setHorizontalAlignment(JLabel.CENTER);
        card.add(lblImagem, BorderLayout.CENTER);

        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setOpaque(false);

        JLabel lblNome = new JLabel(nome);
        lblNome.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblEstilo = new JLabel("Estilo: " + estilo);
        lblEstilo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEstilo.setForeground(new Color(100, 100, 100));

        JLabel lblDescricao = new JLabel(descricao);
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 12));

        painelInfo.add(lblNome);
        painelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
        painelInfo.add(lblEstilo);
        painelInfo.add(Box.createRigidArea(new Dimension(0, 2)));
        painelInfo.add(lblDescricao);

        card.add(painelInfo, BorderLayout.SOUTH);

        return card;
    }
}
