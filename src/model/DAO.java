package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {

	// VARIÁVEIS PARA CONFIGURAR O BANCO DE DADOS
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.26.45.222:3306/dbsistema";
	private String user = "root";
	private String password = "123@senac";

	// Criação de um objeto para uso da classe Connection(JDBC)
	// <Ctrl> <shift> <O> = importa as palavras
	//<Ctrl> <shift> <F> = Alinhamento do codigo
	//<alt> <shift> <y> = quebra de linha 
	//Try <Ctrl> <espaço> = completa a linha
	private Connection con;
	/**
	 * Metodo responsável por abrir conexão com o banco
	 * @return con
	 */
	public Connection conectar() {
		// tratamento de exceções
		try {
			// as linhas abaixa abrem a conexão com o banco
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;

		} catch (Exception e) {
			System.out.println(e);
			return null;
			
		}

	}

}

