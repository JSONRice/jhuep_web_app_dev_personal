package hw10.main;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @class DerbyHandle
 * @desc Main Derby handler instance. This instance by default generates a 'retail' database
 * and provides helper methods for CRUD transaction operations.
 * @author jsnrice
 *
 */
public class DerbyHandle {
	
	private String dburl;
	private boolean isConnected;
	private Connection conn;
	
	/**
	 * Constructor calls overloaded constructor to generate a "retail" Derby in-memory database.
	 */
	public DerbyHandle(){
		this("jdbc:derby:retail;create=true");
		String status = (isConnected == true) ? 
				"Constructor allocated memory for 'retail' Derby database." 
				: "Constructor failed to establish database. Check stack trace.";						
		Logger.getLogger(DerbyHandle.class.getName()).log(Level.INFO, "INFO: " + status);
	}


	/**
	 * Constructor starts up a default database named "retail"
	 */
	public DerbyHandle(String dburl){
	   isConnected = false; // are we connected to the db?
	   
       try {
    	   Class.forName("org.apache.derby.jdbc.EmbeddedDriver"); 
           // start embedded driver
           this.dburl = dburl;
           conn = DriverManager.getConnection(this.dburl);
           if (conn != null) {
       	      Logger.getLogger(DerbyHandle.class.getName()).log(
       	    		  Level.INFO,"INFO: Constructor instantiated embedded Derby database: " + dburl);
       	      isConnected = true;
           }
           else{
      	      Logger.getLogger(DerbyHandle.class.getName()).log(
      	    		  Level.SEVERE,"ERROR: Constructor failed to instantiate embedded Derby database: " + dburl);
           }
       } catch (SQLException ex) {
    	   isConnected = false;
   	       Logger.getLogger(DerbyHandle.class.getName()).log(
   	    		   Level.SEVERE,"ERROR: Constructor caught exception while attempting to instantiate Derby database: " + dburl);
   	       ex.printStackTrace();
       }
       catch (ClassNotFoundException cnfe){
    	   cnfe.printStackTrace();
       }
	}
	
	/**
	 * @desc Closes the database. Reports status on log and returns status.
	 * @return status of transaction
	 */
	public boolean close(){
		try {
			conn.close();
		} catch (SQLException e) {
	   	    Logger.getLogger(DerbyHandle.class.getName()).log(
	   	    		Level.SEVERE,"ERROR: Database failed to close properly.");
	   	    return false;
		}
   	    Logger.getLogger(DerbyHandle.class.getName()).log(
   	    		Level.INFO,"INFO: Database closed properly.");
		return true;
	}
	
	public boolean dropTable(String name){
		try {
			Statement statement = conn.createStatement();
			String executeStr = "DROP TABLE " + name;
	   	    Logger.getLogger(DerbyHandle.class.getName()).log(
	   	    		Level.INFO,"Dropping table: " + name);
			statement.executeUpdate(executeStr);
            conn.commit();
		} catch (SQLException e) {
			
	        if(DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
	        	Logger.getLogger(DerbyHandle.class.getName()).log(
	        			Level.INFO,("Table " + name + " already exists.  No need to recreate"));
	        } else {
	        	Logger.getLogger(DerbyHandle.class.getName()).log(
	        			Level.SEVERE,e.getMessage() + " : " + e.getStackTrace());
	        }
	        
	   	    Logger.getLogger(DerbyHandle.class.getName()).log(
	   	    		Level.INFO,"ERROR: Database transaction failure:");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @desc Given a table name generate that table and use the 'lines' to create
	 * the table. A typically example would be as follows:
	 * 
	 * CREATE TABLE FIRSTTABLE(
	 *    ID INT PRIMARY KEY,
	 *    NAME VARCHAR(12)
	 * );
	 * 
	 * In the case above a call to this method would be as follows:
	 * ArrayList<String> lines = new ArrayList<String>();
	 * lines.add("ID INT PRIMARY KEY");
	 * lines.add("NAME VARCHAR(12)");
	 * 
	 * createTable("FIRSTTABLE", lines);
	 * 
	 * @param name
	 * @param createLines
	 * @return true if the transaction completes and is committed successfully, else false
	 */
	public boolean createTable(String name, ArrayList<String> createLines){
		try {
			Statement statement = conn.createStatement();
			String executeStr = "CREATE TABLE " + name + " (";
			for (String str : createLines){
				executeStr += str + ",";
			}
			// remove the last ','
		    if (executeStr.length() > 0 && executeStr.charAt(executeStr.length()-1) == ',') {
		        executeStr = executeStr.substring(0, executeStr.length() - 1);
		    }
		    // add on the ")" to close the statement
		    executeStr += ")";
	   	    Logger.getLogger(DerbyHandle.class.getName()).log(Level.INFO,"Creating table: " + executeStr);
			statement.executeUpdate(executeStr);
            conn.commit();
		} catch (SQLException e) {
	   	    Logger.getLogger(DerbyHandle.class.getName()).log(Level.INFO,"ERROR: Database transaction failure:");
			e.printStackTrace();
			return false;
		}
   	    Logger.getLogger(DerbyHandle.class.getName()).log(Level.INFO,"INFO: Database transaction completed.");		
		return true;
	}
	
	/**
	 * @desc In our application we know that all tuples will be selected.
	 * @param name
	 * @return List of tuple objects. If no tuples were found or there was a problem return null.
	 */
	public ResultSet getTuples(String name){
		try {
			Statement statement = conn.createStatement();
			String executeStr = "SELECT * FROM  " + name;
	   	    Logger.getLogger(DerbyHandle.class.getName()).log(Level.INFO,"Getting tuples: " + executeStr);
			return statement.executeQuery(executeStr);
		} catch (SQLException e) {
	   	    Logger.getLogger(DerbyHandle.class.getName()).log(Level.INFO,"ERROR: Database transaction failure:");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @desc insert tuple into MERCHANDISE table given values.
	 * @param name
	 * @param price
	 * @param description
	 * @return true if the transaction completes and is committed successfully, else false
	 */
	public boolean insertMerchandiseTuple(String name, Double price, String description){
		try{
			Statement statement = conn.createStatement();
			String executeStr = "INSERT INTO MERCHANDISE VALUES ('" + name 
					             + "'," + price + ",'" + description + "')";
	   	    Logger.getLogger(Retail.class.getName()).log(Level.INFO,"Inserting tuples into MERCHANDISE: " + executeStr);
			statement.executeUpdate(executeStr);
	        conn.commit();			
		} catch (SQLException e) {
	   	    Logger.getLogger(Retail.class.getName()).log(Level.INFO,"ERROR: Database transaction failure:");
			e.printStackTrace();
			return false;
		}
   	    Logger.getLogger(Retail.class.getName()).log(Level.INFO,"INFO: Database transaction completed.");		
		return true;
	}
	
	/**
	 * @desc insert tuples into person based table given a table name and values. Can either
	 * be a EMPLOYEE or CUSTOMER.
	 * @param name
	 * @param values list of values to insert into a given record tuple.
	 * @return true if the transaction completes and is committed successfully, else false
	 */
	public boolean insertPersonTuples(String name, ArrayList<String> values){
		try {
			Statement statement = conn.createStatement();
			String executeStr = "INSERT INTO " + name + " VALUES (";
			for (String value : values){
				if (!value.isEmpty()) { // ignore empty values (strings)
				   executeStr += "'" + value + "',";
				}
			}
			// remove the last ','
		    if (executeStr.length() > 0 && executeStr.charAt(executeStr.length()-1) == ',') {
		        executeStr = executeStr.substring(0, executeStr.length() - 1);
		    }
		    // add on the ")" to close the statement
		    executeStr += ")";
	   	    Logger.getLogger(Retail.class.getName()).log(Level.INFO,"Inserting tuples into " + name + ":" + executeStr);
			statement.executeUpdate(executeStr);
	        conn.commit();
		} catch (SQLException e) {
	   	    Logger.getLogger(Retail.class.getName()).log(Level.INFO,"ERROR: Database transaction failure:");
			e.printStackTrace();
			return false;
		}
   	    Logger.getLogger(Retail.class.getName()).log(Level.INFO,"INFO: Database transaction completed.");		
		return true;
	}
}
