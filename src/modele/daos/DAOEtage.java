package modele.daos;

import java.util.List;
import java.util.Vector;

import modele.beans.Etage;
import utils.DBConnector;

public class DAOEtage {
	public static final String TABLE_NAME = "etage";
	
	private static Etage getByRow(List<Object> row) { 
		Etage item = new Etage();
		
		item.setId( (Integer)row.get(0) );
		item.setNumero( (Integer)row.get(1) );
		item.setDescription( (String)row.get(2) );
		return item;
	}
	
	public static Etage getById(int id) {
		Etage item = null;
		
		String sql = "SELECT * FROM `"+TABLE_NAME+"` WHERE `id` = ? LIMIT 1";
		List<List<Object>> data = DBConnector.getSingleton().selectQueryPS(sql, id);
 
		if (data.size() == 1) {
			item = getByRow( data.get(0) );
		}
		
		return item; 
	}
	
	public static synchronized  void add(Etage item) {
		String sql = "INSERT INTO `"+TABLE_NAME+"` ( `id`, `description`, `numero` ) "
				+ "VALUES (?, ?, ?)";
		
		DBConnector.getSingleton().queryPS(sql, null, item.getDescription(), item.getNumero() );
		
		int id = (Integer)DBConnector.getSingleton().getValue( "SELECT `id` FROM `"+TABLE_NAME+"` ORDER BY `id` DESC LIMIT 1" );
		
		item.setId( id );
	}
	
	public static void update(Etage item) {
		String sql = "UPDATE `"+TABLE_NAME+"` SET `description`=?, `numero`=?  WHERE `id`=?";
		
		DBConnector.getSingleton().queryPS(sql, item.getDescription(), item.getNumero(), item.getId());
	}
	
	public static void delete(Etage item) {
		deleteById(item.getId());
	}
	
	public static void deleteById(int id) {
		String sql = "DELETE FROM `"+TABLE_NAME+"` WHERE `id` = ?";
		DBConnector.getSingleton().queryPS(sql, id);
	}
	
	public static List< Etage > getList(){
		return getList("1");
	}
	
	public static List< Etage > getList(String condition, Object ... values){
		String sql = " SELECT * FROM `"+TABLE_NAME+"` WHERE "+condition;
		
		List<List<Object>> data = DBConnector.getSingleton().selectQueryPS(sql, values);
		
		List<Etage> list = new Vector<Etage>();
		
		for (List<Object> row : data) {
			list.add( getByRow(row) );
		}
		
		return list;
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
}
