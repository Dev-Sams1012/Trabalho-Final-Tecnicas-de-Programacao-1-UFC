package ufc.dc.tp1.app.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.enums.*;
import ufc.dc.tp1.app.itens.vestuário.*;

public class PainelItem extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private List<Item> listaItens = new ArrayList<>();
    private final File arquivo = new File("itens.dat"); 
    private final JPanel painelCartoes;
    private JDialog dialogAdicionar;

    public PainelItem() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        setBackground(new Color(240, 240, 240));
    
        JButton botaoAdicionarItem = new JButton("+ Adicionar Item");
        botaoAdicionarItem.setFocusPainted(false);
        botaoAdicionarItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoAdicionarItem.addActionListener(e -> mostrarDialogAdicionar());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(new Color(240, 240, 240));
        painelBotoes.add(botaoAdicionarItem);
        add(painelBotoes, BorderLayout.NORTH);

        painelCartoes = new JPanel(new GridLayout(2, 8, 15, 15));
        JScrollPane scrollPane = new JScrollPane(painelCartoes);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        carregarItensTela();
    }
    
    private void carregarItensTela() {
    	listaItens = carregaItensArquivo();
    	
        for (Item item : listaItens) {
            adicionarCartaoItem(item);
        }
        
        painelCartoes.revalidate();
        painelCartoes.repaint();
    }

    private void adicionarCartaoItem(Item item) {
        JPanel cartao = new JPanel(new BorderLayout());
        cartao.setPreferredSize(new Dimension(180, 220));
        cartao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        cartao.setBackground(Color.WHITE);

        JLabel labelImagem = new JLabel(new ImageIcon("placeholder.png"));
        labelImagem.setPreferredSize(new Dimension(170, 150));
        labelImagem.setHorizontalAlignment(JLabel.CENTER);
        cartao.add(labelImagem, BorderLayout.CENTER);

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

        cartao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarDetalhesItem(item);
            }
        });
        
        painelCartoes.add(cartao);
    }

    private void mostrarDetalhesItem(Item item) {
        JDialog dialogDetalhes = new JDialog();
        dialogDetalhes.setTitle("Detalhes do Item");
        dialogDetalhes.setSize(350, 250);
        dialogDetalhes.setLocationRelativeTo(this);
        
        JPanel painelDetalhes = new JPanel(new GridLayout(0, 2, 5, 5));
        painelDetalhes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelDetalhes.add(new JLabel("Tipo:"));
        painelDetalhes.add(new JLabel(item.getClass().getSimpleName()));
        
        painelDetalhes.add(new JLabel("ID:"));
        painelDetalhes.add(new JLabel(item.getId()));
        
        painelDetalhes.add(new JLabel("Cor:"));
        painelDetalhes.add(new JLabel(item.getCor()));
        
        painelDetalhes.add(new JLabel("Loja:"));
        painelDetalhes.add(new JLabel(item.getLojaOrigem()));
        
        painelDetalhes.add(new JLabel("Conservação:"));
        painelDetalhes.add(new JLabel(item.getConservacao().toString()));

        if (item instanceof VestimentaCalcado vestimentaCalcado) {
            painelDetalhes.add(new JLabel("Tamanho (numérico):"));
            painelDetalhes.add(new JLabel(String.valueOf(vestimentaCalcado.getTamanho())));
        } else if (item instanceof VestimentaSuperiorExterno || item instanceof VestimentaSuperiorInterno || item instanceof VestimentaIntima) {
            painelDetalhes.add(new JLabel("Tamanho (padrão):"));
            painelDetalhes.add(new JLabel(((VestimentaTamanhoEnum)item).getTamanho().toString()));
        }
        
        dialogDetalhes.add(new JScrollPane(painelDetalhes));
        dialogDetalhes.setVisible(true);
    }

    private void mostrarDialogAdicionar() {
        dialogAdicionar = new JDialog();
        dialogAdicionar.setTitle("Adicionar Novo Item");
        dialogAdicionar.setSize(400, 350);
        dialogAdicionar.setLocationRelativeTo(this);
        dialogAdicionar.setModal(true);

        JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 5, 5));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<CategoriaRoupa> comboTipo = new JComboBox<>(CategoriaRoupa.values());
        JTextField campoId = new JTextField();
        JTextField campoCor = new JTextField();
        JTextField campoLoja = new JTextField();
        JComboBox<Conservacao> comboConservacao = new JComboBox<>(Conservacao.values());

        JLabel labelTamanhoNum = new JLabel("Tamanho (numérico):");
        JTextField campoTamanhoNum = new JTextField();
        JLabel labelTamanhoEnum = new JLabel("Tamanho (padrão):");
        JComboBox<Tamanho> comboTamanhoEnum = new JComboBox<>(Tamanho.values());

        labelTamanhoNum.setVisible(false);
        campoTamanhoNum.setVisible(false);
        labelTamanhoEnum.setVisible(false);
        comboTamanhoEnum.setVisible(false);

        painelFormulario.add(new JLabel("Tipo de Item:"));
        painelFormulario.add(comboTipo);
        painelFormulario.add(new JLabel("ID:"));
        painelFormulario.add(campoId);
        painelFormulario.add(new JLabel("Cor:"));
        painelFormulario.add(campoCor);
        painelFormulario.add(new JLabel("Loja:"));
        painelFormulario.add(campoLoja);
        painelFormulario.add(new JLabel("Conservação:"));
        painelFormulario.add(comboConservacao);
        painelFormulario.add(labelTamanhoNum);
        painelFormulario.add(campoTamanhoNum);
        painelFormulario.add(labelTamanhoEnum);
        painelFormulario.add(comboTamanhoEnum);

        JButton botaoSalvar = new JButton("Salvar");
        JButton botaoCancelar = new JButton("Cancelar");
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoSalvar);

        comboTipo.addActionListener(e -> {
            CategoriaRoupa tipo = (CategoriaRoupa) comboTipo.getSelectedItem();
            boolean usarEnum = (tipo == CategoriaRoupa.SUPERIOR_EXTERNO || tipo == CategoriaRoupa.SUPERIOR_INTERNO || tipo == CategoriaRoupa.ÍNTIMO);
            boolean semTamanho = (tipo == CategoriaRoupa.ACESSÓRIO || tipo == CategoriaRoupa.CABEÇA);

            labelTamanhoNum.setVisible(usarEnum == false && semTamanho == false);
            campoTamanhoNum.setVisible(usarEnum == false && semTamanho == false);
            labelTamanhoEnum.setVisible(usarEnum == true && semTamanho == false);
            comboTamanhoEnum.setVisible(usarEnum == true && semTamanho == false);

            painelFormulario.revalidate();
            painelFormulario.repaint();
        });

        botaoSalvar.addActionListener(e -> {
            Item novoItem = criarItemDeCampos(comboTipo, campoId, campoCor, campoLoja, comboConservacao, campoTamanhoNum, comboTamanhoEnum);

            if (novoItem == null) {
                return;
            }

            listaItens.add(novoItem);

            salvaItensArquivo(listaItens);

            adicionarCartaoItem(novoItem);
            painelCartoes.revalidate();
            painelCartoes.repaint();
            dialogAdicionar.dispose();
        });


        botaoCancelar.addActionListener(e -> dialogAdicionar.dispose());

        dialogAdicionar.add(painelFormulario, BorderLayout.CENTER);
        dialogAdicionar.add(painelBotoes, BorderLayout.SOUTH);
        dialogAdicionar.setVisible(true);
    }

    private void salvaItensArquivo(List<Item> itens) {
        try (FileOutputStream fos = new FileOutputStream(arquivo);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(itens);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    @SuppressWarnings("unchecked")
    private List<Item> carregaItensArquivo() {
        List<Item> itens = new ArrayList<>();

        if (arquivo.exists() == false) {
            return itens;
        }

        try (FileInputStream fis = new FileInputStream(arquivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            itens = (List<Item>) ois.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return itens;
    }


    private Item criarItemDeCampos(
    	    JComboBox<CategoriaRoupa> comboTipo,
    	    JTextField campoId,
    	    JTextField campoCor,
    	    JTextField campoLoja,
    	    JComboBox<Conservacao> comboConservacao,
    	    JTextField campoTamanhoNum,
    	    JComboBox<Tamanho> comboTamanhoEnum
    	) {
    	    CategoriaRoupa tipo = (CategoriaRoupa) comboTipo.getSelectedItem();
    	    String id = campoId.getText().trim();
    	    String cor = campoCor.getText().trim();
    	    String loja = campoLoja.getText().trim();
    	    Conservacao conservacao = (Conservacao) comboConservacao.getSelectedItem();

    	    // Validação básica de campos obrigatórios
    	    if (id.isEmpty() || cor.isEmpty() || loja.isEmpty()) {
    	        JOptionPane.showMessageDialog(dialogAdicionar,
    	            "Por favor, preencha todos os campos obrigatórios.",
    	            "Campos obrigatórios",
    	            JOptionPane.WARNING_MESSAGE);
    	        return null;
    	    }

    	    try {
    	        if (tipo == CategoriaRoupa.CALÇADO) {
    	            String tamanhoStr = campoTamanhoNum.getText().trim();
    	            if (tamanhoStr.isEmpty()) {
    	                JOptionPane.showMessageDialog(dialogAdicionar,
    	                    "Informe o tamanho numérico do calçado.",
    	                    "Campo obrigatório",
    	                    JOptionPane.WARNING_MESSAGE);
    	                return null;
    	            }

    	            int tamanho = Integer.parseInt(tamanhoStr);
    	            return new VestimentaCalcado(id, cor, loja, conservacao, tamanho);
    	        }

    	        if (tipo == CategoriaRoupa.INFERIOR) {
    	            String tamanhoStr = campoTamanhoNum.getText().trim();
    	            if (tamanhoStr.isEmpty()) {
    	                JOptionPane.showMessageDialog(dialogAdicionar,
    	                    "Informe o tamanho numérico da peça inferior.",
    	                    "Campo obrigatório",
    	                    JOptionPane.WARNING_MESSAGE);
    	                return null;
    	            }

    	            int tamanho = Integer.parseInt(tamanhoStr);
    	            return new VestimentaInferior(id, cor, loja, conservacao, tamanho);
    	        }

    	        if (tipo == CategoriaRoupa.SUPERIOR_EXTERNO) {
    	            Tamanho tamanho = (Tamanho) comboTamanhoEnum.getSelectedItem();
    	            return new VestimentaSuperiorExterno(id, cor, loja, conservacao, tamanho);
    	        }

    	        if (tipo == CategoriaRoupa.SUPERIOR_INTERNO) {
    	            Tamanho tamanho = (Tamanho) comboTamanhoEnum.getSelectedItem();
    	            return new VestimentaSuperiorInterno(id, cor, loja, conservacao, tamanho);
    	        }

    	        if (tipo == CategoriaRoupa.ÍNTIMO) {
    	            Tamanho tamanho = (Tamanho) comboTamanhoEnum.getSelectedItem();
    	            return new VestimentaIntima(id, cor, loja, conservacao, tamanho);
    	        }

    	        if (tipo == CategoriaRoupa.ACESSÓRIO) {
    	            return new VestimentaAcessorio(id, cor, loja, conservacao);
    	        }

    	        if (tipo == CategoriaRoupa.CABEÇA) {
    	            return new VestimentaCabeca(id, cor, loja, conservacao);
    	        }

    	        JOptionPane.showMessageDialog(dialogAdicionar,
    	            "Tipo de item não reconhecido.",
    	            "Erro inesperado",
    	            JOptionPane.ERROR_MESSAGE);
    	        return null;

    	    } catch (NumberFormatException ex) {
    	        JOptionPane.showMessageDialog(dialogAdicionar,
    	            "O campo de tamanho numérico deve conter apenas números válidos.",
    	            "Erro de formato",
    	            JOptionPane.ERROR_MESSAGE);
    	        return null;
    	    }
    	}
         
}
