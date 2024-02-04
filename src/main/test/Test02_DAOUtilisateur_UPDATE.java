package main.test;

import modele.beans.Utilisateur;
import modele.daos.DAOUtilisateur;
import utils.DBConnector;

public class Test02_DAOUtilisateur_UPDATE {
	public static void main(String[] args) {
		DBConnector.getSingleton("localhost", 3306, "parking_db", "root", "root");
		
		Utilisateur user = DAOUtilisateur.getById(1);
		
		user.setLogin("omar");
		
		DAOUtilisateur.update(user);
	}
}
