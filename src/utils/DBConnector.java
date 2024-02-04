package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

public class DBConnector {
	private static boolean debug = false;
	
//	Attributs d'objets : pour chaque objet ...
	private String host = "";
	private int port = 0;
	private String dbName = "";
	private String dbUser = "";
	private String dbPwd = "";
	
	private Connection connection = null;
	
//	Attributs commun a tous les objets : attribut de class : static
	private static DBConnector singleton = null;
	
	public static DBConnector getSingleton(String host, int port, String dbName, String dbUser, String dbPwd) {
		if (singleton == null) {
			singleton = new DBConnector(host, port, dbName, dbUser, dbPwd);
		}
		else {
			singleton.setParameters(host, port, dbName, dbUser, dbPwd);
		}
		
		return singleton;
	}
	
	public static DBConnector getSingleton() {
		if (singleton == null) {
			singleton = new DBConnector();
		}
		
		return singleton;
	}

	private DBConnector(){
	}

	
	private DBConnector(String host, int port, String dbName, String dbUser, String dbPwd) {
		setParameters(host, port, dbName, dbUser, dbPwd);
	}

	private void setParameters(String host, int port, String dbName, String dbUser, String dbPwd) {
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.dbUser = dbUser;
		this.dbPwd = dbPwd;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getDbName() {
		return dbName;
	}
	
	public String getDbUser() {
		return dbUser;
	}
	
	public String getDbPwd() {
		return dbPwd;
	}
	
	/**
	 * @param debug the debug to set
	 */
	public static void setDebug(boolean debug) {
		DBConnector.debug = debug;
	}
	
	public static void printDebug(Object message){
		if (debug) {
			System.out.println(message);
		}
	}
	
//	Le code de JDBC ...
	public Connection getConnection() {
		if (connection != null) {
			return connection;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection= DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbName,dbUser, dbPwd);
		} catch (Exception e) {
			printDebug(e);
		}
		
		return connection;
	}
	
	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			printDebug(e);
		}
	}
	
//	*******************************************************************
//	Les requetes SQL Simple (les param�tres sont dans la requ�te SQL
//	Risque de SQL Injection Attack (atack par injection sql) ...
//	*******************************************************************
	
	public boolean query(String sql) {
		boolean success = true;
		
		try {
			Statement statement = getConnection().createStatement();
			statement.execute(sql);
		} catch (Exception e) {
			success = false;
			printDebug(e);
		}
		
		return success;
	}
	
	public List<List<Object>> selectQuery(String sql) {
		printDebug(sql);
		
		List<List<Object>> data = new Vector<>();
		
		try {
			Statement statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			int nbColumns = DBConnectorUtil.getColumnCount(rs);
			while (rs.next()) {
				List<Object> row = new Vector<Object>();
				
				for (int i=1; i<= nbColumns; i++) {
					row.add( rs.getObject(i) );
				}
				
				data.add(row);
			}
			
		} catch (Exception e) {
			printDebug(e);
		}
		
		return data;
	}
	
	

//	*******************************************************************
//	Les requetes SQL preparees (Prepared Statement) : Les parametres sont separes de la requete...
//	Pas de risque de SQL Injection Attack (injection sql)
//	*******************************************************************
	
	public boolean queryPS(String sql, Object ... sqlParameters) {
		printDebug(sql);
		
		boolean success = true;
		
		try {
			PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
			
			DBConnectorUtil.injectSQLParameters( preparedStatement, sqlParameters );
			
			preparedStatement.execute();
		} catch (Exception e) {
			success = false;
			printDebug(e);
		}
		
		return success;
	}
	
	public List<List<Object>> selectQueryPS(String sql, Object ... sqlParameters) {
		printDebug(sql);
		
		List<List<Object>> data = new Vector<>();
		
		try {
			PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
			DBConnectorUtil.injectSQLParameters( preparedStatement, sqlParameters );
			
			ResultSet rs = preparedStatement.executeQuery();
			
			int nbColumns = DBConnectorUtil.getColumnCount(rs);
			while (rs.next()) {
				List<Object> row = new Vector<Object>();
				
				for (int i=1; i<= nbColumns; i++) {
					row.add( rs.getObject(i) );
				}
				
				data.add(row);
			}
			
		} catch (Exception e) {
			printDebug(e);
		}
		
		return data;
	}
	
	public Object getValue(String sql, Object ...sqlParameters) {
		Object value = null;
		
		List<List<Object>> data = this.selectQueryPS(sql, sqlParameters);
		if (data.size() == 1) {
			value = data.get(0).get(0);
		}
		
		return value;
	}
	
//	*******************************************************************
	//Classe utilitaires (helper) pour DBConnector
//	*******************************************************************
	private static class DBConnectorUtil{
		private static int getColumnCount(ResultSet rs) {
			int nbColumns = 0;
			
			try {
				ResultSetMetaData rsmd = rs.getMetaData();
				nbColumns = rsmd.getColumnCount();
			} catch (Exception e) {
				printDebug(e);
			}
			
			return nbColumns;
		}
		
		private static void injectSQLParameters(PreparedStatement preparedStatement, Object ... sqlParameters ) {
			try {
				if (sqlParameters != null) {
					for (int i=0; i<sqlParameters.length; i++) {
						preparedStatement.setObject(i+1, sqlParameters[i] );
					}
				}
			} catch (Exception e) {
				printDebug(e);
			}
		}
	}
}
