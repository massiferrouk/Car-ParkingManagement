package main.test;

import modele.daos.DAOEtage;
import utils.DBConnector;

public class Test03_DAOEtage_DELETE {
	public static void main(String[] args) {
		DBConnector.getSingleton("localhost", 3306, "parking_db", "root", "root");
		
		DAOEtage.deleteById(2);
	}
}
