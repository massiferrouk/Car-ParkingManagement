package main.test;

import java.util.List;

import modele.beans.Utilisateur;
import modele.daos.DAOUtilisateur;
import utils.DBConnector;

public class Test04_DAOUtilisateur_GET_LIST {
	public static void main(String[] args) {
		DBConnector.getSingleton("localhost", 3306, "parking_db", "root", "root");
		
		List<Utilisateur> list = DAOUtilisateur.getList();
		for (Utilisateur user : list) {
			System.out.println(user.getId()+"   "+user.getNom()+"  "+user.getPrenom());
		}
	}
}
