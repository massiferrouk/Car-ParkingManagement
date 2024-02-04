package main.test;

import modele.beans.Etage;
import modele.daos.DAOEtage;
import utils.DBConnector;

public class Test02_DAOEtage_UPDATE {
	public static void main(String[] args) {
		DBConnector.getSingleton("localhost", 3306, "parking_db", "root", "root");
		
		Etage user = DAOEtage.getById(1);
		
		user.setNumero(9);
		
		DAOEtage.update(user);
	}
}
