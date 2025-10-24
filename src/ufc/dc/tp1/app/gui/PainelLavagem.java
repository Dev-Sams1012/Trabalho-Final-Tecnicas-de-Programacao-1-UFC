package ufc.dc.tp1.app.gui;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import ufc.dc.tp1.app.itens.ILavavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.Lavagem;

public final class PainelLavagem extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private List<ILavavel> listaItensSujos = new ArrayList<>();
    private List<Lavagem> historicoLavagens = new ArrayList<>();
    
    private final File arquivoLavagens = new File("lavagens.dat");
    private final File arquivoHistorico = new File("historico_lavagens.dat");

    private final JPanel painelCartoes;
    private final JTextArea areaHistoricoLavagem = new JTextArea(4, 30);
    private JDialog dialogLavar;

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

        areaHistoricoLavagem.setEditable(false);
        areaHistoricoLavagem.setLineWrap(true);
        areaHistoricoLavagem.setWrapStyleWord(true);
        areaHistoricoLavagem.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollHistorico = new JScrollPane(areaHistoricoLavagem);
        scrollHistorico.setBorder(BorderFactory.createTitledBorder("Última Lavagem"));
        add(scrollHistorico, BorderLayout.SOUTH);

        carregarItensSujosTela();
        carregarHistorico();
        atualizarPainelHistorico();
    }

    public void carregarItensSujosTela() {
    	listaItensSujos = carregaItensSujosArquivo();
    	
    	painelCartoes.removeAll();
    	
        for (ILavavel lavavel : listaItensSujos) {
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
        
        painelCartoes.add(cartao);
    }

    private void mostrarDialogLavarItens() {
        dialogLavar = new JDialog();
        dialogLavar.setTitle("Lavar Itens");
        dialogLavar.setSize(400, 450);
        dialogLavar.setLocationRelativeTo(this);
        dialogLavar.setModal(true);

        JButton botaoConfirma = new JButton("Confirmar");
        JButton botaoCancela = new JButton("Cancelar");
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        painelBotoes.add(botaoCancela);
        painelBotoes.add(botaoConfirma);

        JPanel painelItensSujos = new JPanel();
        painelItensSujos.setLayout(new BoxLayout(painelItensSujos, BoxLayout.Y_AXIS));
        painelItensSujos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    
        List<JCheckBox> checkboxes = new ArrayList<>();

        for (ILavavel lavavel : listaItensSujos) {
            Item item = (Item) lavavel;
            JCheckBox checkBox = new JCheckBox("Item: " + item.getId() + " - '" + item.getCategoria() + "' ");
            checkboxes.add(checkBox);
            painelItensSujos.add(checkBox);
        }

        JScrollPane scrollPane = new JScrollPane(painelItensSujos);
        dialogLavar.add(scrollPane, BorderLayout.CENTER);
        dialogLavar.add(painelBotoes, BorderLayout.SOUTH);

        botaoConfirma.addActionListener(e -> {
            List<ILavavel> selecionados = new ArrayList<>();

            for (int i = 0; i < checkboxes.size(); i++) {
                if (checkboxes.get(i).isSelected()) {
                    ILavavel lavavel = listaItensSujos.get(i);
                    selecionados.add(lavavel);
                }
            }

            if(selecionados.isEmpty() == false){
                Lavagem lavagem = new Lavagem();
                lavagem.lavarItens(selecionados);
                historicoLavagens.add(lavagem);

                listaItensSujos.removeAll(selecionados);
                salvaItensSujosArquivo(listaItensSujos);
                
                salvarHistorico();

                carregarItensSujosTela();
                atualizarPainelHistorico();
            }
            
            dialogLavar.dispose();

        });

        botaoCancela.addActionListener(e -> dialogLavar.dispose());

        dialogLavar.setVisible(true);
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

    @SuppressWarnings("unchecked")
    private void carregarHistorico() {
        if (!arquivoHistorico.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivoHistorico))) {
            historicoLavagens = (List<Lavagem>) ois.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvaItensSujosArquivo(List<ILavavel> listaItensSujos) {
		try (FileOutputStream fos = new FileOutputStream(arquivoLavagens);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(listaItensSujos);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

    private void salvarHistorico() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivoHistorico))) {
            oos.writeObject(historicoLavagens);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarPainelHistorico() {
        if (historicoLavagens.isEmpty()) {
            areaHistoricoLavagem.setText("Nenhuma lavagem registrada ainda.");
            return;
        }

        Lavagem ultima = historicoLavagens.get(historicoLavagens.size() - 1);

        String textoHistorico = "";
        textoHistorico += "Data: " + ultima.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n";

        textoHistorico += "Itens lavados:\n";
        
        for (ILavavel lavado : ultima.getItensLavados()) {
            Item item = (Item) lavado;
            textoHistorico += "- " + item.getId() + " (" + item.getCategoria() + ")\n";
        }

        areaHistoricoLavagem.setText(textoHistorico);
    }

}
