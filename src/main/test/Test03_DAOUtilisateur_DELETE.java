package main.test;

import modele.daos.DAOUtilisateur;
import utils.DBConnector;

public class Test03_DAOUtilisateur_DELETE {
	public static void main(String[] args) {
		DBConnector.getSingleton("localhost", 3306, "parking_db", "root", "root");
		
		DAOUtilisateur.deleteById(3);
	}
}
