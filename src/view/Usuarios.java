package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DAO;
import utils.Validador;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.border.EmptyBorder;

public class Usuarios extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnDeletar;
	private JButton btnPesquisar;
	private JPasswordField txtSenha2;
	private JScrollPane scrollPaneUsers;
	private JList listUsers;
	private final JComboBox cboPerfil = new JComboBox();
	private JCheckBox chckSenha;
	private JButton btnApagar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
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
	public Usuarios() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneUsers.setVisible(false);
			}
		});
		getContentPane().setBackground(new Color(128, 128, 128));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/usu.png")));
		setTitle("Usuários");
		setModal(true);
		setBounds(100, 100, 713, 334);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setToolTipText("Usuários");
		lblNewLabel.setBorder(null);
		lblNewLabel.setIcon(new ImageIcon(Usuarios.class.getResource("/img/usu.png")));
		lblNewLabel.setBounds(10, 11, 128, 128);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel_1.setBounds(168, 40, 63, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Nome");
		lblNewLabel_2.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel_2.setBounds(168, 117, 63, 28);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Login");
		lblNewLabel_3.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel_3.setBounds(168, 78, 63, 28);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Senha");
		lblNewLabel_4.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel_4.setBounds(168, 191, 63, 14);
		getContentPane().add(lblNewLabel_4);

		txtID = new JTextField();
		txtID.setBackground(new Color(192, 192, 192));
		txtID.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtID.setEditable(false);
		txtID.setBounds(229, 38, 150, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		txtNome = new JTextField();
		txtNome.setBackground(new Color(192, 192, 192));
		txtNome.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtNome.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				// soltar
				listarUsuarios();
			}

		});
		txtNome.setForeground(Color.RED);
		txtNome.setCaretColor(Color.RED);
		txtNome.setBounds(229, 122, 311, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		txtNome.setDocument(new Validador(30));

		txtLogin = new JTextField();
		txtLogin.setBackground(new Color(192, 192, 192));
		txtLogin.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtLogin.setBounds(229, 83, 311, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);

		txtLogin.setDocument(new Validador(30));

		btnPesquisar = new JButton("");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		getRootPane().setDefaultButton(btnPesquisar);

		btnPesquisar.setToolTipText("Buscar");
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.setBorderPainted(false);
		btnPesquisar.setBorder(null);
		btnPesquisar.setSelected(true);
		btnPesquisar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/pesquisar.png")));
		btnPesquisar.setBounds(548, 65, 48, 48);
		getContentPane().add(btnPesquisar);

		btnApagar = new JButton("");
		btnApagar.setToolTipText("Limpar");
		btnApagar.setBorder(null);
		btnApagar.setContentAreaFilled(false);
		btnApagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnApagar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/borracha.png")));
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnApagar.setBounds(526, 229, 48, 48);
		getContentPane().add(btnApagar);

		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon(Usuarios.class.getResource("/img/TTP.png")));
		lblNewLabel_6.setBounds(10, 149, 128, 128);
		getContentPane().add(lblNewLabel_6);

		btnAdicionar = new JButton("");
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/add.png")));
		btnAdicionar.setBounds(272, 220, 64, 64);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckSenha.isSelected()) {
					editarUsuario();
				}else {
					editarUsuarioExcetoSenha();
				}
			}
		});
		btnEditar.setBorderPainted(false);
		btnEditar.setBorder(null);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/edit (2).png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(368, 229, 48, 48);
		getContentPane().add(btnEditar);

		btnDeletar = new JButton("");
		btnDeletar.setEnabled(false);
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();

			}
		});
		btnDeletar.setBorder(null);
		btnDeletar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeletar.setContentAreaFilled(false);
		btnDeletar.setToolTipText("Deletar");
		btnDeletar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/delete (2).png")));
		btnDeletar.setBounds(439, 220, 64, 64);
		getContentPane().add(btnDeletar);

		txtSenha2 = new JPasswordField();
		txtSenha2.setBackground(new Color(192, 192, 192));
		txtSenha2.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtSenha2.setEnabled(false);
		txtSenha2.setBounds(229, 189, 150, 20);
		getContentPane().add(txtSenha2);

		txtSenha2.setDocument(new Validador(60));

		scrollPaneUsers = new JScrollPane();
		scrollPaneUsers.setVisible(false);
		scrollPaneUsers.setDoubleBuffered(true);
		scrollPaneUsers.setBounds(229, 137, 311, 68);
		getContentPane().add(scrollPaneUsers);

		listUsers = new JList();
		listUsers.setBackground(new Color(192, 192, 192));
		listUsers.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista();
			}

		});
		scrollPaneUsers.setViewportView(listUsers);
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] {"", "admin", "user"}));
		cboPerfil.setBounds(576, 163, 82, 31);
		getContentPane().add(cboPerfil);
		
		JLabel lblNewLabel_5 = new JLabel("Perfil");
		lblNewLabel_5.setBounds(576, 139, 46, 14);
		getContentPane().add(lblNewLabel_5);
		
		chckSenha = new JCheckBox("Alterar senha");
		chckSenha.setBackground(new Color(128, 128, 128));
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckSenha.isSelected()) {
				txtSenha2.setText(null);
				txtSenha2.setEnabled(true);
				txtSenha2.requestFocus();
				
				txtSenha2.setBackground(Color.GREEN);
				}else {
					txtSenha2.setBackground(Color.GRAY);
					txtSenha2.setEnabled(false);
				}
			}
		});
		chckSenha.setBounds(413, 37, 150, 23);
		getContentPane().add(chckSenha);

	}//

	private void LimparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha2.setText(null);
		cboPerfil.setSelectedItem("");
		chckSenha.setSelected(false);
		scrollPaneUsers.setVisible(false);

		//
		btnApagar.setEnabled(true);
		btnDeletar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnAdicionar.setEnabled(false);

	}// Fim do metodo Limpar Campos()

	private void buscar() {
		// dica - testar o evento primeiro
		// System.out.println("teste do botão buscar");
		// criar uma variavel com a query (instrução do banco)

		// String read = "select * from usuarios where nome = ?";
		String read = "select * from usuarios where login = ?";
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execução de query(intrução sql - CRUD Read)
			//
			pst = con.prepareStatement(read);
			// pst.setString(1, txtNome.getText());
			pst.setString(1, txtLogin.getText());
			//
			rs = pst.executeQuery();
			//
			//
			if (rs.next()) {
				//
				txtID.setText(rs.getString(1));//
				txtNome.setText(rs.getString(2));//
				txtLogin.setText(rs.getString(3));//
				txtSenha2.setText(rs.getString(4));//
				cboPerfil.setSelectedItem(rs.getString(5));
				//
				btnEditar.setEnabled(true);
				btnDeletar.setEnabled(true);
			} else {
				// System.out.println("Contatos não cadastrados");
				JOptionPane.showMessageDialog(null, "Usuario inexistente");
				btnAdicionar.setEnabled(true);
				btnEditar.setEnabled(true);
				txtSenha2.setBackground(Color.GRAY);
				

			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void adicionar() {
		String capturarSenha = new String(txtSenha2.getPassword());
		// System.out.println(" !!!!TESTE!!!! ");
		// validação de campos obrigatorios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do usúario");
			txtNome.requestFocus();

		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login do usúario");
			txtLogin.requestFocus();

		} else if (capturarSenha.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o senha do usúario");
			txtSenha2.requestFocus();
			
		} else if (cboPerfil.getSelectedItem().equals(" ")) {
			JOptionPane.showMessageDialog(null, "Digite a perfil do usuario");
			cboPerfil.requestFocus();

		}else {

			// lógica principal
			 String create = "insert into usuarios (nome,login,senha,perfil)values (?,?,md5(?),?)";
			//String create = "insert into usuarios (nome,login,senha,perfil)values (?,?,?,?)";
			// tratamento de exceções
			try {

				// abrir a conexão
				con = dao.conectar();

				pst = con.prepareStatement(create);

				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturarSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, " Usúario adicionado! ");

				LimparCampos();

				con.close();

			}

			catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste login já está sendo utilizado.");
				txtLogin.setText(null);
				txtLogin.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	private void editarUsuario() {
		// System.out.println("teste do botão editar");
		// validar campos obrigatorios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuario");
			txtNome.requestFocus();

		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuario");
			txtLogin.requestFocus();

		} else if (txtSenha2.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a senha do usuario");
			txtSenha2.requestFocus();

		} else if (cboPerfil.getSelectedItem().equals(" ")) {
			JOptionPane.showMessageDialog(null, "Digite a perfil do usuario");
			cboPerfil.requestFocus();

		} else {
			// logica principal
			// CRUD - Update
			String update = "update usuarios set nome =?, login=?, senha=md5(?), perfil=? where id=?";
			// trat de exceção
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha2.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				//cboperfil.getselectrditem () .toString();
				pst.setString(5, txtID.getText());
				// Executar query
				pst.executeUpdate();
				// confirmar para o user
				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso!");
				// limpar campos
				LimparCampos();
				// fechar conexao
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}
	private void editarUsuarioExcetoSenha() {
		// System.out.println("teste do botão editar");
		// validar campos obrigatorios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuario");
			txtNome.requestFocus();

		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuario");
			txtLogin.requestFocus();

		} else if (cboPerfil.getSelectedItem().equals(" ")) {
			JOptionPane.showMessageDialog(null, "Digite a perfil do usuario");
			cboPerfil.requestFocus();

		} else {
			// logica principal
			// CRUD - Update
			String update2 = "update usuarios set nome =?, login=?, perfil=? where id=?";
			// trat de exceção
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(update2);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				//cboperfil.getselectrditem () .toString();
				pst.setString(4, txtID.getText());
				// Executar query
				pst.executeUpdate();
				// confirmar para o user
				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso!");
				// limpar campos
				LimparCampos();
				// fechar conexao
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	private void excluirUsuario() {
		// System.out.println("teste do botão");
		// validação de exclusão - a variavel comfirma captura a opção escolida
		int confirma = JOptionPane.showConfirmDialog(null, " Confirmar a exclusão desse usuario? ", " !!!Atenção!!! ",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where id=?";

			try {
				con = dao.conectar();

				pst = con.prepareStatement(delete);

				pst.setString(1, txtID.getText());

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, " Usuario excluído! ");
				LimparCampos();

				con.close();

			}

			catch (Exception e) {
				System.out.print(e);
			}
		}
	}

	/**
	 * 
	 */

	private void listarUsuarios() {
		// System.out.println("teste");

		// linha abaixo cria um objeto usando como referencia um vetor dinamico , este
		// objeto ira temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar a lista -> modelo
		listUsers.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select *from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			
			// uso do while para trazer os usuarios enquanto exix=stir
			while (rs.next()) {
				// mostrar a barra de rolagem(scrollpane)
				scrollPaneUsers.setVisible(true);
				// adcionar os usuarios novertor -> lista
				modelo.addElement(rs.getString(2));

					
				if (txtNome.getText().isEmpty()) {
					scrollPaneUsers.setVisible(false);
					
				}

			} // fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * metodo que busca o usuario selecionado da lista
	 */
	private void buscarUsuarioLista() {
		// System.out.println("teste");
		// variavel que captuar o indice da linha da lista
		int linha = listUsers.getSelectedIndex();
		if (linha >= 0) {
			// String readBuscaLista=
			// Query (instrução sql)
			// limite " , 1" -> selecionar o indice 0 e 1 usuario da lista
			String readBuscaLista = "select *from usuarios where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " ,1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readBuscaLista);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneUsers.setVisible(false);

					txtID.setText(rs.getString(1));//
					txtNome.setText(rs.getString(2));//
					txtLogin.setText(rs.getString(3));//
					txtSenha2.setText(rs.getString(4));//
					cboPerfil.setSelectedItem(rs.getString(5));
					
					btnAdicionar.setEnabled(true);
					btnEditar.setEnabled(true);
					btnDeletar.setEnabled(true);
				} else {
					// System.out.println("Contatos não cadastrados");
					JOptionPane.showMessageDialog(null, "Usuario inexistente");

				}
				con.close();
			} catch (Exception e) {

			}

		} else {
			scrollPaneUsers.setVisible(false);

		}
	}
}// FIM
