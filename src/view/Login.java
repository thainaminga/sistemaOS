package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import utils.Validador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Cursor;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class Login extends JFrame {
	// objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	// objeto tela principal
	Principal principal = new Principal();

	private JPanel contentPane;
	private JPasswordField txtSenha;
	private JTextField txtLogin;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("Login");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/Relojoaleria.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 259);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Login: ");
		lblNewLabel.setForeground(new Color(192, 192, 192));
		lblNewLabel.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel.setBounds(146, 58, 79, 20);
		contentPane.add(lblNewLabel);

		txtLogin = new JTextField();
		txtLogin.setForeground(new Color(192, 192, 192));
		txtLogin.setBackground(new Color(169, 169, 169));
		txtLogin.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtLogin.setBounds(202, 59, 293, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		
		txtLogin.setDocument(new Validador(30));

		JLabel lblNewLabel_1 = new JLabel("Senha: ");
		lblNewLabel_1.setForeground(new Color(192, 192, 192));
		lblNewLabel_1.setFont(new Font("Bodoni MT", Font.BOLD, 15));
		lblNewLabel_1.setBounds(146, 123, 94, 14);
		contentPane.add(lblNewLabel_1);

		txtSenha = new JPasswordField();
		txtSenha.setForeground(new Color(192, 192, 192));
		txtSenha.setBackground(new Color(169, 169, 169));
		txtSenha.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtSenha.setBounds(202, 121, 185, 20);
		contentPane.add(txtSenha);
		
		txtSenha.setDocument(new Validador(20));

		JButton btnAcessar = new JButton("");
		btnAcessar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAcessar.setIcon(new ImageIcon(Login.class.getResource("/img/acess.png")));
		btnAcessar.setToolTipText("Acessar");
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnAcessar.setFont(new Font("Bodoni MT", Font.BOLD, 13));
		btnAcessar.setBounds(447, 93, 48, 48);
		contentPane.add(btnAcessar);
		
		getRootPane().setDefaultButton(btnAcessar);
		
		
		lblStatus = new JLabel("New label");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dboff.png")));
		lblStatus.setBounds(477, 161, 48, 48);
		contentPane.add(lblStatus);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/img/TTP.png")));
		lblNewLabel_2.setBounds(10, 42, 128, 128);
		contentPane.add(lblNewLabel_2);
	}// FINI CONSTRUTOR

	/**
	 * metodo para
	 */
	private void logar() {
		// Criar váriavel/objeto para capturar a senha
		String capturarSenha = new String(txtSenha.getPassword());
		// validação
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (capturarSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {
			// logica principal

			String read = "select * from usuarios where login =? and senha=md5(?)";
			try {
				con = dao.conectar();
				// preparar a execução da query (instrução sql, CRUD CREATE)
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturarSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					//capturar o perfil do usuario
					//System.out.println(rs.getString(5));
					
					String perfil = rs.getString(5);
					if(perfil.equals("admin")) {
						// logar > acessar tela principal
						principal.setVisible(true);
						//setar a lebal da tela principal com o nime do usuario
						principal.lblUsuario.setText(rs.getString(2));
						// fechar atela de login após acessar
						//abilitar
						principal.btnRelatorios.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						
						principal.panelRodape.setBackground(Color.RED);
						this.dispose();
				
					}else {
						// logar > acessar tela principal
						principal.setVisible(true);
						//setar a lebal da tela principal com o nime do usuario
						principal.lblUsuario.setText(rs.getString(2));
						this.dispose();
					}
			
				} else {
					JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválidos");
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

}

	private void status() {
		try {
			// abrir a conexão
			con = dao.conectar();
			if (con == null) {
				//System.out.println(" Erro de conexão! ");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
			} else {
				//System.out.println(" Banco conectado! ");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dbon.png")));
			}
			// Nunca esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}
	}// fim do metodo status()
}// O FIMMMMMMM
