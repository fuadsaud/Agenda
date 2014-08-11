package agenda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import agenda.Agenda;
import agenda.contatos.Cliente;
import agenda.contatos.Contato;
import agenda.contatos.Fornecedor;
import agenda.exceptions.NomeComCaracteresInvalidosException;
import agenda.exceptions.NomeComDuasParticulasException;

public class AgendaUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new AgendaUI().show();
	}

	private Agenda agenda = new Agenda();
	private JPanel buttonsPanel;
	private JFrame mainFrame;
	private JPanel mainPanel;
	private boolean salvo = true;

	private JTable tabela = new JTable();

	private void abrir() {
		fechar();
		agenda = new AgendaFileManager().abrir();
		carregaContatosDaListaPraTabela(agenda.getContatos());
		salvo = true;
		mainFrame.setTitle(agenda.getNome());
	}

	private void carregaContatosDaListaPraTabela(List<Contato> contatos) {
		ContatosTableModel modeloDeTabela = new ContatosTableModel(contatos);
		tabela.setModel(modeloDeTabela);
	}

	private void fechar() {
		if (agenda != null && !salvo) {
			int result = JOptionPane.showConfirmDialog(mainFrame,
					"Sua agenda foi modificada. Deseja salvar?", null,
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if (!salvar()) {
					return;
				}
			} else if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
				return;
			}
		}
		agenda = new Agenda();
		salvo = true;
		carregaContatosDaListaPraTabela(agenda.getContatos());
		mainFrame.setTitle(agenda.getNome());
	}

	private void inicializaButtonsPanel() {
		buttonsPanel = new JPanel(new GridLayout());
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

		JButton novoContato = new JButton("Novo Contato");
		novoContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novoContato();
			}
		});
		buttonsPanel.add(novoContato);

		JButton removeContato = new JButton("Remover Contato");
		removeContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeContato();
			}
		});
		buttonsPanel.add(removeContato);
	}

	private void inicializaMainFrame() {
		mainFrame = new JFrame(agenda.getNome());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(true);
	}

	private void inicializaMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainFrame.add(mainPanel);
	}

	private void inicializaMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);

		JMenu arquivo = new JMenu("Arquivo");
		menuBar.add(arquivo);

		// -----------------------------------------------------------
		JMenuItem novaAgenda = new JMenuItem("Nova Agenda");
		novaAgenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar();
			}
		});
		arquivo.add(novaAgenda);

		// -----------------------------------------------------------
		JMenuItem novoContato = new JMenuItem("Novo Contato");
		novoContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novoContato();
			}
		});
		arquivo.add(novoContato);

		arquivo.addSeparator();

		// -----------------------------------------------------------
		JMenuItem abrir = new JMenuItem("Abrir...");
		abrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrir();
			}
		});
		arquivo.add(abrir);

		// -----------------------------------------------------------
		JMenuItem fechar = new JMenuItem("Fechar");
		fechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar();
			}
		});
		arquivo.add(fechar);

		arquivo.addSeparator();

		// -----------------------------------------------------------
		JMenuItem salvar = new JMenuItem("Salvar...");
		salvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		arquivo.add(salvar);

		arquivo.addSeparator();

		// -----------------------------------------------------------
		JMenuItem sair = new JMenuItem("Sair");
		sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar();
				System.exit(0);
			}
		});
		arquivo.add(sair);

		// -----------------------------------------------------------
		JMenu ajuda = new JMenu("Ajuda");
		menuBar.add(ajuda);

		JMenuItem sobre = new JMenuItem("Sobre");
		sobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								mainFrame,
								"Agenda de Contatos\n\nDesenvolvedors:\nFuad Saud\nJanderson Oliveira\n\n2011",
								"Sobre Agenda de Contatos", JOptionPane.PLAIN_MESSAGE, null);
			}
		});
		ajuda.add(sobre);
	}

	private void inicializaTabela() {
		tabela.setBorder(new LineBorder(Color.black));
		tabela.setGridColor(Color.black);
		tabela.setShowGrid(true);

		JScrollPane scroll = new JScrollPane();
		scroll.getViewport().setBorder(null);
		scroll.getViewport().add(tabela);
		scroll.setSize(450, 450);

		mainPanel.add(scroll);
	}

	private void novoContato() {
		Contato novoContato = null;
		String nome, email, telefone, endereco, aniversarioTemp, ultimaCompraTemp;
		Date dataAniversario, ultimaCompra;
		int fidelidade = 0, qualidade = -1;

		// Coleta nome (obrigatório)
		try {
			do {
				nome = JOptionPane.showInputDialog(mainFrame, "Digite o nome do contato",
						"Novo contato", JOptionPane.QUESTION_MESSAGE);
			} while (nome.isEmpty());
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}

		// Coleta outros dados
		// Email
		do {
			if ((email = JOptionPane.showInputDialog(mainFrame, "Digite o email do contato",
					"Novo contato", JOptionPane.QUESTION_MESSAGE)) == null) {
				return;
			}
		} while (!email.isEmpty() && !email.contains("@"));

		// Telefone
		boolean soNumeros = true;
		do {
			if ((telefone = JOptionPane.showInputDialog(mainFrame, "Digite o telefone do contato",
					"Novo contato", JOptionPane.QUESTION_MESSAGE)) == null) {
				return;
			}
			try {
				Long.parseLong(telefone);
				soNumeros = true;
			} catch (NumberFormatException e) {
				soNumeros = false;
			}
		} while (!email.isEmpty() && !soNumeros);

		// Endereço
		if ((endereco = JOptionPane.showInputDialog(mainFrame, "Digite o endereco do contato",
				"Novo contato", JOptionPane.QUESTION_MESSAGE)) == null) {
			return;
		}

		// Data de aniversário
		boolean consegueParsear = true;
		do {
			if ((aniversarioTemp = JOptionPane.showInputDialog(mainFrame,
					"Digite a data de aniversário do contato\n[dd/mm/aaaa - digite as barras]",
					"Novo contato", JOptionPane.QUESTION_MESSAGE)) == null) {
				return;
			}

			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				format.setLenient(false);
				dataAniversario = format.parse(aniversarioTemp);
			} catch (ParseException e) {
				consegueParsear = false;
				dataAniversario = null;
				e.printStackTrace();
			}
		} while (!aniversarioTemp.isEmpty() && !consegueParsear);

		// Determina o tipo de contato
		// CLIENTE
		if (JOptionPane.showConfirmDialog(mainFrame, "Este contato é um cliente?", "Novo contato",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			// Input fidelidade
			String fidelidadeTemp = null;
			do {
				try {
					String mensagem = "Digite o índice de fidelidade\n1 - Apenas uma vez\n2 - Pouco frequente\n3 - Frequente\n4 - Fiel";
					fidelidadeTemp = JOptionPane.showInputDialog(mainFrame, mensagem,
							"Novo contato", JOptionPane.QUESTION_MESSAGE);
					fidelidade = Integer.parseInt(fidelidadeTemp);
				} catch (NumberFormatException e) {
					if (fidelidadeTemp == null) {
						return;
					}
					e.printStackTrace();
					continue;
				}
			} while (fidelidade < 1 || fidelidade > 4);

			// Data da última compra
			consegueParsear = true;
			do {
				if ((ultimaCompraTemp = JOptionPane.showInputDialog(mainFrame,
						"Digite a data da última compra\n[dd/mm/aaaa - digite as barras]",
						"Novo contato", JOptionPane.QUESTION_MESSAGE)) == null) {
					return;
				}

				try {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					format.setLenient(false);
					ultimaCompra = format.parse(ultimaCompraTemp);
				} catch (ParseException e) {
					consegueParsear = false;
					ultimaCompra = null;
					e.printStackTrace();
				}
			} while (!ultimaCompraTemp.isEmpty() && !consegueParsear);

			try {
				novoContato = new Cliente(nome, email, endereco, telefone, dataAniversario,
						ultimaCompra, fidelidade);
			} catch (NomeComDuasParticulasException e) {
				JOptionPane.showMessageDialog(mainFrame, "Nome precisa conter duas partículas",
						null, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (NomeComCaracteresInvalidosException e) {
				JOptionPane.showMessageDialog(mainFrame,
						"Nome não pode conter caracteres especiais", null,
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

			// FORNECEDOR
		} else if (JOptionPane.showConfirmDialog(mainFrame, "Este contato é um fornecedor?",
				"Novo contato", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			do {
				String temp = null;
				try {
					temp = JOptionPane.showInputDialog(mainFrame,
							"Digite o índice de qualidade [1, 10]", "Novo contato",
							JOptionPane.QUESTION_MESSAGE);
					qualidade = Integer.parseInt(temp);
				} catch (NumberFormatException e) {
					if (temp == null) {
						return;
					}
					e.printStackTrace();
					continue;
				}
			} while (qualidade < 0 || qualidade > 10);
			try {
				novoContato = new Fornecedor(nome, email, endereco, telefone, dataAniversario,
						qualidade);
			} catch (NomeComDuasParticulasException e) {
				JOptionPane.showMessageDialog(mainFrame, "Nome precisa conter duas partículas",
						null, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (NomeComCaracteresInvalidosException e) {
				JOptionPane.showMessageDialog(mainFrame,
						"Nome não pode conter caracteres especiais", null,
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

			// CONTATO COMUM
		} else {
			try {
				novoContato = new Contato(nome, email, endereco, telefone, dataAniversario);
			} catch (NomeComDuasParticulasException e) {
				JOptionPane.showMessageDialog(mainFrame, "Nome precisa conter duas partículas",
						null, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (NomeComCaracteresInvalidosException e) {
				JOptionPane.showMessageDialog(mainFrame,
						"Nome não pode conter caracteres especiais", null,
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		try {
			agenda.insereContato(novoContato);
			salvo = false;
			carregaContatosDaListaPraTabela(agenda.getContatos());
			JOptionPane.showMessageDialog(mainFrame,
					"Contato adicionado (ID " + novoContato.getId() + ")");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private void removeContato() throws NumberFormatException {
		String id;
		if (agenda.getContatos().isEmpty()) {
			JOptionPane.showMessageDialog(mainFrame, "Agenda vazia", null,
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		do {
			id = JOptionPane.showInputDialog(mainFrame, "Digite o ID do contato a ser removido");
		} while (id.isEmpty());
		try {
			agenda.removeContato(Integer.parseInt(id));
			salvo = false;
			carregaContatosDaListaPraTabela(agenda.getContatos());
			JOptionPane.showMessageDialog(mainFrame, "Contato removido", null,
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame, "ID inválido", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean salvar() {
		if (new AgendaFileManager().salvar(agenda)) {
			mainFrame.setTitle(agenda.getNome());
			return salvo = true;
		}
		return salvo = false;
	}

	private void show() {
		// try {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// } catch (Exception e) {
		// try {
		// UIManager
		// .setLookAndFeel(new javax.swing.plaf.nimbus.NimbusLookAndFeel());
		// } catch (UnsupportedLookAndFeelException e1) {
		// e1.printStackTrace();
		// }
		// }

		try {
			UIManager.setLookAndFeel(new javax.swing.plaf.nimbus.NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		inicializaMainFrame();
		inicializaMainPanel();
		inicializaButtonsPanel();
		inicializaMenuBar();
		inicializaTabela();

		mainFrame.pack();
		mainFrame.setSize(600, 480);
		mainFrame.setLocation(250, 150);
		mainFrame.setMinimumSize(new Dimension(600, 480));
		mainFrame.setVisible(true);
	}

}
