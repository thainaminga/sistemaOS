package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;

public class Relatorios extends JDialog {
	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs; 

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
					Relatorios dialog = new Relatorios();
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
	public Relatorios() {
		getContentPane().setBackground(new Color(128, 128, 128));
		setTitle("Relatórios");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton btnClientes = new JButton("");
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/309041_users_group_people_icon.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		btnClientes.setBounds(77, 101, 48, 48);
		getContentPane().add(btnClientes);
		
		JButton btnServicos = new JButton("");
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatoriosServico();
				
			}
		});
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setIcon(new ImageIcon(Relatorios.class.getResource("/img/4308338_card_checklist_file_invoice_report_icon.png")));
		btnServicos.setBounds(309, 101, 48, 48);
		getContentPane().add(btnServicos);
		
		JLabel lblNewLabel = new JLabel("Fixa de Clientes");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(59, 65, 98, 31);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Fixa de Serviços");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(290, 65, 108, 31);
		getContentPane().add(lblNewLabel_1);

	}//FIM contrutor
	
	//IMPRESSÃO DE RELATÓRIOS

	private void relatorioClientes() {
	    Document document = new Document();

	    try {
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));

	        document.open();

	        // Adicionar cabeçalho com título e data
	        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
	        Paragraph title = new Paragraph("Relatório de Clientes", titleFont);
	        title.setAlignment(Element.ALIGN_CENTER);
	        document.add(title);

	        Date dataRelatorio = new Date();
	        DateFormat formatador = DateFormat.getDateInstance(DateFormat.LONG);
	        Paragraph dateParagraph = new Paragraph("Data do Relatório: " + formatador.format(dataRelatorio));
	        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
	        document.add(dateParagraph);

	        document.add(Chunk.NEWLINE);

	        // Adicionar detalhes do relatório
	        Paragraph relatorioInfo = new Paragraph("Este relatório apresenta informações sobre os clientes cadastrados na empresa.",
	                                                 FontFactory.getFont(FontFactory.HELVETICA, 12));
	        document.add(relatorioInfo);

	        document.add(Chunk.NEWLINE);

	        // Query para buscar informações
	        String readClientes = "SELECT nome, fone, email FROM clientes ORDER BY nome";

	        try {
	            con = dao.conectar();
	            pst = con.prepareStatement(readClientes);
	            rs = pst.executeQuery();

	            PdfPTable tabela = new PdfPTable(3);

	            PdfPCell headerCell = new PdfPCell(new Paragraph("Detalhes do Cliente", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
	            headerCell.setColspan(3);
	            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            tabela.addCell(headerCell);

	            tabela.addCell("Nome");
	            tabela.addCell("Telefone");
	            tabela.addCell("Email");

	            while (rs.next()) {
	                tabela.addCell(rs.getString(1));
	                tabela.addCell(rs.getString(2));
	                tabela.addCell(rs.getString(3));
	            }

	            document.add(tabela);
	            
	            
	            // Adicionar rodapé com data e hora
	            PdfContentByte cb = writer.getDirectContent();
	            Phrase footer = new Phrase("Relatório gerado em: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),FontFactory.getFont(FontFactory.HELVETICA, 10));
	            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 20, 0);


	            con.close();
	        } catch (Exception e) {
	            System.out.println("Erro ao gerar relatório: " + e.getMessage());
	        }
	    } catch (Exception e) {
	        System.out.println("Erro ao criar PDF: " + e.getMessage());
	    } finally {
	        document.close();
	    }

	    try {
	        Desktop.getDesktop().open(new File("clientes.pdf"));
	    } catch (Exception e) {
	        System.out.println("Erro ao abrir o PDF: " + e.getMessage());
	    }
	}

	private void relatoriosServico() {
	    Document document = new Document();

	    try {
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("relatorios.pdf"));

	        document.open();

	        // Adicionar cabeçalho com título e data
	        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
	        Paragraph title = new Paragraph("Relatório de Serviços", titleFont);
	        title.setAlignment(Element.ALIGN_CENTER);
	        document.add(title);

	        Date dataRelatorio = new Date();
	        DateFormat formatador = DateFormat.getDateInstance(DateFormat.LONG);
	        Paragraph dateParagraph = new Paragraph("Data do Relatório: " + formatador.format(dataRelatorio));
	        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
	        document.add(dateParagraph);

	        document.add(Chunk.NEWLINE);

	        // Adicionar detalhes do relatório
	        Paragraph relatorioInfo = new Paragraph("Este relatório apresenta informações sobre as Ordens de Serviço (OS) realizadas.",
	                                                 FontFactory.getFont(FontFactory.HELVETICA, 12));
	        document.add(relatorioInfo);

	        document.add(Chunk.NEWLINE);

	        // Query para buscar informações
	        String readRelatorios = "SELECT os, nome, dataOS, equipamento, defeito, valor FROM servicos " +
	                                "INNER JOIN clientes ON servicos.idcli = clientes.idcli ORDER BY os";

	        try {
	            con = dao.conectar();
	            pst = con.prepareStatement(readRelatorios);
	            rs = pst.executeQuery();

	            PdfPTable tabela = new PdfPTable(6);

	            PdfPCell headerCell = new PdfPCell(new Paragraph("Detalhes da Ordem de Serviço", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
	            headerCell.setColspan(6);
	            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            tabela.addCell(headerCell);

	            tabela.addCell("Número da OS");
	            tabela.addCell("Nome do Cliente");
	            tabela.addCell("Data da OS");
	            tabela.addCell("Equipamento");
	            tabela.addCell("Defeito");
	            tabela.addCell("Valor");

	            while (rs.next()) {
	                tabela.addCell(rs.getString(1));
	                tabela.addCell(rs.getString(2));
	                tabela.addCell(rs.getString(3));
	                tabela.addCell(rs.getString(4));
	                tabela.addCell(rs.getString(5));
	                tabela.addCell("R$ " + rs.getString(6));
	            }

	            document.add(tabela);
	            
	            // Adicionar rodapé com data e hora
	            PdfContentByte cb = writer.getDirectContent();
	            Phrase footer = new Phrase("Relatório gerado em: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
	                                       FontFactory.getFont(FontFactory.HELVETICA, 10));
	            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 20, 0);


	            con.close();
	        } catch (Exception e) {
	            System.out.println("Erro ao gerar relatório: " + e.getMessage());
	        }
	    } catch (Exception e) {
	        System.out.println("Erro ao criar PDF: " + e.getMessage());
	    } finally {
	        document.close();
	    }

	    try {
	        Desktop.getDesktop().open(new File("relatorios.pdf"));
	    } catch (Exception e) {
	        System.out.println("Erro ao abrir o PDF: " + e.getMessage());
	    }
	}

}//FIMMM
