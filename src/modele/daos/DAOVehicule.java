package modele.daos;

import java.util.List;
import java.util.Vector;

import modele.beans.Vehicule;
import utils.DBConnector;

public class DAOVehicule {
	public static final String TABLE_NAME = "vehicule";
	
	private static Vehicule getByRow(List<Object> row) { 
		Vehicule item = new Vehicule();
		
		item.setId( (Integer)row.get(0) );
		item.setMatricule( (String)row.get(1) );
		item.setType(  Vehicule.getTypeCode((String)row.get(2)) );
		
		
		return item;
	}
	
	public static Vehicule getById(int id) {
		Vehicule item = null;
		
		String sql = "SELECT * FROM `"+TABLE_NAME+"` WHERE `id` = ? LIMIT 1";
		List<List<Object>> data = DBConnector.getSingleton().selectQueryPS(sql, id);
 
		if (data.size() == 1) {
			item = getByRow( data.get(0) );
		}
		
		return item; 
	}
	
	public static synchronized  void add(Vehicule item) {
		String sql = "INSERT INTO `"+TABLE_NAME+"` ( `id`, `matricule`, `type` ) "
				+ "VALUES (?, ?, ?)";
		
		DBConnector.getSingleton().queryPS(sql, null, item.getMatricule(), item.getTypeAsString());
		
		int id = (Integer)DBConnector.getSingleton().getValue( "SELECT `id` FROM `"+TABLE_NAME+"` ORDER BY `id` DESC LIMIT 1" );
		
		item.setId( id );
	}
	
	public static void update(Vehicule item) {
		String sql = "UPDATE `"+TABLE_NAME+"` SET `matricule`=?, `type`=?  WHERE `id`=?";
		
		DBConnector.getSingleton().queryPS(sql, item.getMatricule(), item.getTypeAsString());
	}
	
	public static void delete(Vehicule item) {
		deleteById(item.getId());
	}
	
	public static void deleteById(int id) {
		String sql = "DELETE FROM `"+TABLE_NAME+"` WHERE `id` = ?";
		DBConnector.getSingleton().queryPS(sql, id);
	}
	
	public static List< Vehicule > getList(){
		return getList("1");
	}
	
	public static List< Vehicule > getList(String condition, Object ... values){
		String sql = " SELECT * FROM `"+TABLE_NAME+"` WHERE "+condition;
		
		List<List<Object>> data = DBConnector.getSingleton().selectQueryPS(sql, values);
		
		List<Vehicule> list = new Vector<Vehicule>();
		
		for (List<Object> row : data) {
			list.add( getByRow(row) );
		}
		
		return list;
	}
	
	public static Vehicule getVehiculeByMatricule(String value) {
		Vehicule item = null;
		
		List<Vehicule> list = getList("matricule like binary ? limit 1", value);
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
}
