package modele.daos;

import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import modele.beans.OperationParking;
import utils.DBConnector;

public class DAOOperationParking {
	public static final String TABLE_NAME = "operation_parking";
	
	private static OperationParking getByRow(List<Object> row) { 
		OperationParking item = new OperationParking();
		
		item.setId( (Integer)row.get(0) );
		item.setIdVehicule( (Integer)row.get(1) );
		item.setIdPlaceParking( (Integer)row.get(2) );
		item.setDateHeureEntree( (Timestamp)row.get(3) );
		item.setDateHeureSortie( (Timestamp)row.get(4) );
		item.setIdAgent1( (Integer)row.get(5) );
		item.setIdAgent2( (Integer)row.get(6) );
		item.setObservation( (String)row.get(7) );
		
		return item;
	}
	
	public static OperationParking getById(int id) {
		OperationParking item = null;
		
		String sql = "SELECT * FROM `"+TABLE_NAME+"` WHERE `id` = ? LIMIT 1";
		List<List<Object>> data = DBConnector.getSingleton().selectQueryPS(sql, id);
 
		if (data.size() == 1) {
			item = getByRow( data.get(0) );
		}
		
		return item; 
	}
	
	public static synchronized  void add(OperationParking item) {
		String sql = "INSERT INTO `"+TABLE_NAME+"` ( `id`, `idVehicule`, `idPlaceParking`, `date_heure_entree`, `date_heure_sortie`, `id_agent_1`, `id_agent_2`, `observation` ) "
				+ "VALUES (?, ?, ?, ?,  ?, ?, ?, ?)";
		
		DBConnector.getSingleton().queryPS(sql, null, item.getIdVehicule(), item.getIdPlaceParking(), item.getDateHeureEntree(), item.getDateHeureSortie(), item.getIdAgent1(), item.getIdAgent2(), item.getObservation());
		
		int id = (Integer)DBConnector.getSingleton().getValue( "SELECT `id` FROM `"+TABLE_NAME+"` ORDER BY `id` DESC LIMIT 1" );
		
		item.setId( id );
	}
	
	public static void update(OperationParking item) {
		String sql = "UPDATE `"+TABLE_NAME+"` SET `idVehicule`=?, `idPlaceParking`=?, `date_heure_entree`=?, `date_heure_sortie`=?, `id_agent_1`=?, `id_agent_2`=?, `observation`=?  WHERE `id`=?";
		
		DBConnector.getSingleton().queryPS(sql, item.getIdVehicule(), item.getIdPlaceParking(), item.getDateHeureEntree(), item.getDateHeureSortie(), item.getIdAgent1(), item.getIdAgent2(), item.getObservation());
	}
	
	public static void delete(OperationParking item) {
		deleteById(item.getId());
	}
	
	public static void deleteById(int id) {
		String sql = "DELETE FROM `"+TABLE_NAME+"` WHERE `id` = ?";
		DBConnector.getSingleton().queryPS(sql, id);
	}
	
	public static List< OperationParking > getList(){
		return getList("1");
	}
	
	public static List< OperationParking > getList(String condition, Object ... values){
		String sql = " SELECT * FROM `"+TABLE_NAME+"` WHERE "+condition;
		
		List<List<Object>> data = DBConnector.getSingleton().selectQueryPS(sql, values);
		
		List<OperationParking> list = new Vector<OperationParking>();
		
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
