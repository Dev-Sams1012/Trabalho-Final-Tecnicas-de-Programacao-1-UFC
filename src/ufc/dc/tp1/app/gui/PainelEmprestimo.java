package ufc.dc.tp1.app.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import ufc.dc.tp1.app.exceptions.VestimentaJaEmprestadoException;
import ufc.dc.tp1.app.itens.Emprestimo;
import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.Item;

public class PainelEmprestimo extends JPanel {
    private static final long serialVersionUID = 1L;

    private List<Emprestimo> listaEmprestimos = new ArrayList<>();
    private final File arquivoEmprestimos = new File("emprestimos.dat"); 
    private final File arquivoItens = new File("itens.dat");
    private final JPanel painelCartoes;
    private JDialog dialogAdicionar;

    public PainelEmprestimo() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        setBackground(new Color(175, 255, 175));
    
        JButton botaoAdicionarItem = new JButton("+ Registrar Empréstimo");
        botaoAdicionarItem.setFocusPainted(false);
        botaoAdicionarItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoAdicionarItem.addActionListener(e -> mostrarDialogRegistrarEmprestimo());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(new Color(175, 255, 175));
        painelBotoes.add(botaoAdicionarItem);
        add(painelBotoes, BorderLayout.NORTH);

        painelCartoes = new JPanel(new GridLayout(2, 8, 15, 15));
        JScrollPane scrollPane = new JScrollPane(painelCartoes);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        
        carregarEmprestimosTela();
        
    }
    
    private void carregarEmprestimosTela() {
    	listaEmprestimos = carregaEmprestimosArquivo();
    	
    	painelCartoes.removeAll();
    	
        for (Emprestimo emprestimo : listaEmprestimos) {
            adicionarCartaoEmprestimo(emprestimo);
        }
        
        painelCartoes.revalidate();
        painelCartoes.repaint();
    }

    private void mostrarDialogRegistrarEmprestimo() {
    	dialogAdicionar = new JDialog();
        dialogAdicionar.setTitle("Adicionar Novo Item");
        dialogAdicionar.setSize(400, 350);
        dialogAdicionar.setLocationRelativeTo(this);
        dialogAdicionar.setModal(true);
        
        List<Item> itensCarregados = carregaItensArquivo();

        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel labelReceptor = new JLabel("Nome do Receptor do empréstimo:");
        labelReceptor.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField campoReceptor = new JTextField();
        painelFormulario.add(labelReceptor);
        painelFormulario.add(campoReceptor);
        
        JLabel labelData = new JLabel("Data do empréstimo (dd/mm/yyyy):");
        labelData.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField campoData = new JTextField();
        painelFormulario.add(labelData);
        painelFormulario.add(campoData);
        
        JLabel labelTitulo = new JLabel("Selecione os itens para emprestar:");
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
        	String textoData = campoData.getText().trim();
            LocalDate dataEmprestimo;

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataEmprestimo = LocalDate.parse(textoData, formatter);
            } catch (Exception erroData) {
                JOptionPane.showMessageDialog(dialogAdicionar, "Data inválida, use o formato dd/MM/yyyy.", "Erro de formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (dataEmprestimo.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(dialogAdicionar, "A data do empréstimo não pode ser futura.", "Data inválida", JOptionPane.ERROR_MESSAGE);
                return;
            }
        	
        	List<Item> selecionados = new ArrayList<>();
        	
        	for (int i = 0; i < caixasItens.size(); i++) {
                if (caixasItens.get(i).isSelected()) {
                    Item item = itensCarregados.get(i);
                    
                    if(item instanceof IEmprestavel == false) {
                    	JOptionPane.showMessageDialog(dialogAdicionar, "Um ou mais itens não emprestáveis foram selecionados.", "Item não emprestável" ,  JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    if(itemJaEmprestado(item) == true) {
                    	JOptionPane.showMessageDialog(dialogAdicionar, "O item '" + item.getId() + "' já está emprestado.", "Item já emprestado", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    selecionados.add(item);
                }
        	}
        	
        	if (selecionados.isEmpty()) {
                JOptionPane.showMessageDialog(dialogAdicionar, "Nenhum item selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (campoReceptor.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogAdicionar, "O nome do receptor do empréstimo não pode ser vazio.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Emprestimo emprestimo = new Emprestimo(dataEmprestimo, campoReceptor.getText());
            
            try {
				emprestimo.emprestar(selecionados);

			} catch (VestimentaJaEmprestadoException e1) {
				JOptionPane.showMessageDialog(dialogAdicionar, "Um ou mais itens já emprestados foram selecionados.", "Erro" ,  JOptionPane.ERROR_MESSAGE);
                return;
			}
            
            listaEmprestimos.add(emprestimo);
            
            salvaEmprestimosArquivo(listaEmprestimos);
			
            adicionarCartaoEmprestimo(emprestimo);

            painelCartoes.revalidate();
            painelCartoes.repaint();

            dialogAdicionar.dispose();
            
            
        });
        
        dialogAdicionar.add(painelFormulario, BorderLayout.CENTER);
        dialogAdicionar.add(painelBotoes, BorderLayout.SOUTH);
        dialogAdicionar.setVisible(true);
        
    }
    
    private void adicionarCartaoEmprestimo(Emprestimo emprestimo) {
        JPanel cartao = new JPanel(new BorderLayout());
        cartao.setPreferredSize(new Dimension(200, 240));
        cartao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        cartao.setBackground(Color.WHITE);

        JLabel labelImagem = new JLabel(new ImageIcon("placeholder.png"));
        labelImagem.setPreferredSize(new Dimension(180, 130));
        labelImagem.setHorizontalAlignment(JLabel.CENTER);
        cartao.add(labelImagem, BorderLayout.NORTH);

        JPanel painelTexto = new JPanel();
        painelTexto.setLayout(new BoxLayout(painelTexto, BoxLayout.Y_AXIS));
        painelTexto.setOpaque(false);

        JLabel labelReceptor = new JLabel("Para: " + emprestimo.getReceptor());
        labelReceptor.setFont(new Font("Arial", Font.BOLD, 12));
        labelReceptor.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelTexto.add(labelReceptor);

        JLabel labelData = new JLabel("Em: " + emprestimo.getData().toString());
        labelData.setFont(new Font("Arial", Font.PLAIN, 11));
        labelData.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelTexto.add(labelData);

        for (IEmprestavel item : emprestimo.getListaDeEmprestimo()) {
            JLabel itemLabel = new JLabel("• " + ((Item)item).getId() + " - " + ((Item)item).getCategoria());
            itemLabel.setFont(new Font("Arial", Font.ITALIC, 11));
            itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelTexto.add(itemLabel);
        }

        cartao.add(painelTexto, BorderLayout.CENTER);
        cartao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cartao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarDetalhesEmprestimo(emprestimo);
            }
        });

        painelCartoes.add(cartao);
    }
    
    private void mostrarDetalhesEmprestimo(Emprestimo emprestimo) {
    	JDialog dialogDetalhes = new JDialog();
        dialogDetalhes.setTitle("Detalhes do Empréstimo");
        dialogDetalhes.setSize(350, 250);
        dialogDetalhes.setLocationRelativeTo(this);

        JPanel painelDetalhes = new JPanel(new GridLayout(0, 2, 5, 5));
        painelDetalhes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        painelDetalhes.add(new JLabel("Nome do Receptor:"));
        painelDetalhes.add(new JLabel(emprestimo.getReceptor()));
        
        painelDetalhes.add(new JLabel("Data do Empréstimo:"));
        painelDetalhes.add(new JLabel(emprestimo.getDataToString()));
        
        painelDetalhes.add(new JLabel("Dias desde o Empréstimo:"));
        painelDetalhes.add(new JLabel(String.valueOf(emprestimo.getDiasDeEmprestimo())));
        
        dialogDetalhes.add(new JScrollPane(painelDetalhes));

        JButton botaoRecebimento = new JButton("Confirmar recebimento");
        botaoRecebimento.setFocusPainted(false);
        botaoRecebimento.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(this, "Você confirma o recebimento do(s) item(s)?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (opcao == JOptionPane.YES_OPTION) {
                deletaEmprestimo(emprestimo);
                dialogDetalhes.dispose();
            }
        });

        JPanel painelBotoes = new JPanel();

        painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        painelBotoes.add(botaoRecebimento);                  
        
        dialogDetalhes.add(painelBotoes, BorderLayout.SOUTH);

        dialogDetalhes.setVisible(true);
        
    }

    private void deletaEmprestimo(Emprestimo emprestimo){
        int indice = listaEmprestimos.indexOf(emprestimo);
    	if (indice != -1) listaEmprestimos.remove(indice);
    	
        salvaEmprestimosArquivo(listaEmprestimos);
        
        painelCartoes.removeAll();
        
        carregarEmprestimosTela();
    }
    
    @SuppressWarnings("unchecked")
	private List<Emprestimo> carregaEmprestimosArquivo(){
    	List<Emprestimo> emprestimos = new ArrayList<>();
    	
    	if (arquivoEmprestimos.exists() == false) return emprestimos;
        
        try (FileInputStream fis = new FileInputStream(arquivoEmprestimos);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
        	emprestimos = (List<Emprestimo>) ois.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return emprestimos;
    	
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
    
    private boolean itemJaEmprestado(Item itemVerificado) {
        List<Emprestimo> emprestimos = carregaEmprestimosArquivo();

        for (Emprestimo emprestimo : emprestimos) {
            for (IEmprestavel emprestado : emprestimo.getListaDeEmprestimo()) {
            	Item item = (Item) emprestado;
                if (item.getId().equals(itemVerificado.getId())) {
                	return true;
                }                
            }
        }

        return false;
    }
    
    private void salvaEmprestimosArquivo(List<Emprestimo> emprestimos) {
    	try (FileOutputStream fos = new FileOutputStream(arquivoEmprestimos);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
               oos.writeObject(emprestimos);
           } catch (Exception e) {
               JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
           }
    }
    
}
