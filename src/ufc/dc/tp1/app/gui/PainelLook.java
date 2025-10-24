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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import ufc.dc.tp1.app.itens.Emprestimo;
import ufc.dc.tp1.app.itens.IEmprestavel;
import ufc.dc.tp1.app.itens.ILavavel;
import ufc.dc.tp1.app.itens.Item;
import ufc.dc.tp1.app.itens.Look;
import ufc.dc.tp1.app.itens.UtilizacaoDeLook;
import ufc.dc.tp1.app.itens.enums.CategoriaRoupa;

public class PainelLook extends JPanel {
	private static final long serialVersionUID = 1L;

	private List<Look> listaLooks = new ArrayList<>();
	private List<Item> listaItens = new ArrayList<>();
	private List<ILavavel> listaItensSujos = new ArrayList<>();

	private final File arquivoItens = new File("itens.dat");
	private final File arquivoLooks = new File("looks.dat");
	private final File arquivoEmprestimos = new File("emprestimos.dat");
	private final File arquivoLavagens = new File("lavagens.dat"); 

	private final JPanel painelCartoes;
	private final JTextArea areaEstatistica = new JTextArea(4, 20);
	private JDialog dialogAdicionar;

	private final PainelItem painelItem;
	private final PainelLavagem painellavagem;

	public PainelLook(PainelItem painelItem, PainelLavagem painelLavagem) {
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

		painelCartoes = new JPanel(new GridLayout(2, 8, 15, 15));
		JScrollPane scrollPane = new JScrollPane(painelCartoes);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane, BorderLayout.CENTER);

		areaEstatistica.setEditable(false);
        areaEstatistica.setLineWrap(true);
        areaEstatistica.setWrapStyleWord(true);
        areaEstatistica.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollHistorico = new JScrollPane(areaEstatistica);
        scrollHistorico.setBorder(BorderFactory.createTitledBorder("Estatísticas"));
        add(scrollHistorico, BorderLayout.SOUTH);

		this.painelItem = painelItem;
		this.painellavagem = painelLavagem;

		carregarLooksTela();
	}

	private void carregarLooksTela() {
		listaLooks = carregaLooksArquivo();
		listaItens = carregaItensArquivo();
		listaItensSujos = carregaItensSujosArquivo();

		painelCartoes.removeAll();

		for (Look look : listaLooks) {
			adicionarCartaoLook(look);
		}

		atualizarPainelEstasticas();

		painelCartoes.revalidate();
		painelCartoes.repaint();
	}

	private void mostrarDialogAdicionarLook() {
		dialogAdicionar = new JDialog();
		dialogAdicionar.setTitle("Adicionar Look");
		dialogAdicionar.setSize(400, 400);
		dialogAdicionar.setLocationRelativeTo(this);
		dialogAdicionar.setModal(true);

		List<Item> itensCarregados = carregaItensArquivo();

		JPanel painelFormulario = new JPanel();
		painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
		painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel labelNomeLook = new JLabel("Nome do Look:");
		labelNomeLook.setFont(new Font("Arial", Font.BOLD, 16));
		JTextField campoNomeLook = new JTextField();
		painelFormulario.add(labelNomeLook);
		painelFormulario.add(campoNomeLook);
		painelFormulario.add(Box.createRigidArea(new Dimension(0, 10)));

		JLabel labelImagem = new JLabel("Imagem:");
		labelImagem.setFont(new Font("Arial", Font.BOLD, 16));
		JTextField campoImagem = new JTextField();
		campoImagem.setEditable(false);
		JButton botaoEscolherImagem = new JButton("Escolher");

		painelFormulario.add(labelImagem);

		JPanel painelImagem = new JPanel(new BorderLayout());
		painelImagem.add(campoImagem, BorderLayout.CENTER);
		painelImagem.add(botaoEscolherImagem, BorderLayout.EAST);

		botaoEscolherImagem.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			int resultado = fileChooser.showOpenDialog(dialogAdicionar);
			if (resultado == JFileChooser.APPROVE_OPTION) {
				File arquivoSelecionado = fileChooser.getSelectedFile();
				campoImagem.setText(arquivoSelecionado.getAbsolutePath());
			}
		});

		painelFormulario.add(painelImagem);
		painelFormulario.add(Box.createRigidArea(new Dimension(0, 10)));

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
			List<CategoriaRoupa> categoriasUsadas = new ArrayList<>();

			for (int i = 0; i < caixasItens.size(); i++) {
				if (caixasItens.get(i).isSelected()) {
					Item item = itensCarregados.get(i);
					CategoriaRoupa categoria = item.getCategoria();

					if (categoriasUsadas.contains(categoria)) {
						JOptionPane.showMessageDialog(dialogAdicionar, "Mais de um item da categoria '" + categoria + "'' foi selecionado.", "Categoria duplicada", JOptionPane.WARNING_MESSAGE);
						return;
					}

					selecionados.add(item);
					categoriasUsadas.add(categoria);


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

			for(Item item : selecionados){

				if(item instanceof IEmprestavel emprestavel){
					if(emprestavel.isEmprestada()){
						JOptionPane.showMessageDialog(dialogAdicionar, "O item  '" + item.getId() + "' está emprestado.", "Item emprestado", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
			}

			Look novoLook = new Look(campoNomeLook.getText().trim());
			novoLook.montarLook(selecionados);
			listaLooks.add(novoLook);

			novoLook.setCaminhoImagem(campoImagem.getText().trim());

			salvaLooksArquivo(listaLooks);

			adicionarCartaoLook(novoLook);

			atualizarPainelEstasticas();

			painelCartoes.revalidate();
			painelCartoes.repaint();

			dialogAdicionar.dispose();

		});

		dialogAdicionar.add(painelFormulario, BorderLayout.CENTER);
		dialogAdicionar.add(painelBotoes, BorderLayout.SOUTH);
		dialogAdicionar.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	private List<Item> carregaItensArquivo() {
		List<Item> itens = new ArrayList<>();

		if (arquivoItens.exists() == false) {
			return itens;
		}

		try (FileInputStream fis = new FileInputStream(arquivoItens);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			itens = (List<Item>) ois.readObject();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}

		return itens;
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
	private List<Emprestimo> carregaEmprestimosArquivo() {
		List<Emprestimo> emprestimos = new ArrayList<>();

		if (arquivoEmprestimos.exists() == false) {
			return emprestimos;
		}

		try (FileInputStream fis = new FileInputStream(arquivoEmprestimos );
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			emprestimos = (List<Emprestimo>) ois.readObject();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}

		return emprestimos;
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

	private void salvaLooksArquivo(List<Look> Looks) {
		try (FileOutputStream fos = new FileOutputStream(arquivoLooks);
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

		if (look.getCaminhoImagem() != null && look.getCaminhoImagem().isEmpty() == false) {
			JLabel labelImagem = new JLabel();
			labelImagem.setHorizontalAlignment(JLabel.CENTER);

			ImageIcon icon = new ImageIcon(look.getCaminhoImagem());
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
		dialogDetalhes.setLayout(new BorderLayout());
		dialogDetalhes.setTitle("Detalhes do Look");
		dialogDetalhes.setSize(400, 400);
		dialogDetalhes.setLocationRelativeTo(this);

		JPanel painelDetalhes = new JPanel(new GridLayout(0, 2, 5, 5));
		painelDetalhes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		painelDetalhes.add(new JLabel("Nome:"));
		painelDetalhes.add(new JLabel(look.getNome()));

		painelDetalhes.add(new JLabel("Total de usos:"));
		painelDetalhes.add(new JLabel(String.valueOf(look.getNumeroDeUsos())));

		look.getMapaRoupas().forEach((categoria, item) -> {
			painelDetalhes.add(new JLabel(categoria.name() + ":"));
			painelDetalhes.add(new JLabel(item.getId()));
		});

		dialogDetalhes.add(new JScrollPane(painelDetalhes), BorderLayout.NORTH);

		JTextArea areaUsos = new JTextArea();
		areaUsos.setEditable(false);
		areaUsos.setFont(new Font("Monospaced", Font.PLAIN, 12));
		areaUsos.setBorder(BorderFactory.createTitledBorder("Usos do Look"));

		if (look.getHistoricoDeUsos() != null && look.getHistoricoDeUsos().isEmpty() == false) {
			String texto = "";

			for (UtilizacaoDeLook uso : look.getHistoricoDeUsos()) {
				texto += "Data: " + uso.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n";
				texto += "Descrição: " + uso.getDescricao() + "\n";
				texto += "------------------------------\n";
			}

			areaUsos.setText(texto);

		} else {
			areaUsos.setText("Nenhum uso registrado.");
		}

		JScrollPane scrollUsos = new JScrollPane(areaUsos);
		scrollUsos.setPreferredSize(new Dimension(380, 150));

		dialogDetalhes.add(scrollUsos, BorderLayout.CENTER);

		JButton botaoDeletar = new JButton("Deletar Look");
		botaoDeletar.setFocusPainted(false);
		botaoDeletar.addActionListener(e -> {
			
			// VSCODE ESTÁ DANDO WARNING AQUI, MAS É DESNECESSÁRIO.
			String mensagem = "Tem certeza que deseja apagar o look:\n\n" + "- Nome: " + look.getNome() + "\n" + look.getRoupas();

			int opcao = JOptionPane.showConfirmDialog(this, mensagem, "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (opcao == JOptionPane.YES_OPTION) {
				deletaItem(look);
				dialogDetalhes.dispose();
			}
		});

		JButton botaoModificar = new JButton("Modificar Look");
		botaoModificar.setFocusable(false);
		botaoModificar.addActionListener(e -> {
			List<Item> itensCarregados = carregaItensArquivo();
			List<JCheckBox> caixasItens = new ArrayList<>();

			JPanel painelCheckboxes = new JPanel();
			painelCheckboxes.setLayout(new BoxLayout(painelCheckboxes, BoxLayout.Y_AXIS));

			for (Item item : itensCarregados) {
				JCheckBox check = new JCheckBox(item.getId() + " - " + item.getCategoria());
				if (look.getMapaRoupas().containsValue(item)) {
					check.setSelected(true);
				}
				caixasItens.add(check);
				painelCheckboxes.add(check);
			}

			JTextField campoNomeLook = new JTextField(look.getNome());

			JPanel painelFormulario = new JPanel();
			painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
			painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			painelFormulario.add(new JLabel("Nome do Look:"));
			painelFormulario.add(campoNomeLook);
			painelFormulario.add(new JLabel("Selecione os itens para o Look:"));

			JScrollPane scroll = new JScrollPane(painelCheckboxes);
			scroll.setPreferredSize(new Dimension(350, 200));
			painelFormulario.add(scroll);

			int resultado = JOptionPane.showConfirmDialog(this, painelFormulario, "Modificar Look", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (resultado == JOptionPane.OK_OPTION) {
				List<Item> selecionados = new ArrayList<>();
				List<CategoriaRoupa> categoriasUsadas = new ArrayList<>();

				for (int i = 0; i < caixasItens.size(); i++) {
					if (caixasItens.get(i).isSelected()) {
						Item item = itensCarregados.get(i);
						CategoriaRoupa categoria = item.getCategoria();

						if (categoriasUsadas.contains(categoria)) {
							JOptionPane.showMessageDialog(this, "Mais de um item da categoria \"" + categoria + "\" foi selecionado.", "Categoria duplicada", JOptionPane.WARNING_MESSAGE);
							return;
						}

						selecionados.add(item);
						categoriasUsadas.add(categoria);
					}
				}

				if (selecionados.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Nenhum item selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (campoNomeLook.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(this, "O nome do look não pode ser vazio.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
					return;
				}

				look.setNome(campoNomeLook.getText().trim());
				look.modificarLook(selecionados);

				salvaLooksArquivo(listaLooks);
				
				painelCartoes.removeAll();
				carregarLooksTela();

				atualizarPainelEstasticas();
				dialogDetalhes.dispose();
			}
		});

		JButton botaoUtilizacao = new JButton("Registrar uso");
		botaoUtilizacao.setFocusable(false);
		botaoUtilizacao.addActionListener(e -> {
			List<Emprestimo> emprestimos = carregaEmprestimosArquivo();

			for (Emprestimo emprestimo : emprestimos) {
				List<IEmprestavel> itensEmprestados = emprestimo.getListaDeEmprestimo();

				for (Item item : look.getMapaRoupas().values()) {
					for (IEmprestavel emprestado : itensEmprestados) {
						Item itemEmprestado = ((Item)emprestado);
						if (item.getId().equals(itemEmprestado.getId())) {
							JOptionPane.showMessageDialog(this, "O item '" + item.getId() + "' está emprestado.", "Erro", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
			}

			JPanel painelFormulario = new JPanel();
			painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
			painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			JLabel labelData = new JLabel("Data do uso (dd/mm/yyyy):");
			labelData.setFont(new Font("Arial", Font.BOLD, 16));
			JTextField campoData = new JTextField();
			painelFormulario.add(labelData);
			painelFormulario.add(campoData);

			JLabel labelDescricao = new JLabel("Descrição do uso:");
			labelDescricao.setFont(new Font("Arial", Font.BOLD, 16));
			JTextField campoDescricao = new JTextField();
			painelFormulario.add(labelDescricao);
			painelFormulario.add(campoDescricao);

			int resultado = JOptionPane.showConfirmDialog(dialogDetalhes, painelFormulario, "Registrar Uso do Look", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (resultado == JOptionPane.OK_OPTION) {
				String dataTexto = campoData.getText().trim();
				String descricao = campoDescricao.getText().trim();

				if (dataTexto.isEmpty() || descricao.isEmpty()) {
					JOptionPane.showMessageDialog(dialogDetalhes, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
				}

				try {
					LocalDate data = LocalDate.parse(dataTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					if (data.isAfter(LocalDate.now())) {
						JOptionPane.showMessageDialog(dialogAdicionar, "A data do uso não pode ser futura.", "Data inválida", JOptionPane.ERROR_MESSAGE);
						return;
					}

					look.registrarUso(data, descricao);

					registrarUsoItensLook(look);

					salvaLooksArquivo(listaLooks);

					painelItem.carregarItensTela();

					carregarLooksTela();

					atualizarPainelEstasticas();

					dialogDetalhes.dispose();


					JOptionPane.showMessageDialog(dialogDetalhes, "Uso registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					dialogDetalhes.dispose();

				} catch (DateTimeParseException ex) {
					JOptionPane.showMessageDialog(dialogDetalhes, "Formato de data inválido. Use dd/mm/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JPanel painelBotoes = new JPanel();
		painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

		painelBotoes.add(botaoDeletar);
		painelBotoes.add(botaoModificar);
		painelBotoes.add(botaoUtilizacao);

		dialogDetalhes.add(painelBotoes, BorderLayout.SOUTH);
		dialogDetalhes.setVisible(true);
	}

	private void registrarUsoItensLook(Look look) {
		listaItens = carregaItensArquivo();
		listaItensSujos = carregaItensSujosArquivo();

		for (Item itemDoLook : look.getMapaRoupas().values()) {
			for (Item itemPrincipal : listaItens) {
				if (itemPrincipal.getId().equals(itemDoLook.getId())) {
					itemPrincipal.registrarUso();
					if (itemPrincipal instanceof ILavavel lavavelPrincipal){
						colocaItemSujoLista(lavavelPrincipal);
					}
				}
			}
		}
		painellavagem.carregarItensSujosTela();
		salvaItensArquivo(listaItens);
	}

	private void colocaItemSujoLista(ILavavel lavavel) {
		boolean jaEstaSujo = false;
		
		for(ILavavel sujo : listaItensSujos){
			if(((Item)sujo).getId().equals(((Item)lavavel).getId())){
				jaEstaSujo = true;
				break;
			}
		}

		if(jaEstaSujo == false){
			listaItensSujos.add(lavavel);
			salvaItensSujosArquivo(listaItensSujos);
		}

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

	private void atualizarPainelEstasticas() {
        if (listaLooks.isEmpty()) {
            areaEstatistica.setText("Nenhum item registrado ainda.");
            return;
        }

		List<Look> menosUsados = new ArrayList<>();
        for(Look look : listaLooks){
			if(menosUsados.isEmpty()) menosUsados.add(look);
			else{
				if(look.getNumeroDeUsos() < menosUsados.get(0).getNumeroDeUsos()){
					menosUsados.clear();
					menosUsados.add(look);
				} else if (look.getNumeroDeUsos() == menosUsados.get(0).getNumeroDeUsos()) {
					menosUsados.add(look);
				}
			}
		}

		List<Look> maisUsados = new ArrayList<>();
		for(Look look : listaLooks){
			if(maisUsados.isEmpty()) maisUsados.add(look);
			else{
				if(look.getNumeroDeUsos() > maisUsados.get(0).getNumeroDeUsos()){
					maisUsados.clear();
					maisUsados.add(look);
				} else if (look.getNumeroDeUsos() == maisUsados.get(0).getNumeroDeUsos()) {
					maisUsados.add(look);
				}
			}
		}

		String textoEstatistica = "";
		textoEstatistica += "Look(s) menos usado(s): \n";

		for(Look look : menosUsados){
			textoEstatistica += look.getNome() + " - Usos: " + look.getNumeroDeUsos() + "\n";
		}

		textoEstatistica += "-----------------\n";

		textoEstatistica += "Look(s) mais usado(s): \n";

		for(Look look : maisUsados){
			textoEstatistica += look.getNome() + " - Usos: " + look.getNumeroDeUsos() + "\n";
		}

        areaEstatistica.setText(textoEstatistica);
    }

	private void deletaItem(Look look) {
		int indice = listaLooks.indexOf(look);
		if (indice != -1) listaLooks.remove(indice);

		salvaLooksArquivo(listaLooks);

		painelCartoes.removeAll();

		carregarLooksTela();
	}

}
