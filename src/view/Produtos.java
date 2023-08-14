package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import model.DAO;

public class Produtos extends JDialog {
	
	private JTextField txtBarras;
	private JTextField txtID;
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	DAO dao = new DAO();

	private FileInputStream fis;

	private int tamanho;
	private JLabel lblFoto;
	private JTextField txtEstoqueMin;
	private JTextField txtEstoque;
	private JTextField txtArmazem;
	private JTextField txtValor;
	private JComboBox cboMedida;
	private JTextField txtIDFor;
	private JList listProdf;
	private JScrollPane scrollPaneProd;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnApagar;
	private JTextField txtNome;
	private JScrollPane scrollPaneNome;
	private JList listNome;
	private JTextField txtLote;
	private JTextField txtFabricante;
	private JTextField txtLucro;
	private JDateChooser dateVal;
	private JDateChooser dateEnt;
	private JTextArea txtDesc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produtos dialog = new Produtos();
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
	public Produtos() {
		getContentPane().setFont(new Font("Bodoni MT", Font.BOLD, 15));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneNome.setVisible(false);
				scrollPaneProd.setVisible(false);
			}
		});
		getContentPane().setBackground(new Color(128, 128, 128));
		getContentPane().setLayout(null);

		JButton btnFoto = new JButton("Carregar foto");
		btnFoto.setBackground(new Color(192, 192, 192));
		btnFoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
				btnAdicionar.setEnabled(true);

			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

			}
		});

		scrollPaneProd = new JScrollPane();
		scrollPaneProd.setVisible(false);

		scrollPaneNome = new JScrollPane();
		scrollPaneNome.setVisible(false);
		scrollPaneNome.setBounds(22, 165, 412, 48);
		getContentPane().add(scrollPaneNome);

		listNome = new JList();
		listNome.setBackground(new Color(192, 192, 192));
		listNome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarNome();
			}
		});
		listNome.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollPaneNome.setViewportView(listNome);
		scrollPaneProd.setBounds(471, 104, 170, 23);
		getContentPane().add(scrollPaneProd);

		listProdf = new JList();
		listProdf.setBackground(new Color(192, 192, 192));
		listProdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarForLista();
			}
		});
		scrollPaneProd.setViewportView(listProdf);
		btnFoto.setForeground(new Color(0, 0, 0));
		btnFoto.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		btnFoto.setBounds(789, 412, 141, 23);
		getContentPane().add(btnFoto);

		txtBarras = new JTextField();
		txtBarras.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarCDBarras();
				}
			}
		});
		txtBarras.setFont(new Font("Arial", Font.PLAIN, 16));
		txtBarras.setColumns(10);
		txtBarras.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtBarras.setBackground(new Color(126, 134, 143));
		txtBarras.setBounds(108, 76, 326, 29);
		getContentPane().add(txtBarras);

		JLabel lblBarCode = new JLabel("");
		lblBarCode.setIcon(new ImageIcon(Produtos.class.getResource("/img/barcode_code_icon.png")));
		lblBarCode.setToolTipText("Código de Barras");
		lblBarCode.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblBarCode.setBounds(22, 41, 64, 64);
		getContentPane().add(lblBarCode);

		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(713, 121, 280, 280);
		getContentPane().add(panel);
		panel.setLayout(null);

		lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/instagram_camera_foto.png")));
		lblFoto.setBounds(10, 11, 256, 256);
		panel.add(lblFoto);

		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblDescricao.setBounds(22, 171, 156, 19);
		getContentPane().add(lblDescricao);

		txtEstoqueMin = new JTextField();
		txtEstoqueMin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtEstoqueMin.setFont(new Font("Arial", Font.PLAIN, 16));
		txtEstoqueMin.setColumns(10);
		txtEstoqueMin.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtEstoqueMin.setBackground(new Color(126, 134, 143));
		txtEstoqueMin.setBounds(168, 321, 136, 29);
		getContentPane().add(txtEstoqueMin);

		JLabel lblEstoque = new JLabel("Estoque*");
		lblEstoque.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblEstoque.setBounds(22, 296, 156, 19);
		getContentPane().add(lblEstoque);

		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}

			}
		});
		txtEstoque.setFont(new Font("Arial", Font.PLAIN, 16));
		txtEstoque.setColumns(10);
		txtEstoque.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtEstoque.setBackground(new Color(126, 134, 143));
		txtEstoque.setBounds(22, 321, 136, 29);
		getContentPane().add(txtEstoque);

		JLabel lblDescricao_1_1 = new JLabel("Estoque Mínimo*");
		lblDescricao_1_1.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblDescricao_1_1.setBounds(169, 296, 156, 19);
		getContentPane().add(lblDescricao_1_1);

		JLabel lblDescricao_1_1_1 = new JLabel("Unidade de medida:");
		lblDescricao_1_1_1.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblDescricao_1_1_1.setBounds(318, 296, 156, 19);
		getContentPane().add(lblDescricao_1_1_1);

		cboMedida = new JComboBox();
		cboMedida.setBackground(new Color(192, 192, 192));
		cboMedida.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		cboMedida.setModel(new DefaultComboBoxModel(new String[] { "UN", "CX", "PC", "Kg", "m" }));
		cboMedida.setBounds(316, 321, 78, 29);
		getContentPane().add(cboMedida);

		JLabel lblLocalDeArmazenagem = new JLabel("Local de Armazenagem:");
		lblLocalDeArmazenagem.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblLocalDeArmazenagem.setBounds(23, 368, 189, 19);
		getContentPane().add(lblLocalDeArmazenagem);

		txtArmazem = new JTextField();
		txtArmazem.setFont(new Font("Arial", Font.PLAIN, 16));
		txtArmazem.setColumns(10);
		txtArmazem.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtArmazem.setBackground(new Color(126, 134, 143));
		txtArmazem.setBounds(22, 390, 261, 25);
		getContentPane().add(txtArmazem);

		JLabel lblEstoque_1_1 = new JLabel("Valor*");
		lblEstoque_1_1.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblEstoque_1_1.setBounds(22, 436, 156, 19);
		getContentPane().add(lblEstoque_1_1);

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});

		txtValor.setFont(new Font("Arial", Font.PLAIN, 16));
		txtValor.setColumns(10);
		txtValor.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtValor.setBackground(new Color(126, 134, 143));
		txtValor.setBounds(22, 461, 261, 29);
		getContentPane().add(txtValor);

		btnAdicionar = new JButton("");
		btnAdicionar.setBackground(new Color(0, 0, 0));
		btnAdicionar.setIcon(new ImageIcon(Produtos.class.getResource("/img/add.png")));
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarProduto();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setBounds(22, 501, 68, 68);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setBackground(new Color(0, 0, 0));
		btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/edit (2).png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBounds(330, 505, 64, 64);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setBackground(new Color(0, 0, 0));
		btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/delete (2).png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBounds(622, 505, 64, 64);
		getContentPane().add(btnExcluir);

		JLabel lblIDFor = new JLabel("ID do Fornecedor:");
		lblIDFor.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblIDFor.setBounds(471, 51, 170, 19);
		getContentPane().add(lblIDFor);

		txtIDFor = new JTextField();
		txtIDFor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				buscarForLista();
			}
		});
		txtIDFor.setFont(new Font("Arial", Font.PLAIN, 16));
		txtIDFor.setColumns(10);
		txtIDFor.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtIDFor.setBackground(new Color(126, 134, 143));
		txtIDFor.setBounds(471, 75, 170, 29);
		getContentPane().add(txtIDFor);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNome.setBounds(22, 116, 156, 19);
		getContentPane().add(lblNome);

		btnApagar = new JButton("");
		btnApagar.setIcon(new ImageIcon(Produtos.class.getResource("/img/borracha.png")));
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnApagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnApagar.setBorderPainted(false);
		btnApagar.setContentAreaFilled(false);
		btnApagar.setBounds(929, 505, 64, 64);
		getContentPane().add(btnApagar);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				listarNome();
			}
		});
		txtNome.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNome.setColumns(10);
		txtNome.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtNome.setBackground(new Color(126, 134, 143));
		txtNome.setBounds(22, 139, 412, 29);
		getContentPane().add(txtNome);

		JButton btnPesq = new JButton("");
		btnPesq.setIcon(new ImageIcon(Produtos.class.getResource("/img/pesquisar.png")));
		btnPesq.setBounds(918, 61, 48, 48);
		getContentPane().add(btnPesq);
		btnPesq.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesq.setContentAreaFilled(false);
		btnPesq.setBorderPainted(false);

		txtID = new JTextField();
		txtID.setBounds(758, 76, 141, 29);
		getContentPane().add(txtID);
		txtID.setEditable(false);
		txtID.setFont(new Font("Arial", Font.PLAIN, 16));
		txtID.setColumns(10);
		txtID.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtID.setBackground(new Color(126, 134, 143));

		JLabel lblID = new JLabel("Código do produto:");
		lblID.setBounds(758, 46, 161, 29);
		getContentPane().add(lblID);
		lblID.setFont(new Font("Bodoni MT", Font.BOLD, 15));

		JLabel lblNewLabel = new JLabel("Lote*");
		lblNewLabel.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel.setBounds(450, 297, 46, 17);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Fabricante*");
		lblNewLabel_1.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel_1.setBounds(318, 361, 108, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Data Entrada:");
		lblNewLabel_2.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel_2.setBounds(471, 170, 93, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Data Validade*");
		lblNewLabel_3.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel_3.setBounds(471, 227, 93, 14);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_5 = new JLabel("Lucro*");
		lblNewLabel_5.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel_5.setBounds(318, 438, 68, 14);
		getContentPane().add(lblNewLabel_5);

		txtLucro = new JTextField();
		txtLucro.setText("0");
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtLucro.setFont(new Font("Arial", Font.PLAIN, 16));
		txtLucro.setColumns(10);
		txtLucro.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtLucro.setBackground(new Color(126, 134, 143));
		txtLucro.setBounds(318, 461, 261, 29);
		getContentPane().add(txtLucro);

		txtFabricante = new JTextField();
		txtFabricante.setFont(new Font("Arial", Font.PLAIN, 16));
		txtFabricante.setColumns(10);
		txtFabricante.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtFabricante.setBackground(new Color(126, 134, 143));
		txtFabricante.setBounds(318, 386, 261, 29);
		getContentPane().add(txtFabricante);

		txtLote = new JTextField();
		txtLote.setFont(new Font("Arial", Font.PLAIN, 16));
		txtLote.setColumns(10);
		txtLote.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtLote.setBackground(new Color(126, 134, 143));
		txtLote.setBounds(450, 321, 129, 29);
		getContentPane().add(txtLote);

		dateEnt = new JDateChooser();
		dateEnt.setBackground(new Color(192, 192, 192));
		dateEnt.setBounds(471, 193, 170, 20);
		getContentPane().add(dateEnt);

		dateVal = new JDateChooser();
		dateVal.setBackground(new Color(192, 192, 192));
		dateVal.setBounds(471, 250, 170, 20);
		getContentPane().add(dateVal);

		txtDesc = new JTextArea();
		txtDesc.setBackground(new Color(126, 134, 143));
		txtDesc.setBounds(22, 191, 412, 39);
		getContentPane().add(txtDesc);

		JLabel lblNewLabel_6 = new JLabel("%");
		lblNewLabel_6.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel_6.setBounds(588, 466, 34, 23);
		getContentPane().add(lblNewLabel_6);
		btnPesq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarProduto();
			}
		});
		setBounds(100, 100, 1043, 615);

	}

	private void carregarFoto() {
		
		JFileChooser jfc = new JFileChooser();
		// PERSONALIZAÇÃO do explorador de arquivos java
		jfc.setDialogTitle("Selecionar arquivo");
		// filtro de arquivos permitidos
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG,*.JPG,*.JPEG)", "png", "jpg", "jpeg"));
		//
		
		int resultado = jfc.showOpenDialog(this);
		
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {

				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
				
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void adicionarProduto() {

		// System.out.println("Teste");
		// validação de campos obrigatórios

		if (txtBarras.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Código de Barras'. ");
			txtBarras.requestFocus();
		} else if (txtDesc.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Descrição'.");
			txtDesc.requestFocus();

		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Estoque'.");
			txtEstoque.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Estoque Mínimo'.");
			txtEstoqueMin.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Valor'.");
			txtValor.requestFocus();
		} else if (txtArmazem.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Local de Armazenagem'.");
			txtArmazem.requestFocus();
		} else if (txtIDFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'ID do Fornecedor'.");
			txtIDFor.requestFocus();
			
		} else if (dateEnt.equals("...")) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Data de Entrada'.");
			dateEnt.requestFocus();
		} else if (dateVal.equals("...")) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Data de Validade'.");
			dateVal.requestFocus();

			btnAdicionar.setEnabled(true);
			btnEditar.setEnabled(true);
			btnExcluir.setEnabled(true);

		} else {
			// lógica principal
			// CRUD Create
			String create = "INSERT INTO produtos (idfor,barcode,descricao,foto,estoque,estoquemin,valor,um,locl,nome,lote,fabricante,lucro,dataent,dataval) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";
			// codigo,idfor,barcode,descricao,foto,estoque,estoquemin,valor,um,locl,nome,lote,fabricante,dataent,dataval,garantia,lucro,dataent,dataval
			// tratamento de exceções

			try {
				// abrir conexao
				con = dao.conectar();
				// preparar a execução da query (instrução sql, CRUD CREATE)
				pst = con.prepareStatement(create);

				pst.setString(1, txtIDFor.getText());
				pst.setString(2, txtBarras.getText());
				pst.setString(3, txtDesc.getText());
				pst.setBlob(4, fis, tamanho);
				pst.setString(5, txtEstoque.getText());

				pst.setString(6, txtEstoqueMin.getText());
				pst.setString(7, txtValor.getText());
				pst.setString(8, cboMedida.getSelectedItem().toString());
				pst.setString(9, txtArmazem.getText());
				pst.setString(10, txtNome.getText());

				pst.setString(11, txtLote.getText());
				pst.setString(12, txtFabricante.getText());
				
				pst.setString(13, txtLucro.getText());
				// pst.setString(14, dateEnt.getText());
				// pst.setString(15, dateVal.getText());
				// codigo,idfor,barcode,descricao,foto,estoque,estoquemin,valor,um,locl,nome,lote,fabricante,dataent,dataval,garantia,lucro
				
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada = formatador.format(dateEnt.getDate());
				pst.setString(14, dataFormatada);

				SimpleDateFormat formatadorval = new SimpleDateFormat("yyyyMMdd");
				String dataFormatadaval = formatadorval.format(dateVal.getDate());
				pst.setString(15, dataFormatadaval);

				// executa a query(instrução sql, CRUD)
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "Produto adicionado");
				// limpar campos
				limparCampos();

				btnAdicionar.setEnabled(false);

				// fechar conection
				con.close();

				// } catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				// JOptionPane.showMessageDialog(null, "Cliente não adicionado.\nEste CNPJ ou
				// EMAIL já está sendo utilizado.");
				// txtCNPJ.setText(null);
				// txtCNPJ.requestFocus();
				// txtEmail.setText(null);
				// txtEmail.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	private void buscarForLista() {
		int linha = listProdf.getSelectedIndex();

		if (linha >= 0) {

			String readBuscaLista = "select *from produtos where idfor like '" + txtIDFor.getText() + "%'"

					+ "order by idfor limit " + (linha) + " ,1";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(readBuscaLista);

				rs = pst.executeQuery();

				if (rs.next()) {
					// codigo,idfor,barcode,descricao,foto,estoque,estoquemin,valor,um,locl,nome,lote,fabricante,dataent,dataval,garantia,lucro

					scrollPaneProd.setVisible(false);

					txtID.setText(rs.getString(1));
					txtIDFor.setText(rs.getString(2));
					txtBarras.setText(rs.getString(3)); // 2º Campo da Tabela ID
					//
					txtDesc.setText(rs.getNString(4));
					//
					txtEstoque.setText(rs.getString(6)); // 4º Campo da Tabela ID
					txtEstoqueMin.setText(rs.getString(7)); // 4º Campo da Tabela ID
					txtValor.setText(rs.getString(8)); // 4º Campo da Tabela ID
					cboMedida.setSelectedItem(rs.getString(9)); // 4º Campo da Tabela ID
					txtArmazem.setText(rs.getString(10));

					txtNome.setText(rs.getString(11)); // 4º Campo da Tabela ID
					txtLote.setText(rs.getString(12));
					txtFabricante.setText(rs.getString(13));
					
					txtLucro.setText(rs.getString(14));
					
					SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
					String dataFormatada = formatador.format(dateEnt.getDate());
					pst.setString(14, dataFormatada);

					SimpleDateFormat formatadorval = new SimpleDateFormat("yyyyMMdd");
					String dataFormatadaval = formatadorval.format(dateVal.getDate());
					pst.setString(15, dataFormatadaval);

					
					// txtDataent.setText(rs.getString(15));
					// txtDataval.setText(rs.getString(16));

					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);

					Blob blob = (Blob) rs.getBlob(5);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));

					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);

				} else {
					JOptionPane.showMessageDialog(null, "Produto não encontrado");

				}

				con.close();

			} catch (Exception e) {
				System.out.println(e);

			}

		} else {

			scrollPaneProd.setVisible(false);

		}
	}

	private void listarNome() {
		DefaultListModel<String> modelo = new DefaultListModel<>();

		listNome.setModel(modelo);

		String readLista = "select * from produtos where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();

			while (rs.next()) {
				modelo.addElement(rs.getString(11));
				scrollPaneNome.setVisible(true);

				if (txtNome.getText().isEmpty()) {
					scrollPaneNome.setVisible(false);
					
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarNome() {

		int linha = listNome.getSelectedIndex();

		if (linha >= 0) {

			String readBuscaLista = "select * from produtos where nome like '" + txtNome.getText() + "%'"

					+ "order by nome limit " + (linha) + " ,1";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(readBuscaLista);

				rs = pst.executeQuery();

				if (rs.next()) {

					scrollPaneNome.setVisible(false);

					txtID.setText(rs.getString(1)); // 1º Campo da Tabela ID
					txtIDFor.setText(rs.getString(2));
					
					txtBarras.setText(rs.getString(3)); // 2º Campo da Tabela ID
					txtDesc.setText(rs.getNString(4)); // 3º Campo da Tabela ID

					txtEstoque.setText(rs.getString(6)); // 4º Campo da Tabela ID
					txtEstoqueMin.setText(rs.getString(7)); // 4º Campo da Tabela ID
					txtValor.setText(rs.getString(8)); // 4º Campo da Tabela ID
					cboMedida.setSelectedItem(rs.getString(9)); // 4º Campo da Tabela ID
					txtArmazem.setText(rs.getString(10)); // 4º Campo da Tabela ID
					txtNome.setText(rs.getString(11)); // 4º Campo da Tabela ID
					txtLote.setText(rs.getString(12));
					txtFabricante.setText(rs.getString(13));
					
					txtLucro.setText(rs.getString(14));
					
					String setarDataEnt = rs.getString(15);
					Date dataFormatadaEnt = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataEnt);
					dateEnt.setDate(dataFormatadaEnt);

					 

					String setarDataVal = rs.getString(16);
					Date dataFormatadaVal = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataVal);
					dateVal.setDate(dataFormatadaVal);

					// txtDataent.setText(rs.getString(15));
					// txtDataval.setText(rs.getString(16));

					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);

					Blob blob = (Blob) rs.getBlob(5);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));

					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);

				} else {
					JOptionPane.showMessageDialog(null, "Produto não encontrado");

				}

				con.close();

			} catch (Exception e) {
				System.out.println(e);

			}
		} else {

			scrollPaneNome.setVisible(false);

		}
	}

	private void editar() {

		if (txtBarras.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Código de Barras'. ");
			txtBarras.requestFocus();
		} else if (txtDesc.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Descrição'.");
			txtDesc.requestFocus();

		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Estoque'.");
			txtEstoque.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Estoque Mínimo'.");
			txtEstoqueMin.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Valor'.");
			txtValor.requestFocus();
		} else if (txtArmazem.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Local de Armazenagem'.");
			txtArmazem.requestFocus();
		} else if (txtIDFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'ID do Fornecedor'.");
			txtIDFor.requestFocus();

			// codigo,idfor,barcode,descricao,foto,estoque,estoquemin,valor,um,locl,nome,lote,fabricante,dataent,dataval,garantia,lucro

		} else {

			String update = "update produtos set barcode=?, descricao=?, foto=?, estoque=?, estoquemin=?, valor=?, um=?, locl=?, nome=?,lote=?,fabricante=?,lucro=?,dataent=? where codigo=?";

			try {
				// abrir conexão
				con = dao.conectar();

				pst = con.prepareStatement(update);

				
				pst.setString(1, txtBarras.getText());
				pst.setString(2, txtDesc.getText());
				pst.setBlob(3, fis, tamanho);
				pst.setString(4, txtEstoque.getText());
				pst.setString(5, txtEstoqueMin.getText());
				pst.setString(6, txtValor.getText());
				pst.setString(7, cboMedida.getSelectedItem().toString());
				pst.setString(8, txtArmazem.getText());

				pst.setString(9, txtNome.getText());
				pst.setString(10, txtLote.getText());
				pst.setString(11, txtFabricante.getText());
				
				pst.setString(12, txtLucro.getText());
				
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada = formatador.format(dateEnt.getDate());
				pst.setString(13, dataFormatada);

				// pst.setString(14, txtDataent.getText());
				// pst.setString(15, txtDataval.getText());
				pst.setString(14, txtID.getText());

				pst.executeUpdate();
				// confirmar para o user
				JOptionPane.showMessageDialog(null, "Dados do produto editados com sucesso!");
				// limpar campos
				limparCampos();
				// fechar conexao
				con.close();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Coloque uma imagem para poder editar o produto!!");

				System.out.println(e);

			}

		}

	}

	private void excluir() {

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produtos?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// CRUD - Delete vai excluir o contato

			String delete = "delete from produtos where codigo= ?";
			// tratamento de exceção
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				// executar query
				pst.executeUpdate();
				// confirmar para o user
				JOptionPane.showMessageDialog(null, "Produto excluido com sucesso!");
				// limpar campos
				limparCampos();
				// fechar conexao
				con.close();

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}
	
	private void buscarProduto() {

		String produto = JOptionPane.showInputDialog("Digite o código do produto: ");

		String read = "select * from produtos where codigo= ?";
		try {
			// abrir a conexão
			con = dao.conectar();

			pst = con.prepareStatement(read);

			pst.setString(1, produto);

			rs = pst.executeQuery();

			if (rs.next()) {

				txtID.setText(rs.getString(1)); // 1º Campo da Tabela ID
				txtIDFor.setText(rs.getString(2));
				txtBarras.setText(rs.getString(3)); // 2º Campo da Tabela ID
				//
				txtDesc.setText(rs.getNString(4));
				//
				txtEstoque.setText(rs.getString(6)); // 4º Campo da Tabela ID
				txtEstoqueMin.setText(rs.getString(7)); // 4º Campo da Tabela ID
				txtValor.setText(rs.getString(8)); // 4º Campo da Tabela ID
				cboMedida.setSelectedItem(rs.getString(9)); // 4º Campo da Tabela ID
				txtArmazem.setText(rs.getString(10)); // 4º Campo da Tabela ID

				txtNome.setText(rs.getString(11));
				txtLote.setText(rs.getString(12));
				txtFabricante.setText(rs.getString(13));
				
				txtLucro.setText(rs.getString(14));
				
				String setarDataEnt = rs.getString(15);
				Date dataEnt = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataEnt);
				dateEnt.setDate(dataEnt);
				
				String setarDataVal = rs.getString(16);
				Date dataVal = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataVal);
				dateVal.setDate(dataVal);
				

				Blob blob = (Blob) rs.getBlob(5);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));

				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);

				// btnEditar.setEnabled(true);
				// btnExcluir.setEnabled(true);
			} else {
				// System.out.println("Contaos não cadastrados");
				JOptionPane.showMessageDialog(null, "Produto inexistente");
				// btnAdicionar.setEnabled(true);
				// btnPesquisar.setEnabled(true);
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}

	}
	
	private void buscarCDBarras() {

		String read = "select * from produtos where barcode= ?";
		try {
			// abrir a conexão
			con = dao.conectar();

			pst = con.prepareStatement(read);

			pst.setString(1, txtBarras.getText());

			rs = pst.executeQuery();

			if (rs.next()) {

				txtID.setText(rs.getString(1)); // 1º Campo da Tabela ID
				txtIDFor.setText(rs.getString(2));
				txtBarras.setText(rs.getString(3)); // 2º Campo da Tabela ID
				//
				txtDesc.setText(rs.getNString(4));
				//
				txtEstoque.setText(rs.getString(6)); // 4º Campo da Tabela ID
				txtEstoqueMin.setText(rs.getString(7)); // 4º Campo da Tabela ID
				txtValor.setText(rs.getString(8)); // 4º Campo da Tabela ID
				cboMedida.setSelectedItem(rs.getString(9)); // 4º Campo da Tabela ID
				txtArmazem.setText(rs.getString(10)); // 4º Campo da Tabela ID

				txtNome.setText(rs.getString(11));
				txtLote.setText(rs.getString(12));
				txtFabricante.setText(rs.getString(13));
				
				txtLucro.setText(rs.getString(14));
				
				String setarDataEnt = rs.getString(15);
				Date dataEnt = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataEnt);
				dateEnt.setDate(dataEnt);
				
				String setarDataVal = rs.getString(16);
				Date dataVal = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataVal);
				dateVal.setDate(dataVal);
				

				Blob blob = (Blob) rs.getBlob(5);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));

				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);

				// btnEditar.setEnabled(true);
				// btnExcluir.setEnabled(true);
			} else {
				// System.out.println("Contaos não cadastrados");
				JOptionPane.showMessageDialog(null, "Produto inexistente");
				// btnAdicionar.setEnabled(true);
				// btnPesquisar.setEnabled(true);
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}

	}
	private void limparCampos() {

		txtBarras.setText(null);
		txtNome.setText(null);
		txtEstoque.setText(null);
		txtEstoqueMin.setText(null);
		txtDesc.setText(null);
		txtArmazem.setText(null);
		txtIDFor.setText(null);
		txtID.setText(null);
		txtValor.setText(null);
		txtLote.setText(null);
		txtFabricante.setText(null);
		
		txtLucro.setText(null);
		dateEnt.setDate(null);
		dateVal.setDate(null);
		lblFoto.setIcon(null);
		dateEnt.setDate(null);
		dateVal.setDate(null);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		// codigo,idfor,barcode,descricao,foto,estoque,estoquemin,valor,um,locl,nome,lote,fabricante,dataent,dataval,garantia,lucro

	}
}
