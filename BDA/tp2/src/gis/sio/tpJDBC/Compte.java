package gis.sio.tpJDBC;

import java.sql.*;

public class Compte {

	private Connection connect;
	private PreparedStatement stmt;
	private ResultSet rs;

	public Compte(String url, String user, String passwd, String idClient) throws CompteInconnuException, SQLException {
		this.connect = DriverManager.getConnection(url, user, passwd);

		String request = "SELECT * FROM compte WHERE id_client = ?";
		this.stmt = this.connect.prepareStatement(request, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		this.stmt.setString(1, idClient);

		this.rs = this.stmt.executeQuery();
		
		if (!this.rs.next()) {
			throw new CompteInconnuException();
		}
	}

	public Connection getConnection() {
		return this.connect;
	}

	public double getSolde() throws SQLException {
		this.rs.refreshRow();
		
		return this.rs.getDouble("solde");
	}

	public void setSolde(double value) throws SQLException {
		this.rs.updateDouble("solde", value);
		this.rs.updateRow();
	}

	public String getIdClient() throws SQLException {
		this.rs.refreshRow();
		
		return this.rs.getString("id_client");
	}

	public void close() throws SQLException {
		this.stmt.close();
		this.connect.close();
	}
}
