package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Clientes extends JDialog {
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtRG;
	private JTextField txtCPF;
	private JTextField txtFone;
	private JTextField txtEndereco;
	private JTextField txtEmail;

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JTextField txtCep;
	private JTextField txtCidade;
	private JTextField txtBairro;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JComboBox cboUf;
	private JScrollPane scrollPaneClientes;
	private JList listClientes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Clientes() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneClientes.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/Cliente.png")));
		getContentPane().setBackground(new Color(128, 128, 128));
		getContentPane().setLayout(null);
		
				scrollPaneClientes = new JScrollPane();
				scrollPaneClientes.setVisible(false);
				scrollPaneClientes.setBounds(110, 100, 409, 70);
				getContentPane().add(scrollPaneClientes);
				
						listClientes = new JList();
						listClientes.setBackground(new Color(192, 192, 192));
						listClientes.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								buscarClienteLista();
							}
						});
						scrollPaneClientes.setViewportView(listClientes);

		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblID.setBounds(32, 39, 48, 14);
		getContentPane().add(lblID);

		txtID = new JTextField();
		txtID.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBackground(new Color(126, 134, 143));
		txtID.setBounds(110, 37, 146, 20);
		getContentPane().add(txtID);

		JLabel lblNome = new JLabel("Nome*");
		lblNome.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNome.setBounds(32, 84, 92, 14);
		getContentPane().add(lblNome);

		txtNome = new JTextField();
		txtNome.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setColumns(10);
		txtNome.setBackground(new Color(126, 134, 143));
		txtNome.setBounds(110, 82, 409, 20);
		getContentPane().add(txtNome);

		txtNome.setDocument(new Validador(40));

		JLabel lblRG = new JLabel("RG*");
		lblRG.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblRG.setBounds(32, 126, 67, 14);
		getContentPane().add(lblRG);

		txtRG = new JTextField();
		txtRG.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtRG.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789-";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}

			}
		});
		txtRG.setColumns(10);
		txtRG.setBackground(new Color(126, 134, 143));
		txtRG.setBounds(110, 124, 188, 20);
		getContentPane().add(txtRG);

		txtRG.setDocument(new Validador(9));

		JLabel lblCPF = new JLabel("CPF*");
		lblCPF.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblCPF.setBounds(339, 126, 63, 14);
		getContentPane().add(lblCPF);

		txtCPF = new JTextField();
		txtCPF.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtCPF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789-";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}

			}
		});
		txtCPF.setColumns(10);
		txtCPF.setBackground(new Color(126, 134, 143));
		txtCPF.setBounds(384, 124, 135, 20);
		getContentPane().add(txtCPF);

		txtCPF.setDocument(new Validador(11));

		JLabel lblFone = new JLabel("Telefone*");
		lblFone.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblFone.setBounds(592, 126, 135, 14);
		getContentPane().add(lblFone);

		txtFone = new JTextField();
		txtFone.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtFone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789-";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtFone.setColumns(10);
		txtFone.setBackground(new Color(126, 134, 143));
		txtFone.setBounds(669, 124, 136, 20);
		getContentPane().add(txtFone);

		txtFone.setDocument(new Validador(10));

		JLabel lblEndereco = new JLabel("Endereço*");
		lblEndereco.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblEndereco.setBounds(32, 216, 134, 14);
		getContentPane().add(lblEndereco);

		txtEndereco = new JTextField();
		txtEndereco.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtEndereco.setColumns(10);
		txtEndereco.setBackground(new Color(126, 134, 143));
		txtEndereco.setBounds(110, 214, 459, 20);
		getContentPane().add(txtEndereco);

		txtEndereco.setDocument(new Validador(50));

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblEmail.setBounds(32, 169, 124, 14);
		getContentPane().add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtEmail.setColumns(10);
		txtEmail.setBackground(new Color(126, 134, 143));
		txtEmail.setBounds(110, 167, 459, 20);
		getContentPane().add(txtEmail);

		txtEmail.setDocument(new Validador(50));

		btnAdicionar = new JButton("");
		btnAdicionar.setBorder(null);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/add.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();

			}
		});
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setBounds(84, 385, 48, 48);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarUsuario();
			}
		});
		btnEditar.setBorder(null);
		btnEditar.setBorderPainted(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/edit (2).png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(299, 385, 48, 48);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCliente();

			}
		});
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBorder(null);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/delete (2).png")));
		btnExcluir.setToolTipText("Apagar");
		btnExcluir.setBounds(524, 385, 48, 48);
		getContentPane().add(btnExcluir);

		JButton btnApagar = new JButton("");
		btnApagar.setBorder(null);
		btnApagar.setBorderPainted(false);
		btnApagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnApagar.setContentAreaFilled(false);
		btnApagar.setIcon(new ImageIcon(Clientes.class.getResource("/img/borracha.png")));
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();

			}
		});
		btnApagar.setToolTipText("Limpar");
		btnApagar.setBounds(718, 385, 48, 48);
		getContentPane().add(btnApagar);

		txtCep = new JTextField();
		txtCep.setBackground(new Color(126, 134, 143));
		txtCep.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtCep.setBounds(633, 214, 136, 20);
		getContentPane().add(txtCep);
		txtCep.setColumns(10);
		
		txtCep.setDocument(new Validador(8));

		JLabel lblCEP = new JLabel("CEP");
		lblCEP.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblCEP.setBounds(592, 216, 79, 14);
		getContentPane().add(lblCEP);

		JLabel lblBairro = new JLabel("Bairro*");
		lblBairro.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblBairro.setBounds(32, 256, 79, 14);
		getContentPane().add(lblBairro);

		JLabel lblCidade = new JLabel("Cidade*");
		lblCidade.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblCidade.setBounds(592, 167, 82, 14);
		getContentPane().add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setBackground(new Color(126, 134, 143));
		txtCidade.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtCidade.setBounds(669, 164, 136, 20);
		getContentPane().add(txtCidade);
		txtCidade.setColumns(10);
		
		txtCidade.setDocument(new Validador(10));

		txtBairro = new JTextField();
		txtBairro.setBackground(new Color(126, 134, 143));
		txtBairro.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtBairro.setBounds(110, 254, 459, 20);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblNumero = new JLabel("Nº *");
		lblNumero.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNumero.setBounds(592, 257, 46, 14);
		getContentPane().add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setBackground(new Color(126, 134, 143));
		txtNumero.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtNumero.setBounds(633, 254, 69, 20);
		getContentPane().add(txtNumero);
		txtNumero.setColumns(10);
		
		txtNumero.setDocument(new Validador(10));

		JLabel lblUf = new JLabel("UF*");
		lblUf.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblUf.setBounds(727, 256, 46, 14);
		getContentPane().add(lblUf);

		JLabel lblComplemento = new JLabel("Complemento");
		lblComplemento.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblComplemento.setBounds(32, 305, 110, 14);
		getContentPane().add(lblComplemento);

		txtComplemento = new JTextField();
		txtComplemento.setBackground(new Color(126, 134, 143));
		txtComplemento.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtComplemento.setBounds(130, 303, 439, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);
		
		txtComplemento.setDocument(new Validador(50));

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
						"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(765, 253, 55, 22);
		getContentPane().add(cboUf);

		JButton btnBuscarCep = new JButton("CEP");
		btnBuscarCep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(779, 213, 63, 23);
		getContentPane().add(btnBuscarCep);
		setBounds(100, 100, 891, 530);

	}

	private void LimparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtRG.setText(null);
		txtCPF.setText(null);
		txtFone.setText(null);

		txtCidade.setText(null);
		txtEndereco.setText(null);
		txtBairro.setText(null);
		txtCep.setText(null);
		txtNumero.setText(null);

		txtComplemento.setText(null);
		txtEmail.setText(null);

		//
		
	}

	private void adicionar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do cliente!");
			txtNome.requestFocus();

		} else if (txtRG.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o RG do cliente!");
			txtRG.requestFocus();

		} else if (txtCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF do cliente!");
			txtCPF.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone do cliente!");
			txtFone.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereco do cliente!");
			txtEndereco.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do cliente!");
			txtBairro.requestFocus();

		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero do cliente!");
			txtNumero.requestFocus();

		} else {
			String create = "insert into clientes(nome,rg,cpf,fone,cidade,endereco,bairro,cep,numero,complemento,uf,email)values (?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();

				pst = con.prepareStatement(create);

				pst.setString(1, txtNome.getText());
				pst.setString(2, txtRG.getText());
				pst.setString(3, txtCPF.getText());
				pst.setString(4, txtFone.getText());
				pst.setString(5, txtCidade.getText());

				pst.setString(6, txtEndereco.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(8, txtCep.getText());
				pst.setString(9, txtNumero.getText());
				pst.setString(10, txtComplemento.getText());

				pst.setString(11, cboUf.getSelectedItem().toString());

				pst.setString(12, txtEmail.getText());

				pst.executeUpdate();
				// validar um combobox(preenchimento obrigatório)
				// if(cboUf.getSelectedItem().equals(""))

				// limpar um combobox
				// cboUf.setSelectedItem("");

				// obter o valor de um combobox (gravar no banco)
				// cboUf.getSelectedItem().toString()

				// setar um combobox (obter do banco e mostrar no combobox)
				// cboUf.setSelectedItem(rs.getString(5)) //(5) -> "exemplo"

				JOptionPane.showMessageDialog(null, " Cliente adicionado! ");

				LimparCampos();

				con.close();

			}  catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste CPF /ou RG já está sendo utilizado.");
				txtCPF.setText(null);
				txtCPF.requestFocus();
				txtRG.setText(null);
				txtRG.requestFocus();
				
			}
			catch (Exception e2) {
				System.out.println(e2);
			}
			}

		}
	

	private void excluirCliente() {
		int confirma = JOptionPane.showConfirmDialog(null, " Confirmar a exclusão desse cliente? ", " !!!Atenção!!! ",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from clientes where idcli=?";

			try {
				con = dao.conectar();

				pst = con.prepareStatement(delete);

				pst.setString(1, txtID.getText());

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, " Cliente excluído! ");
				LimparCampos();

				con.close();

			}

			catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não deletado.\nEste Cliente de uma OS pendente.");
				txtID.setText(null);
				txtID.requestFocus();
				
			}
			catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	private void editarUsuario() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do cliente");
			txtNome.requestFocus();

		} else if (txtRG.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o RG do cliente");
			txtRG.requestFocus();

		} else if (txtCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a CPF do cliente");
			txtCPF.requestFocus();

		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a Telefone do cliente");
			txtFone.requestFocus();

		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a Cidade do cliente");
			txtCidade.requestFocus();

		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a Endereço do cliente");
			txtEndereco.requestFocus();

		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a Bairro do cliente");
			txtBairro.requestFocus();

		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a Numero do cliente");
			txtNumero.requestFocus();

		} else {// (nome,rg,cpf,fone,cidade,endereco,bairro,numero)
			String update = "update clientes set nome=? ,rg=? ,cpf=? ,fone=? ,cidade=? ,endereco=? ,bairro=? ,cep=? ,numero=? ,complemento=? ,uf=? ,email=? where idcli=?";
			try {

				con = dao.conectar();

				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtRG.getText());
				pst.setString(3, txtCPF.getText());
				pst.setString(4, txtFone.getText());

				pst.setString(5, txtCidade.getText());
				pst.setString(6, txtEndereco.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(8, txtCep.getText());
				pst.setString(9, txtNumero.getText());
				pst.setString(10, txtComplemento.getText());

				pst.setString(11, cboUf.getSelectedItem().toString());

				pst.setString(12, txtEmail.getText());
				pst.setString(13, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do cliente editado com sucesso!");

				LimparCampos();

				con.close();

			}  catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste CPF,RG ou Email já está sendo utilizado.");
				txtCPF.setText(null);
				txtCPF.requestFocus();
				
			}
			catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	/**
	 * buscarCep
	 */
	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						//lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
						//} else {
						//	JOptionPane.showMessageDialog(null, "CEP não encontrado");
						}
					}
				}
				txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
				System.out.println(e);
		}
	}

	private void listarClientes() {
		// System.out.println("teste");

		// linha abaixo cria um objeto usando como referencia um vetor dinamico , este
		// objeto ira temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar a lista -> modelo
		listClientes.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select *from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			// uso do while para trazer os usuarios enquanto exix=stir
			while (rs.next()) {
				// mostrar a barra de rolagem(scrollpane)
				scrollPaneClientes.setVisible(true);
				// adcionar os usuarios novertor -> lista
				modelo.addElement(rs.getString(2));

				if (txtNome.getText().isEmpty()) {
					scrollPaneClientes.setVisible(false);
				}

			} // fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void buscarClienteLista() {
		// System.out.println("teste");
		// variavel que captuar o indice da linha da lista
		int linha = listClientes.getSelectedIndex();
		if (linha >= 0) {
			// String readBuscaLista=
			// Query (instrução sql)
			// limite " , 1" -> selecionar o indice 0 e 1 usuario da lista
			String readBuscaLista = "select *from clientes where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " ,1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readBuscaLista);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneClientes.setVisible(false);

					txtNome.setText(rs.getString(2));
					txtRG.setText(rs.getString(3));
					txtCPF.setText(rs.getString(4));
					txtFone.setText(rs.getString(5));
					txtCidade.setText(rs.getString(6));

					txtEndereco.setText(rs.getString(7));
					txtBairro.setText(rs.getString(8));
					txtCep.setText(rs.getString(9));
					txtNumero.setText(rs.getString(10));
					txtComplemento.setText(rs.getString(11));

					cboUf.setSelectedItem(rs.getString(12));

					txtEmail.setText(rs.getString(13));
					txtID.setText(rs.getString(1));
					
				} else {
					// System.out.println("Contatos não cadastrados");
					JOptionPane.showMessageDialog(null, "Usuario inexistente");

				}
				con.close();
			} catch (Exception e) {

			}

		} else {
			scrollPaneClientes.setVisible(false);

		}
	}
}// FIM
