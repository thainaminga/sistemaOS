package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import java.awt.SystemColor;

public class Principal extends JFrame {
	// Instanciar OBJ JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	//estes componentes serao alterados pela tela de login
	public JLabel lblUsuario;
	public JPanel panelRodape;
	public JButton btnRelatorios;
	public JButton btnUsuarios;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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

	public Principal() {
		setFont(new Font("Bodoni MT", Font.ITALIC, 12));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setResizable(false);
		setBackground(new Color(255, 255, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/Relojoaleria.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// Ativação da janela
				status();
				setarData();
			}
		});
		setTitle("Relojoaria Sistema OS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 608);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(64, 128, 128));
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnUsuarios = new JButton("");
		btnUsuarios.setEnabled(false);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/usu.png")));
		btnUsuarios.setBounds(46, 73, 128, 128);
		contentPane.add(btnUsuarios);

		JButton btnSobre = new JButton("");
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setToolTipText("Sobre");
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
		btnSobre.setBorder(null);
		btnSobre.setContentAreaFilled(false);
		btnSobre.setBounds(566, 11, 48, 48);
		contentPane.add(btnSobre);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
		lblStatus.setBounds(576, 487, 48, 48);
		contentPane.add(lblStatus);

		JLabel lblLogo = new JLabel("New label");
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/TTP.png")));
		lblLogo.setBounds(231, 212, 128, 128);
		contentPane.add(lblLogo);

		JLabel lblNewLabel = new JLabel("Relojoaria");
		lblNewLabel.setFont(new Font("Bodoni MT", Font.ITALIC, 30));
		lblNewLabel.setBounds(228, 11, 172, 59);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("            TTP");
		lblNewLabel_1.setFont(new Font("Bodoni MT", Font.ITALIC, 15));
		lblNewLabel_1.setBounds(231, 351, 125, 14);
		contentPane.add(lblNewLabel_1);

		btnRelatorios = new JButton("");
		btnRelatorios.setEnabled(false);
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setToolTipText("Relatório");
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/relatoiro.png")));
		btnRelatorios.setBounds(402, 73, 128, 128);
		contentPane.add(btnRelatorios);

		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
				//
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/Cliente.png")));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setBounds(46, 223, 128, 128);
		contentPane.add(btnClientes);

		JButton btnServicos = new JButton("");
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
				
			}
		});
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setIcon(new ImageIcon(Principal.class.getResource("/img/serviço.png")));
		btnServicos.setToolTipText("Serviço");
		btnServicos.setBounds(402, 223, 128, 128);
		contentPane.add(btnServicos);
		
		JButton btnFornecedor = new JButton("");
		btnFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setVisible(true);
				
			}
		});
		btnFornecedor.setIcon(new ImageIcon(Principal.class.getResource("/img/fornecedor.png")));
		btnFornecedor.setToolTipText("Fornecedor");
		btnFornecedor.setBounds(46, 380, 128, 128);
		contentPane.add(btnFornecedor);
				
				panelRodape = new JPanel();
				panelRodape.setBackground(new Color(64, 128, 128));
				panelRodape.setBounds(0, 531, 636, 38);
				contentPane.add(panelRodape);
				panelRodape.setLayout(null);
				
				JLabel lblNewLabel_2 = new JLabel("Usúario:");
				lblNewLabel_2.setFont(new Font("Bodoni MT", Font.ITALIC, 15));
				lblNewLabel_2.setForeground(new Color(255, 255, 255));
				lblNewLabel_2.setBounds(10, 4, 63, 25);
				panelRodape.add(lblNewLabel_2);
				
				lblUsuario = new JLabel("");
				lblUsuario.setFont(new Font("Bodoni MT", Font.ITALIC, 15));
				lblUsuario.setForeground(new Color(255, 255, 255));
				lblUsuario.setBounds(75, 4, 194, 25);
				panelRodape.add(lblUsuario);
				
						lblData = new JLabel("New label");
						lblData.setBounds(236, 4, 278, 25);
						panelRodape.add(lblData);
						lblData.setForeground(new Color(255, 255, 255));
						lblData.setFont(new Font("Bodoni MT", Font.ITALIC, 20));
						
						JButton btnNewButton = new JButton("");
						btnNewButton.setIcon(new ImageIcon(Principal.class.getResource("/img/proodutoo.png")));
						btnNewButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Produtos produtos = new Produtos();
								produtos.setVisible(true);
							}
						});
						btnNewButton.setBounds(402, 380, 128, 128);
						contentPane.add(btnNewButton);
	}

	private void status() {
		try {
			// abrir a conexão
			con = dao.conectar();
			if (con == null) {
				System.out.println(" Erro de conexão! ");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
			} else {
				System.out.println(" Banco conectado! ");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dbon.png")));
			}
			// Nunca esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}
	}// fim do metodo status()

	/**
	 * Metodo responsavel por setar a data no rodape
	 */
	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		// alterar o texto da label pela data atual formatada
		lblData.setText(formatador.format(data));
		setLocationRelativeTo(null);
	}
}// O FIMMMMM
