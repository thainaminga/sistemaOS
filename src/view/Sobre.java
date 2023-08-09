package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sobre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
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
	public Sobre() {
		getContentPane().setBackground(new Color(64, 128, 128));
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/about.png")));
		setTitle("Sobre");
		setBounds(100, 100, 510, 374);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Autor: Thaina J.M. Santos");
		lblNewLabel.setFont(new Font("Bodoni MT", Font.ITALIC, 15));
		lblNewLabel.setBounds(10, 308, 276, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sob a Licença MIT");
		lblNewLabel_1.setFont(new Font("Bodoni MT", Font.ITALIC, 15));
		lblNewLabel_1.setBounds(339, 256, 171, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		lblNewLabel_2.setBounds(339, 142, 128, 128);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Relojoaria");
		lblNewLabel_3.setFont(new Font("Bodoni MT", Font.ITALIC, 30));
		lblNewLabel_3.setBounds(163, 22, 222, 71);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Sobre.class.getResource("/img/TTP.png")));
		lblLogo.setBounds(33, 124, 128, 128);
		getContentPane().add(lblLogo);
		
		JLabel lblNewLabel_1_1 = new JLabel("TTP");
		lblNewLabel_1_1.setFont(new Font("Bodoni MT", Font.ITALIC, 15));
		lblNewLabel_1_1.setBounds(81, 263, 46, 14);
		getContentPane().add(lblNewLabel_1_1);
		
		JTextPane txtpnPolticaDeConsertos = new JTextPane();
		txtpnPolticaDeConsertos.setBackground(new Color(140, 159, 166));
		txtpnPolticaDeConsertos.setEditable(false);
		txtpnPolticaDeConsertos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txtpnPolticaDeConsertos.setFont(new Font("Bodoni MT", Font.ITALIC, 15));
		txtpnPolticaDeConsertos.setText("Sistema para gestão de consertos de bijuterioas");
		txtpnPolticaDeConsertos.setBounds(81, 78, 304, 35);
		getContentPane().add(txtpnPolticaDeConsertos);
		
		JButton btnGit = new JButton("");
		btnGit.setContentAreaFilled(false);
		btnGit.setBorder(null);
		btnGit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https://github.com/thainaminga/javaJ");
			}
		});
		btnGit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGit.setIcon(new ImageIcon(Sobre.class.getResource("/img/git.png")));
		btnGit.setBounds(210, 175, 64, 64);
		getContentPane().add(btnGit);
		
		JLabel lblNewLabel_4 = new JLabel("Acesso para o Github.");
		lblNewLabel_4.setBounds(180, 142, 149, 35);
		getContentPane().add(lblNewLabel_4);
		
		

	}
	private void link(String site) {

        Desktop desktop = Desktop.getDesktop();

        try {

            JOptionPane.showMessageDialog(null, "Voce será encaminhado para o GitHub");

            URI uri = new URI(site);

            desktop.browse(uri);

           

       } catch (Exception e) {

           

           //System.out.println(e);

       }
	}
}
