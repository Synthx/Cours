package gis.sio.tpJDBC;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.*;

public class VirementCompte {

	public static void main(String args[]) {
		try {
			String nomBaseDebiteur = args[0];
			String idClientDebiteur = args[1];
			String nomBaseCrediteur = args[2];
			String idClientCrediteur = args[3];
			String value = args[4];
			
			// Lecture du fichier database.properties
			FileInputStream in = new FileInputStream("database.properties");
			Properties props = new Properties();
			props.load(in);
			String server = props.getProperty("jdbc.server");
			String username = props.getProperty("jdbc.user");
			String password = props.getProperty("jdbc.password");
			String protocoleURL = props.getProperty("jdbc.protocole");

			// Constitution des URL
			String urlDebiteur = protocoleURL + "://" + server + "/" + nomBaseDebiteur;
			String urlCrediteur = protocoleURL + "://" + server + "/" + nomBaseCrediteur;

			// Programme :
			Compte compteDebiteur = new Compte(urlDebiteur, username, password, idClientDebiteur);
			Compte compteCrediteur = new Compte(urlCrediteur, username, password, idClientCrediteur);
			
			compteDebiteur.getConnection().setAutoCommit(false);
			compteCrediteur.getConnection().setAutoCommit(false);
			compteDebiteur.getConnection().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			compteCrediteur.getConnection().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			System.out.println("Avant opération :");
			System.out.println("compte débit : " + compteDebiteur.getIdClient() + ", solde : " + compteDebiteur.getSolde());
			System.out.println("compte crédit : " + compteCrediteur.getIdClient() + ", solde : " + compteCrediteur.getSolde());
			
			System.out.println("Virement de " + value);
			compteDebiteur.setSolde(compteDebiteur.getSolde() - Double.valueOf(value));
			compteCrediteur.setSolde(compteCrediteur.getSolde() + Double.valueOf(value));
			
			System.out.println("Après opération :");
			System.out.println("compte débit : " + compteDebiteur.getIdClient() + ", solde : " + compteDebiteur.getSolde());
			System.out.println("compte crédit : " + compteCrediteur.getIdClient() + ", solde : " + compteCrediteur.getSolde());
			
			if (compteDebiteur.getSolde() < 0) {
				compteDebiteur.getConnection().rollback();
				compteCrediteur.getConnection().rollback();
				
				System.out.println("Opération annulée car solde négatif pour le débiteur");
			} else {
				compteDebiteur.getConnection().commit();
				compteCrediteur.getConnection().commit();
			}
			
			compteDebiteur.close();
			compteCrediteur.close();
		} catch (ArrayIndexOutOfBoundsException e0) {
			System.err.println("erreur argument: java AccesCompte nomBaseDebiteur idClientDebiteur nomBaseCrediteur idClientCrediteur valeur");
		} catch (IOException e1) {
			System.err.println("erreur fichier database.properties");
		} catch (SQLException e3) {
			System.err.println("erreur SQL : " + e3.getMessage());
		} catch (CompteInconnuException e4) {
			System.err.println("compte client inconnu");
		}
	}
}
