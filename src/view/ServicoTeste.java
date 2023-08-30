package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.DebugGraphics;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import java.awt.Choice;

public class ServicoTeste extends JDialog {
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtEquipamento;
	private JTextField txtValor;
	private JTextField txtID;
	private JTextField txtDefeito;

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JTextField txtCliente;
	private JLabel lblID;
	private JScrollPane scrollPaneClientes;
	private JList listClientes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServicoTeste dialog = new ServicoTeste();
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
	public ServicoTeste() {
		setTitle("Serviços");
		getContentPane().setBackground(new Color(128, 128, 128));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneClientes.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(ServicoTeste.class.getResource("/img/serviço.png")));
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		JLabel lblOS = new JLabel("OS*");
		lblOS.setForeground(new Color(192, 192, 192));
		lblOS.setBounds(28, 25, 46, 20);
		getContentPane().add(lblOS);

		txtOS = new JTextField();
		txtOS.setForeground(new Color(255, 255, 255));
		txtOS.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtOS.setBackground(new Color(169, 169, 169));
		txtOS.setEditable(false);
		txtOS.setBounds(28, 45, 139, 20);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);

		JLabel lblNewLabel = new JLabel("Data*");
		lblNewLabel.setForeground(new Color(192, 192, 192));
		lblNewLabel.setBounds(28, 85, 213, 22);
		getContentPane().add(lblNewLabel);

		txtData = new JTextField();
		txtData.setForeground(new Color(255, 255, 255));
		txtData.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtData.setBackground(new Color(169, 169, 169));
		txtData.setEditable(false);
		txtData.setBounds(28, 104, 234, 22);
		getContentPane().add(txtData);
		txtData.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Bijuteria*");
		lblNewLabel_1.setForeground(new Color(192, 192, 192));
		lblNewLabel_1.setBounds(254, 148, 144, 20);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Valor");
		lblNewLabel_2.setForeground(new Color(192, 192, 192));
		lblNewLabel_2.setBounds(28, 148, 46, 20);
		getContentPane().add(lblNewLabel_2);

		txtEquipamento = new JTextField();
		txtEquipamento.setForeground(new Color(255, 255, 255));
		txtEquipamento.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtEquipamento.setBackground(new Color(169, 169, 169));
		txtEquipamento.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		txtEquipamento.setBounds(254, 167, 234, 20);
		getContentPane().add(txtEquipamento);
		txtEquipamento.setColumns(10);

		txtValor = new JTextField();
		txtValor.setForeground(new Color(255, 255, 255));
		txtValor.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtValor.setBackground(new Color(169, 169, 169));
		txtValor.setBounds(28, 168, 209, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

		JButton btnBuscar = new JButton("");
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorder(null);
		btnBuscar.setToolTipText("Buscar");
		btnBuscar.setIcon(new ImageIcon(ServicoTeste.class.getResource("/img/pesquisar.png")));
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});

		getRootPane().setDefaultButton(btnBuscar);

		btnBuscar.setBounds(177, 26, 48, 48);
		getContentPane().add(btnBuscar);

		JButton bntAdicionar = new JButton("");
		bntAdicionar.setContentAreaFilled(false);
		bntAdicionar.setBorder(null);
		bntAdicionar.setIcon(new ImageIcon(ServicoTeste.class.getResource("/img/add.png")));
		bntAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bntAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		bntAdicionar.setBounds(51, 472, 48, 48);
		getContentPane().add(bntAdicionar);

		JButton bntEditar = new JButton("");
		bntEditar.setContentAreaFilled(false);
		bntEditar.setBorder(null);
		bntEditar.setIcon(new ImageIcon(ServicoTeste.class.getResource("/img/edit (2).png")));
		bntEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bntEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarOS();
			}
		});
		bntEditar.setBounds(193, 472, 48, 48);
		getContentPane().add(bntEditar);

		JButton btnExcluir = new JButton("");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setIcon(new ImageIcon(ServicoTeste.class.getResource("/img/delete (2).png")));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOS();
			}
		});
		btnExcluir.setBounds(512, 472, 48, 48);
		getContentPane().add(btnExcluir);

		JLabel lblNewLabel_3 = new JLabel("Defeito*");
		lblNewLabel_3.setForeground(new Color(192, 192, 192));
		lblNewLabel_3.setBounds(28, 223, 57, 17);
		getContentPane().add(lblNewLabel_3);

		txtDefeito = new JTextField();
		txtDefeito.setForeground(new Color(255, 255, 255));
		txtDefeito.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtDefeito.setBackground(new Color(169, 169, 169));
		txtDefeito.setBounds(51, 276, 460, 115);
		getContentPane().add(txtDefeito);
		txtDefeito.setColumns(10);

		JButton btnLimpar = new JButton("");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setIcon(new ImageIcon(ServicoTeste.class.getResource("/img/borracha.png")));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnLimpar.setBounds(672, 472, 48, 48);
		getContentPane().add(btnLimpar);

		JPanel panel = new JPanel();
		panel.setForeground(new Color(192, 192, 192));
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cliente",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(272, 28, 206, 99);
		getContentPane().add(panel);
		panel.setLayout(null);

		txtCliente = new JTextField();
		txtCliente.setForeground(new Color(255, 255, 255));
		txtCliente.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtCliente.setBackground(new Color(169, 169, 169));
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});

		scrollPaneClientes = new JScrollPane();
		scrollPaneClientes.setVisible(false);
		scrollPaneClientes.setBounds(10, 41, 186, 39);
		panel.add(scrollPaneClientes);

		listClientes = new JList();
		listClientes.setForeground(new Color(255, 255, 255));
		listClientes.setBackground(new Color(192, 192, 192));
		listClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClientesLista();
			}
		});
		scrollPaneClientes.setViewportView(listClientes);
		txtCliente.setBounds(10, 23, 186, 20);
		panel.add(txtCliente);
		txtCliente.setColumns(10);

		txtID = new JTextField();
		txtID.setForeground(new Color(0, 0, 0));
		txtID.setEditable(false);
		txtID.setBounds(10, 60, 86, 20);
		panel.add(txtID);
		txtID.setColumns(10);

		lblID = new JLabel("ID*");
		lblID.setBounds(10, 42, 100, 20);
		panel.add(lblID);

		JButton btnNewButton = new JButton("Pesquisa");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBounds(107, 59, 89, 23);
		panel.add(btnNewButton);

		JButton btnOS = new JButton("");
		btnOS.setContentAreaFilled(false);
		btnOS.setBorder(null);
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setIcon(new ImageIcon(ServicoTeste.class.getResource("/img/imprimir.png")));
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(350, 472, 48, 48);
		getContentPane().add(btnOS);

	}

	private void LimparCampos() {
		txtCliente.setText(null);
		txtOS.setText(null);
		txtData.setText(null);
		txtEquipamento.setText(null);
		txtDefeito.setText(null);
		txtValor.setText(null);
		txtID.setText(null);

		// scrollPaneServico.setVisible(false);

		//// (os,dataOS,equipamento,defeito,valor,idcli)
		// btnDeletar.setEnabled(false);
		// btnEditar.setEnabled(false);
		// btnAdicionar.setEnabled(false);

	}// Fim do metodo Limpar Campos()

	private void buscar() {
		String numOS = JOptionPane.showInputDialog("Número da OS ");

		String read = "select * from servicos inner join clientes on servicos.idcli= clientes.idcli where os = ?";
		try {

			con = dao.conectar();

			pst = con.prepareStatement(read);

			pst.setString(1, numOS);

			rs = pst.executeQuery();

			if (rs.next()) {
				//

				txtOS.setText(rs.getString(1));//
				txtData.setText(rs.getString(2));//
				txtEquipamento.setText(rs.getString(3));//
				txtDefeito.setText(rs.getString(4));//
				txtValor.setText(rs.getString(5));//
				txtID.setText(rs.getString(6));//
				txtCliente.setText(rs.getString(8));//

				// btnEditar.setEnabled(true);
				// btnDeletar.setEnabled(true);

			} else {

				JOptionPane.showMessageDialog(null, "Serviço inexistente! ");
				// btnAdicionar.setEnabled(true);

			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void adicionar() {
		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Bijuteria para o serviço! ");
			txtEquipamento.requestFocus();

		} else if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do cliente!");
			txtID.requestFocus();

		} else {

			String create = "insert into servicos (equipamento,defeito,valor,idcli)values (?,?,?,?)";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(create);

				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, " Serviço adicionado! ");

				LimparCampos();

				con.close();

			}

			catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Serviço não adicionado.\nEsta OS já está sendo utilizado.");
				txtOS.setText(null);
				txtOS.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	private void editarOS() {
		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a bijuteria do cliente ");
			txtEquipamento.requestFocus();

		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a defeito do cliente ");
			txtDefeito.requestFocus();

		} else {
			// logica principal
			// CRUD - Update
			String update = "update servicos set os =?, equipamento=?, defeito=? where idcli=?";
			// trat de exceção
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(update);
				pst.setString(1, txtOS.getText());
				pst.setString(2, txtEquipamento.getText());
				pst.setString(3, txtDefeito.getText());
				pst.setString(4, txtID.getText());
				// Executar query
				pst.executeUpdate();
				// confirmar para o user
				JOptionPane.showMessageDialog(null, "Dados do serviço editado com sucesso! ");
				// limpar campos
				LimparCampos();
				// fechar conexao
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	private void excluirOS() {

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo equipamento");
			txtEquipamento.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo defeito");
			txtDefeito.requestFocus();

		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo valor");
			txtValor.requestFocus();

		} else {

			int confirma = JOptionPane.showConfirmDialog(null, " Confirmar a exclusão desse serviço? ",
					" !!!Atenção!!! ", JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {

				String delete = "delete from servicos where idcli=?";

				try {
					con = dao.conectar();

					pst = con.prepareStatement(delete);

					pst.setString(1, txtID.getText());

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, " Serviço excluído! ");
					LimparCampos();

					con.close();

				}

				catch (Exception e) {
					System.out.print(e);
				}
			}
		}
	}

	private void listarClientes() {

		DefaultListModel<String> modelo = new DefaultListModel<>();

		listClientes.setModel(modelo);

		String readLista = "select *from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();

			while (rs.next()) {

				scrollPaneClientes.setVisible(true);

				modelo.addElement(rs.getString(2));

				if (txtCliente.getText().isEmpty()) {
					scrollPaneClientes.setVisible(false);
				}

			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void buscarClientesLista() {

		int linha = listClientes.getSelectedIndex();
		if (linha >= 0) {
			String readBuscaLista = "select *from clientes where nome like '" + txtCliente.getText() + "%'"
					+ "order by nome limit " + (linha) + " ,1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readBuscaLista);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneClientes.setVisible(false);

					txtID.setText(rs.getString(1));//
					txtCliente.setText(rs.getString(2));//

				} else {
					JOptionPane.showMessageDialog(null, "Cliente inexistente! ");

				}
				con.close();
			} catch (Exception e) {

			}

		} else {
			scrollPaneClientes.setVisible(false);

		}
	}

	/**
	 * Impressão da OS
	 */
	private void imprimirOS() {
	    if (txtEquipamento.getText().isEmpty() || txtDefeito.getText().isEmpty() || txtValor.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de gerar o relatório");
	        return;
	    }

	    Document document = new Document();

	    try {
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
	        document.open();

	        // Adicionar imagem abaixo do cabeçalho
	        Image logo = Image.getInstance(ServicoTeste.class.getResource("/img/TTP.png"));
	        logo.scaleToFit(192, 148);
	        logo.setAlignment(Element.ALIGN_CENTER);
	        document.add(logo);
	        document.add(Chunk.NEWLINE);

	        // Adicionar cabeçalho
	        Paragraph header = new Paragraph("Ordem de Serviço", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD));
	        header.setAlignment(Element.ALIGN_CENTER);
	        document.add(header);

	        
	        // Adicionar detalhes da OS
	        String readOS = "SELECT * FROM servicos WHERE os = ?";
	        try (Connection con = dao.conectar(); PreparedStatement pst = con.prepareStatement(readOS)) {
	            pst.setString(1, txtOS.getText());
	            try (ResultSet rs = pst.executeQuery()) {
	                if (rs.next()) {
	                	
	                    document.add(Chunk.NEWLINE);
	                    Paragraph os = new Paragraph("Número da OS: " + rs.getString(1), FontFactory.getFont(FontFactory.HELVETICA, 12));
	                    os.setAlignment(Element.ALIGN_LEFT);
	                    document.add(os);
	                    
	                 // Adicionar informações do cliente
	        	        Paragraph clienteInfo = new Paragraph("Cliente: " + txtCliente.getText(), FontFactory.getFont(FontFactory.HELVETICA, 12));
	        	        clienteInfo.setAlignment(Element.ALIGN_LEFT);
	        	        document.add(clienteInfo);


	                    Paragraph data = new Paragraph("Data: " + rs.getString(2), FontFactory.getFont(FontFactory.HELVETICA, 12));
	                    data.setAlignment(Element.ALIGN_LEFT);
	                    document.add(data);

	                    Paragraph equipamento = new Paragraph("Equipamento: " + rs.getString(3), FontFactory.getFont(FontFactory.HELVETICA, 12));
	                    equipamento.setAlignment(Element.ALIGN_LEFT);
	                    document.add(equipamento);

	                    Paragraph defeito = new Paragraph("Defeito: " + rs.getString(4), FontFactory.getFont(FontFactory.HELVETICA, 12));
	                    defeito.setAlignment(Element.ALIGN_LEFT);
	                    document.add(defeito);
	                    
	                    Paragraph valor = new Paragraph("Valor Serviço: " + rs.getString(5), FontFactory.getFont(FontFactory.HELVETICA, 12));
	                    valor.setAlignment(Element.ALIGN_LEFT);
	                    document.add(valor);
	                     
	                    
	                } else {
	                    JOptionPane.showMessageDialog(null, "Nenhuma OS encontrada para o número informado");
	                    return;
	                }
	            }
	        }

	        // Adicionar rodapé
	        PdfContentByte cb = writer.getDirectContent();
	        Phrase footer = new Phrase(
	                "Serviço gerado em: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
	                FontFactory.getFont(FontFactory.HELVETICA, 10));
	        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
	                (document.right() - document.left()) / 2 + document.leftMargin(),
	                document.bottom() - 10, 0);

	    } catch (Exception e) {
	        System.out.println("Erro: " + e.getMessage());
	        return;
	    } finally {
	        document.close();
	    }

	    try {
	        Desktop.getDesktop().open(new File("os.pdf"));
	    } catch (Exception e) {
	        System.out.println("Erro ao abrir o PDF: " + e.getMessage());
	    }
	}
}