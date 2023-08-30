package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import java.awt.Cursor;
import javax.swing.UIManager;

public class Fornecedor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtCNPJ;
	private JTextField txtEmail;
	private JTextField txtEndereco;

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private JTextField txtBairro;
	private JTextField txtComplemento;
	private JTextField txtNumero;
	private JTextField txtCep;
	private JTextField txtCidade;
	private JTextField txtFone;
	private JButton btnBuscarCep;

	private JComboBox cboUF;
	private JScrollPane scrollPaneFornecedor;
	private JList listFornecedor;
	private JTextField txtFantasia;
	private JTextField txtIE;
	private JTextField txtSite;
	private JTextField txtVendedor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Fornecedor dialog = new Fornecedor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Fornecedor() {
		setTitle("Fornecedor");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fornecedor.class.getResource("/img/fornecedor.png")));
		setBounds(100, 100, 891, 529);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(128, 128, 128));
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneFornecedor.setVisible(false);
			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblID = new JLabel("ID");
		lblID.setForeground(new Color(192, 192, 192));
		lblID.setBounds(62, 24, 48, 23);
		lblID.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblID);

		txtID = new JTextField();
		txtID.setForeground(new Color(255, 255, 255));
		txtID.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtID.setBounds(62, 49, 110, 20);
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBackground(new Color(169, 169, 169));
		contentPanel.add(txtID);

		JLabel lblNomeEmpresa = new JLabel("Nome Empresa *");
		lblNomeEmpresa.setForeground(new Color(192, 192, 192));
		lblNomeEmpresa.setBounds(209, 24, 224, 23);
		lblNomeEmpresa.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblNomeEmpresa);

		txtNome = new JTextField();
		txtNome.setForeground(new Color(255, 255, 255));
		txtNome.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtNome.setBounds(209, 49, 280, 20);
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedores();
			}
		});
		txtNome.setColumns(10);
		txtNome.setBackground(new Color(169, 169, 169));
		contentPanel.add(txtNome);

		txtNome.setDocument(new Validador(50));

		JLabel lblCnpj = new JLabel("CNPJ*");
		lblCnpj.setForeground(new Color(192, 192, 192));
		lblCnpj.setBounds(677, 84, 147, 19);
		lblCnpj.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblCnpj);

		txtCNPJ = new JTextField();
		txtCNPJ.setForeground(new Color(255, 255, 255));
		txtCNPJ.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtCNPJ.setBounds(677, 105, 147, 20);
		txtCNPJ.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtCNPJ.setColumns(10);
		txtCNPJ.setBackground(new Color(169, 169, 169));
		contentPanel.add(txtCNPJ);

		txtCNPJ.setDocument(new Validador(14));

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setForeground(new Color(192, 192, 192));
		lblEmail.setBounds(62, 80, 124, 23);
		lblEmail.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setForeground(new Color(255, 255, 255));
		txtEmail.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtEmail.setBounds(62, 105, 427, 20);
		txtEmail.setColumns(10);
		txtEmail.setBackground(new Color(169, 169, 169));
		contentPanel.add(txtEmail);

		txtEmail.setDocument(new Validador(70));

		JLabel lblEndereco = new JLabel("Endereço*");
		lblEndereco.setForeground(new Color(192, 192, 192));
		lblEndereco.setBounds(62, 180, 134, 28);
		lblEndereco.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblEndereco);

		txtEndereco = new JTextField();
		txtEndereco.setForeground(new Color(255, 255, 255));
		txtEndereco.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtEndereco.setBounds(62, 205, 427, 20);
		txtEndereco.setColumns(10);
		txtEndereco.setBackground(new Color(169, 169, 169));
		contentPanel.add(txtEndereco);
		txtEndereco.setDocument(new Validador(100));

		JLabel lblBairro = new JLabel("Bairro*");
		lblBairro.setForeground(new Color(192, 192, 192));
		lblBairro.setBounds(62, 239, 79, 14);
		lblBairro.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setForeground(new Color(255, 255, 255));
		txtBairro.setBackground(new Color(169, 169, 169));
		txtBairro.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtBairro.setBounds(62, 264, 427, 20);
		txtBairro.setColumns(10);
		contentPanel.add(txtBairro);

		txtBairro.setDocument(new Validador(30));

		JLabel lblComplemento = new JLabel("Complemento");
		lblComplemento.setForeground(new Color(192, 192, 192));
		lblComplemento.setBounds(62, 295, 110, 23);
		lblComplemento.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblComplemento);

		txtComplemento = new JTextField();
		txtComplemento.setForeground(new Color(255, 255, 255));
		txtComplemento.setBackground(new Color(169, 169, 169));
		txtComplemento.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtComplemento.setBounds(62, 320, 427, 20);
		txtComplemento.setColumns(10);
		contentPanel.add(txtComplemento);

		txtComplemento.setDocument(new Validador(20));

		txtNumero = new JTextField();
		txtNumero.setForeground(new Color(255, 255, 255));
		txtNumero.setBackground(new Color(169, 169, 169));
		txtNumero.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtNumero.setBounds(538, 320, 69, 20);
		txtNumero.setColumns(10);
		contentPanel.add(txtNumero);

		txtNumero.setDocument(new Validador(10));

		JLabel lblNumero = new JLabel("Nº *");
		lblNumero.setForeground(new Color(192, 192, 192));
		lblNumero.setBounds(538, 299, 46, 14);
		lblNumero.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblNumero);

		JLabel lblUf = new JLabel("UF*");
		lblUf.setForeground(new Color(192, 192, 192));
		lblUf.setBounds(677, 186, 46, 14);
		lblUf.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblUf);

		btnBuscarCep = new JButton("CEP");
		btnBuscarCep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscarCep.setBounds(677, 263, 59, 23);
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		contentPanel.add(btnBuscarCep);

		txtCep = new JTextField();
		txtCep.setForeground(new Color(255, 255, 255));
		txtCep.setBackground(new Color(169, 169, 169));
		txtCep.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtCep.setBounds(538, 264, 136, 20);
		txtCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtCep.setColumns(10);
		contentPanel.add(txtCep);

		txtCep.setDocument(new Validador(10));

		JLabel lblCEP = new JLabel("CEP");
		lblCEP.setForeground(new Color(192, 192, 192));
		lblCEP.setBounds(538, 246, 79, 14);
		lblCEP.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblCEP);

		JLabel lblCidade = new JLabel("Cidade*");
		lblCidade.setForeground(new Color(192, 192, 192));
		lblCidade.setBounds(538, 183, 82, 23);
		lblCidade.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setForeground(new Color(255, 255, 255));
		txtCidade.setBackground(new Color(169, 169, 169));
		txtCidade.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtCidade.setBounds(538, 205, 136, 20);
		txtCidade.setColumns(10);
		contentPanel.add(txtCidade);

		txtCidade.setDocument(new Validador(30));

		JLabel lblFone = new JLabel("Telefone*");
		lblFone.setForeground(new Color(192, 192, 192));
		lblFone.setBounds(538, 84, 126, 19);
		lblFone.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblFone);

		txtFone = new JTextField();
		txtFone.setForeground(new Color(255, 255, 255));
		txtFone.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtFone.setBounds(538, 105, 117, 20);
		txtFone.setColumns(10);
		txtFone.setBackground(new Color(169, 169, 169));
		contentPanel.add(txtFone);

		txtFone.setDocument(new Validador(15));

		JButton btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBounds(77, 390, 48, 48);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/add.png")));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setBorder(null);
		contentPanel.add(btnAdicionar);

		JButton btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setBounds(292, 390, 48, 48);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/edit (2).png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBorder(null);
		contentPanel.add(btnEditar);

		JButton btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBounds(517, 390, 48, 48);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/delete (2).png")));
		btnExcluir.setToolTipText("Apagar");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBorder(null);
		contentPanel.add(btnExcluir);

		JButton btnApagar = new JButton("");
		btnApagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnApagar.setBounds(711, 390, 48, 48);
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnApagar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/borracha.png")));
		btnApagar.setToolTipText("Limpar");
		btnApagar.setContentAreaFilled(false);
		btnApagar.setBorderPainted(false);
		btnApagar.setBorder(null);
		contentPanel.add(btnApagar);

		cboUF = new JComboBox();
		cboUF.setBounds(677, 203, 59, 22);
		cboUF.setModel(new DefaultComboBoxModel(
				new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
						"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		contentPanel.add(cboUF);

		scrollPaneFornecedor = new JScrollPane();
		scrollPaneFornecedor.setBounds(209, 69, 280, 62);
		scrollPaneFornecedor.setVisible(false);
		contentPanel.add(scrollPaneFornecedor);

		listFornecedor = new JList();
		listFornecedor.setForeground(new Color(255, 255, 255));
		listFornecedor.setBackground(new Color(192, 192, 192));
		listFornecedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedorLista();
			}
		});
		scrollPaneFornecedor.setViewportView(listFornecedor);

		JLabel lblFantasi = new JLabel("Nome Fantasia*");
		lblFantasi.setForeground(new Color(192, 192, 192));
		lblFantasi.setBounds(538, 24, 138, 23);
		lblFantasi.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		contentPanel.add(lblFantasi);

		txtFantasia = new JTextField();
		txtFantasia.setForeground(new Color(255, 255, 255));
		txtFantasia.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtFantasia.setBounds(538, 49, 286, 20);
		txtFantasia.setColumns(10);
		txtFantasia.setBackground(new Color(169, 169, 169));
		contentPanel.add(txtFantasia);

		JLabel lblNewLabel = new JLabel("IE");
		lblNewLabel.setForeground(new Color(192, 192, 192));
		lblNewLabel.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel.setBounds(617, 300, 46, 14);
		contentPanel.add(lblNewLabel);

		txtIE = new JTextField();
		txtIE.setForeground(new Color(255, 255, 255));
		txtIE.setBackground(new Color(169, 169, 169));
		txtIE.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtIE.setBounds(617, 320, 57, 20);
		contentPanel.add(txtIE);
		txtIE.setColumns(10);

		JLabel lblSite = new JLabel("Site");
		lblSite.setForeground(new Color(192, 192, 192));
		lblSite.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblSite.setBounds(62, 135, 124, 23);
		contentPanel.add(lblSite);

		txtSite = new JTextField();
		txtSite.setForeground(new Color(255, 255, 255));
		txtSite.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtSite.setColumns(10);
		txtSite.setBackground(new Color(169, 169, 169));
		txtSite.setBounds(62, 160, 427, 20);
		contentPanel.add(txtSite);

		JLabel lblNomeVendedor = new JLabel("Nome Vendedor");
		lblNomeVendedor.setForeground(new Color(192, 192, 192));
		lblNomeVendedor.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNomeVendedor.setBounds(538, 130, 136, 23);
		contentPanel.add(lblNomeVendedor);

		txtVendedor = new JTextField();
		txtVendedor.setForeground(new Color(255, 255, 255));
		txtVendedor.setBackground(new Color(169, 169, 169));
		txtVendedor.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtVendedor.setColumns(10);
		txtVendedor.setBounds(538, 152, 286, 20);
		contentPanel.add(txtVendedor);
	}

	private void LimparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtFantasia.setText(null);
		txtCNPJ.setText(null);
		txtFone.setText(null);

		txtCidade.setText(null);
		txtEndereco.setText(null);
		txtBairro.setText(null);
		txtCep.setText(null);
		txtNumero.setText(null);

		txtComplemento.setText(null);
		cboUF.setSelectedItem("");
		txtEmail.setText(null);
		txtSite.setText(null);
		txtVendedor.setText(null);
		txtIE.setText(null);
		scrollPaneFornecedor.setVisible(false);

	}

	private void adicionar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do fornecedor!");
			txtNome.requestFocus();

		} else if (txtCNPJ.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CNPJ do fornecedor!");
			txtCNPJ.requestFocus();

		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone do fornecedor!");
			txtFone.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereco do fornecedor!");
			txtEndereco.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do fornecedor!");
			txtBairro.requestFocus();

		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero do fornecedor!");
			txtNumero.requestFocus();

		} else {
			String create = "insert into fornecedores(razao,fantasia,cnpj,fone,cidade,endereco,bairro,cep,numero,complemento,uf,email,site,vendedor,ie)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();

				pst = con.prepareStatement(create);

				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtCNPJ.getText());
				pst.setString(4, txtFone.getText());
				pst.setString(5, txtCidade.getText());

				pst.setString(6, txtEndereco.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(8, txtCep.getText());
				pst.setString(9, txtNumero.getText());
				pst.setString(10, txtComplemento.getText());

				pst.setString(11, cboUF.getSelectedItem().toString());
				pst.setString(12, txtEmail.getText());
				pst.setString(13, txtSite.getText());
				pst.setString(14, txtVendedor.getText());
				pst.setString(15, txtIE.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, " Fornecedor adicionado! ");

				LimparCampos();

				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Fornecedor não adicionado.\nEste CNPJ já está sendo utilizado.");
				txtCNPJ.setText(null);
				txtCNPJ.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	private void editar() {

		if (txtFantasia.getText().isEmpty()) {

			JOptionPane.showMessageDialog(null, "Preencha o campo 'Nome da empresa'. ");

			txtFantasia.requestFocus();

		} else if (txtCNPJ.getText().isEmpty()) {

			JOptionPane.showMessageDialog(null, "Preencha o campo 'CNPJ'.");

			txtCNPJ.requestFocus();

		} else if (txtNumero.getText().isEmpty()) {

			JOptionPane.showMessageDialog(null, "Preencha o campo 'Número'.");

			txtNumero.requestFocus();

		} else if (txtCep.getText().isEmpty()) {

			JOptionPane.showMessageDialog(null, "Preencha o campo 'CEP'.");

			txtCep.requestFocus();

		} else if (txtBairro.getText().isEmpty()) {

			JOptionPane.showMessageDialog(null, "Preencha o campo 'Bairro'.");

			txtBairro.requestFocus();

		} else if (txtCidade.getText().isEmpty()) {

			JOptionPane.showMessageDialog(null, "Preencha o campo 'Cidade'.");

			txtCidade.requestFocus();

		} else if (txtEmail.getText().isEmpty()) {

			JOptionPane.showMessageDialog(null, "Preencha o campo 'Email'.");

			txtEmail.requestFocus();

		} else if (cboUF.equals(" ")) {

			JOptionPane.showMessageDialog(null, "Preencha a UF");

			cboUF.requestFocus();

		} else {

			// logica principal

			// CRUD - Update

			String update = "update fornecedores set razao=?, fantasia=?, cnpj=?, fone=?, cidade=?, endereco=?, bairro=?, cep=?, numero=?, complemento=?, uf=?, email=?, site=?, vendedor=?, ie=? where idfor=?";

			// trat de exceção

			try {

				// abrir conexão

				con = dao.conectar();

				// preparar a query

				pst = con.prepareStatement(update);

				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtCNPJ.getText());
				pst.setString(4, txtFone.getText());
				pst.setString(5, txtCidade.getText());

				pst.setString(6, txtEndereco.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(8, txtCep.getText());
				pst.setString(9, txtNumero.getText());
				pst.setString(10, txtComplemento.getText());

				pst.setString(11, cboUF.getSelectedItem().toString());
				pst.setString(12, txtEmail.getText());
				pst.setString(13, txtSite.getText());
				pst.setString(14, txtVendedor.getText());
				pst.setString(15, txtIE.getText());
				pst.setString(16, txtID.getText());

				// Executar query

				pst.executeUpdate();

				// confirmar para o user

				JOptionPane.showMessageDialog(null, "Dados do fornecedor editados com sucesso!");

				// limpar campos

				LimparCampos();

				// fechar conexao

				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {

				JOptionPane.showMessageDialog(null,

						"Fornecedor não adicionado.\nEste(a) CNPJ e/ou EMAIL já está sendo utilizado.");

				txtCNPJ.setText(null);

				txtCNPJ.requestFocus();

				txtEmail.setText(null);

				txtEmail.requestFocus();

			} catch (Exception e2) {

				System.out.println(e2);

			}

		}

	}

	private void excluir() {
		int confirma = JOptionPane.showConfirmDialog(null, " Confirmar a exclusão desse fornecedor? ",
				" !!!Atenção!!! ", JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from fornecedores where idfor=?";

			try {
				con = dao.conectar();

				pst = con.prepareStatement(delete);

				pst.setString(1, txtID.getText());

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, " Fornecedor excluído! ");
				LimparCampos();

				con.close();

			}

			catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Fornecedor não deletado.\n");
				txtID.setText(null);
				txtID.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

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
					cboUF.setSelectedItem(element.getText());
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
						// lblStatus.setIcon(new
						// javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
						// } else {
						// JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void listarFornecedores() {

		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar a lista -> modelo
		listFornecedor.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select *from fornecedores where razao like '" + txtNome.getText() + "%'" + "order by razao";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			// uso do while para trazer os usuarios enquanto exix=stir
			while (rs.next()) {
				// mostrar a barra de rolagem(scrollpane)
				scrollPaneFornecedor.setVisible(true);
				// adcionar os usuarios novertor -> lista
				modelo.addElement(rs.getString(2));

				if (txtNome.getText().isEmpty()) {
					scrollPaneFornecedor.setVisible(false);
				}

			} // fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void buscarFornecedorLista() {
		// System.out.println("teste");
		// variavel que captuar o indice da linha da lista
		int linha = listFornecedor.getSelectedIndex();
		if (linha >= 0) {
			// String readBuscaLista=
			// Query (instrução sql)
			// limite " , 1" -> selecionar o indice 0 e 1 usuario da lista
			String readBuscaLista = "select *from fornecedores where razao like '" + txtNome.getText() + "%'"
					+ "order by razao limit " + (linha) + " ,1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readBuscaLista);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneFornecedor.setVisible(false);

					txtNome.setText(rs.getString(2));
					txtFantasia.setText(rs.getString(3));
					txtCNPJ.setText(rs.getString(4));
					txtFone.setText(rs.getString(5));
					txtCidade.setText(rs.getString(6));

					txtEndereco.setText(rs.getString(7));
					txtBairro.setText(rs.getString(8));
					txtCep.setText(rs.getString(9));
					txtNumero.setText(rs.getString(10));
					txtComplemento.setText(rs.getString(11));

					cboUF.setSelectedItem(rs.getString(12));

					txtEmail.setText(rs.getString(13));
					txtSite.setText(rs.getString(14));
					txtVendedor.setText(rs.getString(15));
					txtIE.setText(rs.getString(16));
					txtID.setText(rs.getString(1));

				} else {
					// System.out.println("Contatos não cadastrados");
					JOptionPane.showMessageDialog(null, "Fornecedor inexistente");

				}
				con.close();
			} catch (Exception e) {

			}

		} else {
			scrollPaneFornecedor.setVisible(false);

		}
	}
}
