package main.test;

import modele.beans.Etage;
import modele.daos.DAOEtage;
import utils.DBConnector;

public class Test01_DAOEtage_ADD {
	public static void main(String[] args) {
		DBConnector.getSingleton("localhost", 3306, "parking_db", "root", "root");
		
		Etage user = new Etage();
		user.setDescription("Leger");
		user.setNumero(3);
		
		DAOEtage.add(user);
		
		System.out.println( user.getId() );
	}
}
