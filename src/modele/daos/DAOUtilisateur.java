package modele.daos;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import modele.beans.Utilisateur;
import utils.DBConnector;

public class DAOUtilisateur {
	public static final String TABLE_NAME = "utilisateur";
	
	public static Utilisateur getByRow(List<Object> row) { 
		Utilisateur item = new Utilisateur();
		
		item.setId( (Integer)row.get(0) );
		item.setNom( (String)row.get(1) );
		item.setPrenom( (String)row.get(2) );
		item.setLogin( (String)row.get(3) );
		item.setMotDePasse( (String)row.get(4) );
		item.setRole( Utilisateur.getRoleCode( (String)row.get(5) ) );
		
		return item;
	}
	
	public static Utilisateur getByMap(Map<String, Object> row) { 
		Utilisateur item = new Utilisateur();
		
		item.setId( (Integer)row.get("id") );
		item.setNom( (String)row.get("nom") );
		item.setPrenom( (String)row.get("prenom") );
		item.setLogin( (String)row.get("login") );
		item.setMotDePasse( (String)row.get("motDePasse") );
		item.setRole( Utilisateur.getRoleCode( (String)row.get("role") ) );
		
		return item;
	}
	
	public static Utilisateur getById(int id) {
		Utilisateur item = null;
		
		String sql = "SELECT * FROM `"+TABLE_NAME+"` WHERE `id` = ? LIMIT 1";
		List<List<Object>> data = DBConnector.getSingleton().selectQueryPS(sql, id);
 
		if (data.size() == 1) {
			item = getByRow( data.get(0) );
		}
		
		return item; 
	}
	
	public static synchronized  void add(Utilisateur item) {
		String sql = "INSERT INTO `"+TABLE_NAME+"` ( `id`, `nom`, `prenom`, `login`, `motDePasse`, `role` ) "
				+ "VALUES (?, ?, ?, ?,  ?, ?)";
		
		DBConnector.getSingleton().queryPS(sql, null, item.getNom(), item.getPrenom(), item.getLogin(), item.getMotDePasse(), item.getRolseAsString());
		
		int id = (Integer)DBConnector.getSingleton().getValue( "SELECT `id` FROM `"+TABLE_NAME+"` ORDER BY `id` DESC LIMIT 1" );
		
		item.setId( id );
	}
	
	public static void update(Utilisateur item) {
		String sql = "UPDATE `"+TABLE_NAME+"` SET `nom`=?, `prenom`=?, `login`=?, `motDePasse`=?, `role`=?  WHERE `id`=?";
		
		DBConnector.getSingleton().queryPS(sql, item.getNom(), item.getPrenom(), item.getLogin(), item.getMotDePasse(), item.getRolseAsString(), item.getId());
	}
	
	public static void delete(Utilisateur item) {
		deleteById(item.getId());
	}
	
	public static void deleteById(int id) {
		String sql = "DELETE FROM `"+TABLE_NAME+"` WHERE `id` = ?";
		DBConnector.getSingleton().queryPS(sql, id);
	}
	
	public static List< Utilisateur > getList(){
		return getList("1");
	}
	
	public static List< Utilisateur > getList(String condition, Object ... values){
		String sql = " SELECT * FROM `"+TABLE_NAME+"` WHERE "+condition;
		
		List<List<Object>> data = DBConnector.getSingleton().selectQueryPS(sql, values);
		
		List<Utilisateur> list = new Vector<Utilisateur>();
		
		for (List<Object> row : data) {
			list.add( getByRow(row) );
		}
		
		return list;
	}
	
	public static Utilisateur getUtilisateurByLogin(String value) {
		Utilisateur item = null;
		
		List<Utilisateur> list = getList("login like binary ? limit 1", value);
		if (list.size()==1) {
			item = list.get(0);
		}
		
		return item;
	}
	
	public static long getCountInTable() {
		return getCountInTable("1");
	}
	
	public static long getCountInTable(String whereCondition, Object ... values) {
		long nb = 0;
		
		Object value = DBConnector.getSingleton().getValue("SELECT COUNT(id) from `"+TABLE_NAME+"` WHERE "+whereCondition, values);
		if (value != null) {
			nb = (long)value;
		}
		
		return nb;
	}
	
	public static Utilisateur authentifier(String login, String motDePasse) {
		Utilisateur user = null;
		
		List<Utilisateur> list = getList("login like binary ? and motDePasse like binary ? LIMIT 1", login,   utils.Utils.encryptSHA1(motDePasse) );
		
		if (list.size() == 1) {
			user = list.get(0);
		}
		
		return user;
	}
}
