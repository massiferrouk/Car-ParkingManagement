package modele.daos;

import java.util.List;
import java.util.Vector;

import modele.beans.PlaceParking;
import utils.DBConnector;

public class DAOPlaceParking {
	public static final String TABLE_NAME = "place_parking";
	
	private static PlaceParking getByRow(List<Object> row) { 
		PlaceParking item = new PlaceParking();
		
		int i=0;
		item.setId( (Integer)row.get(i++) );
		item.setNumero( (String)row.get(i++) );
		item.setType( PlaceParking.getTypeCode( (String)row.get(i++) ) );
		item.setOccupe( (Boolean)row.get(i++) );
		item.setIdEtage( (Integer)row.get(i++) );
		
		return item;
	}
	
	public static PlaceParking getById(int id) {
		PlaceParking item = null;
		
		String sql = "SELECT * FROM `"+TABLE_NAME+"` WHERE `id` = ? LIMIT 1";
		List<List<Object>> data = DBConnector.getSingleton().selectQueryPS(sql, id);
 
		if (data.size() == 1) {
			item = getByRow( data.get(0) );
		}
		
		return item; 
	}
	
	public static synchronized  void add(PlaceParking item) {
		String sql = "INSERT INTO `"+TABLE_NAME+"` ( `id`, `numero`, `type`, `occupe`, `idEtage` ) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		DBConnector.getSingleton().queryPS(sql, null, item.getNumero(), item.getTypeAsString(), item.isOccupe(), item.getIdEtage());
		
		int id = (Integer)DBConnector.getSingleton().getValue( "SELECT `id` FROM `"+TABLE_NAME+"` ORDER BY `id` DESC LIMIT 1" );
		
		item.setId( id );
	}
	
	public static void update(PlaceParking item) {
		String sql = "UPDATE `"+TABLE_NAME+"` SET `numero`=?, `type`=?, `occupe`=?, `idEtage`=?  WHERE `id`=?";
		
		DBConnector.getSingleton().queryPS(sql, item.getNumero(), item.getTypeAsString(), item.isOccupe(), item.getIdEtage());
	}
	
	public static void delete(PlaceParking item) {
		deleteById(item.getId());
	}
	
	public static void deleteById(int id) {
		String sql = "DELETE FROM `"+TABLE_NAME+"` WHERE `id` = ?";
		DBConnector.getSingleton().queryPS(sql, id);
	}
	
	public static List< PlaceParking > getList(){
		return getList("1");
	}
	
	public static List< PlaceParking > getList(String condition, Object ... values){
		String sql = " SELECT * FROM `"+TABLE_NAME+"` WHERE "+condition;
		
		List<List<Object>> data = DBConnector.getSingleton().selectQueryPS(sql, values);
		
		List<PlaceParking> list = new Vector<PlaceParking>();
		
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
