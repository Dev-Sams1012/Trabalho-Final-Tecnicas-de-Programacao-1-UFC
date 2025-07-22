package ufc.dc.tp1.app.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import ufc.dc.tp1.app.itens.ILavavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.Lavagem;

public class PainelLavagem extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private List<ILavavel> listaLavagens = new ArrayList<>();
    private final File arquivoLavagens = new File("lavagens.dat");
    private final File arquivoItens = new File("itens.dat");
    private final File arquivoEmprestimos = new File("emprestimos.dat");
    private final JPanel painelCartoes;

    public PainelLavagem() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        setBackground(new Color(255, 255, 176));
    
        JButton botaoAdicionarItem = new JButton("+ Lavar Itens");
        botaoAdicionarItem.setFocusPainted(false);
        botaoAdicionarItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoAdicionarItem.addActionListener(e -> mostrarDialogLavarItens());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(new Color(255, 255, 176));
        painelBotoes.add(botaoAdicionarItem);
        add(painelBotoes, BorderLayout.NORTH);

        painelCartoes = new JPanel(new GridLayout(2, 8, 15, 15));
        JScrollPane scrollPane = new JScrollPane(painelCartoes);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        carregarItensSujosTela();
    }

    public void carregarItensSujosTela() {
    	listaLavagens = carregaItensSujosArquivo();
    	
    	painelCartoes.removeAll();
    	
        for (ILavavel lavavel : listaLavagens) {
            adicionarCartaoLavagem(lavavel);
        }
        
        painelCartoes.revalidate();
        painelCartoes.repaint();        
    }

    private void adicionarCartaoLavagem(ILavavel lavavel) {
    	Item item = ((Item)lavavel);
    	
        JPanel cartao = new JPanel(new BorderLayout());
        cartao.setPreferredSize(new Dimension(180, 220));
        cartao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        cartao.setBackground(Color.WHITE);
        
        if (item.getCaminhoImagem() != null && item.getCaminhoImagem().isEmpty() == false) {
        	JLabel labelImagem = new JLabel();
        	labelImagem.setHorizontalAlignment(JLabel.CENTER);
        	
        	ImageIcon icon = new ImageIcon(item.getCaminhoImagem());
            Image img = icon.getImage().getScaledInstance(170, 150, Image.SCALE_SMOOTH);
            labelImagem.setIcon(new ImageIcon(img));
            
            cartao.add(labelImagem, BorderLayout.CENTER);
        } else {
            JLabel labelImagem = new JLabel("Sem Imagem");
            labelImagem.setHorizontalAlignment(JLabel.CENTER);
            labelImagem.setForeground(Color.GRAY);
            
            cartao.add(labelImagem, BorderLayout.CENTER);
        }
        
        JPanel painelTexto = new JPanel(new GridLayout(2, 1));
        painelTexto.setOpaque(false);
        
        JLabel labelNome = new JLabel(item.getId());
        labelNome.setFont(new Font("Arial", Font.BOLD, 12));
        labelNome.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel labelTipo = new JLabel(item.getCategoria().toString());
        labelTipo.setHorizontalAlignment(JLabel.CENTER);
        
        painelTexto.add(labelNome);
        painelTexto.add(labelTipo);
        cartao.add(painelTexto, BorderLayout.SOUTH);
        
        cartao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        
        painelCartoes.add(cartao);
    }

    private void mostrarDialogLavarItens(){
        
    }

    @SuppressWarnings("unchecked")
    private List<ILavavel> carregaItensSujosArquivo(){
        List<ILavavel> itensSujos = new ArrayList<>();

        if (arquivoLavagens.exists() == false) {
            return itensSujos;
        }

        try (FileInputStream fis = new FileInputStream(arquivoLavagens);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            itensSujos = (List<ILavavel>) ois.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return itensSujos;        
    }
}
