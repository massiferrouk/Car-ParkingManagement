package main.test;

import java.util.List;

import modele.beans.Etage;
import modele.daos.DAOEtage;
import utils.DBConnector;

public class Test04_DAOEtage_GET_LIST {
	public static void main(String[] args) {
		DBConnector.getSingleton("localhost", 3306, "parking_db", "root", "root");
		
		List<Etage> list = DAOEtage.getList();
		for (Etage user : list) {
			System.out.println(user.getId()+"   "+user.getNumero()+"  "+user.getDescription());
		}
	}
}
