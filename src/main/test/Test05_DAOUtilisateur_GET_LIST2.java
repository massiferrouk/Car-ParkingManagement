package main.test;

import java.util.List;

import modele.beans.Utilisateur;
import modele.daos.DAOUtilisateur;
import utils.DBConnector;

public class Test05_DAOUtilisateur_GET_LIST2 {
	public static void main(String[] args) {
		DBConnector.setDebug(true);
		
		DBConnector.getSingleton("localhost", 3306, "parking_db", "Redouane", "Redouane");
		
		String condition = "login like '%red%'";
		
		System.out.println( DAOUtilisateur.getCountInTable(condition) );
		
		List<Utilisateur> list = DAOUtilisateur.getList(condition);
		for (Utilisateur user : list) {
			System.out.println(user.getLogin()+"    "+user.getId()+"   "+user.getNom()+"  "+user.getPrenom());
		}
	}
}
