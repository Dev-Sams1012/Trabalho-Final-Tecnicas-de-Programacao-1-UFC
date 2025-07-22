package ufc.dc.tp1.app.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;

import ufc.dc.tp1.app.itens.Emprestimo;
import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.ILavavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.Look;
import ufc.dc.tp1.app.itens.enums.*;
import ufc.dc.tp1.app.itens.vestuário.*;

public class PainelItem extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private List<Item> listaItens = new ArrayList<>();
    private List<ILavavel> listaItensSujos = new ArrayList<>();

    private final File arquivoItens = new File("itens.dat");
    private final File arquivoEmprestimos = new File("emprestimos.dat");
    private final File arquivoLooks = new File("looks.dat");
    private final File arquivoLavagens = new File("lavagens.dat");
    
    private PainelLavagem painelLavagem;

    private final JPanel painelCartoes;
    private JDialog dialogAdicionar;

    public PainelItem(PainelLavagem painelLavagem) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        setBackground(new Color(175, 215, 255));
    
        JButton botaoAdicionarItem = new JButton("+ Adicionar Item");
        botaoAdicionarItem.setFocusPainted(false);
        botaoAdicionarItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoAdicionarItem.addActionListener(e -> mostrarDialogAdicionarItem());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(new Color(175, 215, 255));
        painelBotoes.add(botaoAdicionarItem);
        add(painelBotoes, BorderLayout.NORTH);

        painelCartoes = new JPanel(new GridLayout(2, 8, 15, 15));
        JScrollPane scrollPane = new JScrollPane(painelCartoes);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        
        this.painelLavagem = painelLavagem;

        carregarItensTela();
    }
    
    public void carregarItensTela() {
    	listaItens = carregaItensArquivo();
        listaItensSujos = carregaItensSujosArquivo();
    	
    	painelCartoes.removeAll();
    	
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
        dialogDetalhes.setSize(400, 250);
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

        painelDetalhes.add(new JLabel("Quantiade de Usos:"));
        painelDetalhes.add(new JLabel(String.valueOf(item.getQuantidadeUsos())));
        
        painelDetalhes.add(new JLabel("Conservação:"));
        painelDetalhes.add(new JLabel(item.getConservacao().toString()));

        if (item instanceof VestimentaTamanhoInt vestimentaTamanhoInt) {
            painelDetalhes.add(new JLabel("Tamanho (numérico):"));
            painelDetalhes.add(new JLabel(String.valueOf(vestimentaTamanhoInt.getTamanho())));
        } else if (item instanceof VestimentaTamanhoEnum vestimentaTamanhoEnum) {
            painelDetalhes.add(new JLabel("Tamanho (padrão):"));
            painelDetalhes.add(new JLabel(vestimentaTamanhoEnum.getTamanho().toString()));
        }

        dialogDetalhes.add(new JScrollPane(painelDetalhes));
        
        JButton botaoDeletar = new JButton("Deletar Item");
        botaoDeletar.setFocusPainted(false);
        botaoDeletar.addActionListener(e -> {
            List<Emprestimo> emprestimos = carregaEmprestimosArquivo();
            
            for (Emprestimo emprestimo : emprestimos) {
                for (IEmprestavel emprestado : emprestimo.getListaDeEmprestimo()) {
                    if (((Item)emprestado).getId().equals(item.getId())) {
                        JOptionPane.showMessageDialog(PainelItem.this, "Esse item está emprestado, não é possível excluí-lo.", "Item emprestado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }
            
            List<Look> looks = carregaLooksArquivo();
            
            for(Look look: looks){
                for (Item itemLook : look.getMapaRoupas().values()){
                    if(itemLook.getId().equals(item.getId())){
                        JOptionPane.showMessageDialog(PainelItem.this, "Esse item pertence a um look, não é possivel excluí-lo.", "Item pertence a look", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }
            
            String mensagem = "Tem certeza que deseja apagar o item:\n\n" + "- ID: " + item.getId() + "\n" + "- Categoria: " + item.getCategoria();

            int opcao = JOptionPane.showConfirmDialog(PainelItem.this, mensagem, "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (opcao == JOptionPane.YES_OPTION) {
                deletaItem(item);
                dialogDetalhes.dispose();
            }
        });

        JButton botaoModificar = new JButton("Modificar Item");
        
        botaoModificar.setFocusPainted(false);
        botaoModificar.addActionListener(e -> {
        	JTextField campoCor = new JTextField(item.getCor());
            JTextField campoLoja = new JTextField(item.getLojaOrigem());
            JComboBox<Conservacao> comboConservacao = new JComboBox<>(Conservacao.values());
            comboConservacao.setSelectedItem(item.getConservacao());
            
            JTextField campoImagem = new JTextField(item.getCaminhoImagem());
            campoImagem.setEditable(false);
            JButton botaoEscolherImagem = new JButton("Escolher");
            
            botaoEscolherImagem.addActionListener(img -> {
                JFileChooser fileChooser = new JFileChooser();
                int resultado = fileChooser.showOpenDialog(dialogAdicionar);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File arquivoSelecionado = fileChooser.getSelectedFile();
                    campoImagem.setText(arquivoSelecionado.getAbsolutePath());
                }
            });

            JButton botaoLimparImagem = new JButton("Limpar Imagem");
            botaoLimparImagem.addActionListener(img -> campoImagem.setText(""));

            JPanel painelImagem = new JPanel(new BorderLayout());
                       
            painelImagem.add(campoImagem, BorderLayout.CENTER);

            JPanel painelImagemBotoes = new JPanel(new BorderLayout());
            painelImagemBotoes.add(botaoEscolherImagem, BorderLayout.EAST);
            painelImagemBotoes.add(botaoLimparImagem, BorderLayout.WEST);
            painelImagem.add(painelImagemBotoes, BorderLayout.SOUTH);
            
            JPanel painelModifica = new JPanel(new GridLayout(0, 2, 5, 5));
            painelModifica.add(new JLabel("Cor:"));
            painelModifica.add(campoCor);
            painelModifica.add(new JLabel("Loja:"));
            painelModifica.add(campoLoja);
            painelModifica.add(new JLabel("Conservação:"));
            painelModifica.add(comboConservacao);
            painelModifica.add(new JLabel("Imagem:"));
            painelModifica.add(painelImagem);
            
            int opcao = JOptionPane.showConfirmDialog(dialogDetalhes, painelModifica, "Modificar Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (opcao == JOptionPane.OK_OPTION) {
                item.setCor(campoCor.getText().trim());
                item.setLojaOrigem(campoLoja.getText().trim());
                item.setConservacao((Conservacao) comboConservacao.getSelectedItem());
                item.setCaminhoImagem(campoImagem.getText().trim());
                
                salvaItensArquivo(listaItens); 
                painelCartoes.removeAll();      
                carregarItensTela();
                dialogDetalhes.dispose();       
            }
            
        });

        JButton botaoUsarItem = new JButton("Registrar uso");
        botaoUsarItem.addActionListener(use -> {
            int resultado = JOptionPane.showConfirmDialog(dialogDetalhes, "Você confirma que o item '" + item.getId() + "' será usado?", "Registrar Uso do Look", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if(resultado == JOptionPane.OK_OPTION){
                if(item instanceof ILavavel lavavel){
                    if (listaItensSujos.contains(lavavel) == false){
                        listaItensSujos.add(lavavel);
                        salvaItensSujosArquivo(listaItensSujos);
                        
                    }
                }           
                item.registrarUso();
                salvaItensArquivo(listaItens);
                
                carregarItensTela();
                
                painelLavagem.carregarItensSujosTela();
                
                dialogDetalhes.dispose();
            }
        });
        
        JPanel painelBotoes = new JPanel();
    
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        painelBotoes.add(botaoDeletar);                  
        painelBotoes.add(botaoModificar);
        painelBotoes.add(botaoUsarItem); 
        
        dialogDetalhes.add(painelBotoes, BorderLayout.SOUTH);
        
        dialogDetalhes.setVisible(true);
    }

    private void deletaItem(Item item) {    	
    	int indiceItem = listaItens.indexOf(item);
    	if (indiceItem != -1) listaItens.remove(indiceItem);
    	
    	if(listaItensSujos.contains((ILavavel)item)) {
    		int indiceItemSujo = listaItensSujos.indexOf((ILavavel)item);
    		if (indiceItemSujo != -1) listaItensSujos.remove(indiceItemSujo);
    	}
    	
        salvaItensArquivo(listaItens);
        
        salvaItensSujosArquivo(listaItensSujos);
        
        painelCartoes.removeAll();
        
        carregarItensTela();
    }

    private void mostrarDialogAdicionarItem() {
        dialogAdicionar = new JDialog();
        dialogAdicionar.setTitle("Adicionar Novo Item");
        dialogAdicionar.setSize(400, 450);
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

        JLabel labelImagem = new JLabel("Imagem:");
        JTextField campoImagem = new JTextField();
        campoImagem.setEditable(false);
        JButton botaoEscolherImagem = new JButton("Escolher");
        JButton botaoLimparImagem = new JButton("Limpar");


        botaoEscolherImagem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int resultado = fileChooser.showOpenDialog(dialogAdicionar);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File arquivoSelecionado = fileChooser.getSelectedFile();
                campoImagem.setText(arquivoSelecionado.getAbsolutePath());
            }
        });

        botaoLimparImagem.addActionListener(e -> campoImagem.setText(""));

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
        painelFormulario.add(labelImagem);

        JPanel painelImagem = new JPanel(new BorderLayout());
        painelImagem.add(campoImagem, BorderLayout.NORTH);
        
        painelImagem.add(botaoEscolherImagem, BorderLayout.EAST);
        painelImagem.add(botaoLimparImagem, BorderLayout.WEST);
        painelFormulario.add(painelImagem);

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

        botaoCancelar.addActionListener(e -> dialogAdicionar.dispose());
        
        botaoSalvar.addActionListener(e -> {
            String id = campoId.getText().trim();

            for(Item item : listaItens){
                if(id.equals(item.getId())){
                    JOptionPane.showMessageDialog(painelFormulario, "Um item com esse ID já existe.","ID Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } 

            Item novoItem = criarItemDeCampos(comboTipo, campoId, campoCor, campoLoja, comboConservacao, campoTamanhoNum, comboTamanhoEnum);

            if (novoItem == null) return;

            novoItem.setCaminhoImagem(campoImagem.getText());

            listaItens.add(novoItem);

            salvaItensArquivo(listaItens);

            adicionarCartaoItem(novoItem);
            painelCartoes.revalidate();
            painelCartoes.repaint();
            dialogAdicionar.dispose();
        });

        dialogAdicionar.add(painelFormulario, BorderLayout.CENTER);
        dialogAdicionar.add(painelBotoes, BorderLayout.SOUTH);
        dialogAdicionar.setVisible(true);
    }

    private void salvaItensArquivo(List<Item> itens) {
        try (FileOutputStream fos = new FileOutputStream(arquivoItens);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(itens);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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

    @SuppressWarnings("unchecked")
    private List<Item> carregaItensArquivo() {
        List<Item> itens = new ArrayList<>();

        if (arquivoItens.exists() == false) return itens;
        
        try (FileInputStream fis = new FileInputStream(arquivoItens);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            itens = (List<Item>) ois.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return itens;
    }
    
    @SuppressWarnings("unchecked")
    private List<Emprestimo> carregaEmprestimosArquivo() {
        List<Emprestimo> emprestimos = new ArrayList<>();

        if (arquivoEmprestimos.exists() == false) {
            return emprestimos;
        }

        try (FileInputStream fis = new FileInputStream(arquivoEmprestimos);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
        	emprestimos = (List<Emprestimo>) ois.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return emprestimos;
    }

        @SuppressWarnings("unchecked")
    private List<Look> carregaLooksArquivo() {
        List<Look> itens = new ArrayList<>();

        if (arquivoLooks.exists() == false) {
            return itens;
        }

        try (FileInputStream fis = new FileInputStream(arquivoLooks);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            itens = (List<Look>) ois.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return itens;
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

    private Item criarItemDeCampos(
    	    JComboBox<CategoriaRoupa> comboTipo,
    	    JTextField campoId,
    	    JTextField campoCor,
    	    JTextField campoLoja,
    	    JComboBox<Conservacao> comboConservacao,
    	    JTextField campoTamanhoNum,
    	    JComboBox<Tamanho> comboTamanhoEnum) {
    	
    	    CategoriaRoupa tipo = (CategoriaRoupa) comboTipo.getSelectedItem();
    	    String id = campoId.getText().trim();
    	    String cor = campoCor.getText().trim();
    	    String loja = campoLoja.getText().trim();
    	    Conservacao conservacao = (Conservacao) comboConservacao.getSelectedItem();

    	    if (id.isEmpty() || cor.isEmpty() || loja.isEmpty()) {
    	        JOptionPane.showMessageDialog(dialogAdicionar, "Por favor, preencha todos os campos obrigatórios.", "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
    	        return null;
    	    }

    	    try {
    	        if (tipo == CategoriaRoupa.CALÇADO) {
    	            String tamanhoStr = campoTamanhoNum.getText().trim();
    	            if (tamanhoStr.isEmpty() || Integer.parseInt(tamanhoStr) <= 0) {
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
    	            if (tamanhoStr.isEmpty() || Integer.parseInt(tamanhoStr) <= 0) {
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
