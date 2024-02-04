package main.test;

import modele.beans.Utilisateur;
import modele.daos.DAOUtilisateur;
import utils.DBConnector;

public class Test01_DAOUtilisateur_ADD {
	public static void main(String[] args) {
		DBConnector.getSingleton("localhost", 3306, "parking_db", "root", "root");
		
		Utilisateur user = new Utilisateur();
		user.setNom("FERROUK");
		user.setPrenom("Massi");
		user.setLogin("massi2");
		user.setMotDePasse("massi");
		user.setRole( Utilisateur.ROLE_ADMINISTRATEUR );
		
		DAOUtilisateur.add(user);
		
		System.out.println( user.getId() );
	}
}
