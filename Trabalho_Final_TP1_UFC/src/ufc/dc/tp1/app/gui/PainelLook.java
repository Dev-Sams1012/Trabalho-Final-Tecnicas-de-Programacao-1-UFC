package ufc.dc.tp1.app.gui;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ufc.dc.tp1.app.itens.Look;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;
import ufc.dc.tp1.app.itens.Item;

public class PainelLook extends JPanel {
	private static final long serialVersionUID = 1L;

    private List<Look> listaLooks = new ArrayList<>();
    private final File arquivoItens = new File("itens.dat");
    private final File arquivoLook = new File("looks.dat"); 
    private final JPanel painelCartoes;
    private JDialog dialogAdicionar;    

	public PainelLook() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        setBackground(new Color(204, 204, 255));

        JButton botaoAdicionarLook = new JButton("+ Adicionar Look");
        botaoAdicionarLook.setFocusPainted(false);
        botaoAdicionarLook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoAdicionarLook.addActionListener(e -> mostrarDialogAdicionarLook());

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTopo.setBackground(new Color(204, 204, 255));
        painelTopo.add(botaoAdicionarLook);
        add(painelTopo, BorderLayout.NORTH);

        JPanel painelLooks = new JPanel();
        painelLooks.setLayout(new GridLayout(0, 3, 20, 20));  
        painelLooks.setBackground(new Color(204, 204, 255));  
        
        painelCartoes = new JPanel(new GridLayout(2, 8, 15, 15));
        JScrollPane scrollPane = new JScrollPane(painelCartoes);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        
        carregarItensTela();
    }

    private void carregarItensTela() {
    	listaLooks = carregaLooksArquivo(arquivoLook);
    	
        for (Look look : listaLooks) {
            adicionarCartaoLook(look);
        }
        
        painelCartoes.revalidate();
        painelCartoes.repaint();
    }

    private void mostrarDialogAdicionarLook() {
        dialogAdicionar = new JDialog();
        dialogAdicionar.setTitle("Adicionar Look");
        dialogAdicionar.setSize(400, 400);
        dialogAdicionar.setLocationRelativeTo(this);
        dialogAdicionar.setModal(true);

        List<Item> itensCarregados = carregaItensArquivo(arquivoItens);

        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelNomeLook = new JLabel("Nome do Look:");
        labelNomeLook.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField campoNomeLook = new JTextField();
        campoNomeLook.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoNomeLook.getPreferredSize().height));
        painelFormulario.add(labelNomeLook);
        painelFormulario.add(campoNomeLook);

        JLabel labelTitulo = new JLabel("Selecione os itens para o Look:");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        painelFormulario.add(labelTitulo);

        JPanel painelCheckboxes = new JPanel();
        painelCheckboxes.setLayout(new BoxLayout(painelCheckboxes, BoxLayout.Y_AXIS));
        List<JCheckBox> caixasItens = new ArrayList<>();

        for (Item item : itensCarregados) {
            JCheckBox check = new JCheckBox(item.getId()+" - "+ item.getCategoria());
            caixasItens.add(check);
            painelCheckboxes.add(check);
        }

        JScrollPane scroll = new JScrollPane(painelCheckboxes);
        scroll.setPreferredSize(new Dimension(350, 200));
        painelFormulario.add(scroll);

        JButton botaoSalvar = new JButton("Salvar");
        JButton botaoCancelar = new JButton("Cancelar");
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoSalvar);

        botaoCancelar.addActionListener(e -> dialogAdicionar.dispose());
        botaoSalvar.addActionListener(e -> {
            List<Item> selecionados = new ArrayList<>();
            Set<CategoriaRoupa> categoriasUsadas = new HashSet<>();

            for (int i = 0; i < caixasItens.size(); i++) {
                if (caixasItens.get(i).isSelected()) {
                    Item item = itensCarregados.get(i);
                    selecionados.add(item);

                    CategoriaRoupa categoria = item.getCategoria();
                    if (!categoriasUsadas.add(categoria)) {
                        JOptionPane.showMessageDialog(dialogAdicionar,
                            "Mais de um item da categoria \"" + categoria + "\" foi selecionado.",
                            "Categoria duplicada",
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }

            if (selecionados.isEmpty()) {
                JOptionPane.showMessageDialog(dialogAdicionar, "Nenhum item selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (campoNomeLook.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogAdicionar, "O nome do look não pode ser vazio.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Look novoLook = new Look(campoNomeLook.getText().trim());
            novoLook.montarLook(selecionados);
            listaLooks.add(novoLook);

            salvaLooksArquivo(listaLooks);

            adicionarCartaoLook(novoLook);

            painelCartoes.revalidate();
            painelCartoes.repaint();

            dialogAdicionar.dispose();

        });

        dialogAdicionar.add(painelFormulario, BorderLayout.CENTER);
        dialogAdicionar.add(painelBotoes, BorderLayout.SOUTH);
        dialogAdicionar.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private List<Item> carregaItensArquivo(File arquivo) {
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

    @SuppressWarnings("unchecked")
    private List<Look> carregaLooksArquivo(File arquivo) {
        List<Look> itens = new ArrayList<>();

        if (arquivo.exists() == false) {
            return itens;
        }

        try (FileInputStream fis = new FileInputStream(arquivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            itens = (List<Look>) ois.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return itens;
    }

    private void salvaLooksArquivo(List<Look> Looks) {
        try (FileOutputStream fos = new FileOutputStream(arquivoLook);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(Looks);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarCartaoLook(Look look){
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
        
        JLabel labelNome = new JLabel(look.getNome());
        labelNome.setFont(new Font("Arial", Font.BOLD, 12));
        labelNome.setHorizontalAlignment(JLabel.CENTER);
        
        painelTexto.add(labelNome);

        cartao.add(painelTexto, BorderLayout.SOUTH);
        
        cartao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cartao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarDetalhesLook(look);
            }
        });

        painelCartoes.add(cartao);
    }
    
    private void mostrarDetalhesLook(Look look) {
        JDialog dialogDetalhes = new JDialog();
        dialogDetalhes.setTitle("Detalhes do Look");
        dialogDetalhes.setSize(350, 250);
        dialogDetalhes.setLocationRelativeTo(this);

        JPanel painelDetalhes = new JPanel(new GridLayout(0, 2, 5, 5));
        painelDetalhes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelDetalhes.add(new JLabel("Nome:"));
        painelDetalhes.add(new JLabel(look.getNome()));

        look.getRoupas().forEach((categoria, item) -> {
            painelDetalhes.add(new JLabel(categoria.name() + ":"));
            painelDetalhes.add(new JLabel(item.getId()));
        });

        JButton botaoDeletar = new JButton("Deletar Look");
        botaoDeletar.setFocusPainted(false);
        botaoDeletar.addActionListener(e -> deletaItem(look));

        dialogDetalhes.add(new JScrollPane(painelDetalhes));
        dialogDetalhes.add(botaoDeletar, BorderLayout.SOUTH);
        dialogDetalhes.setVisible(true);
    }

    private void deletaItem(Look look) {

    }
}
