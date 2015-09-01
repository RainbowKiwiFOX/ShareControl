package com.net.h1karo.sharecontrol.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;

public class MySQL {
	
	private static ShareControl main;
	public static Connection connection = null;
	public static ResultSet resultSet = null;
	
	static ConsoleCommandSender console = Bukkit.getConsoleSender();

	public MySQL(ShareControl h)
	{
		MySQL.main = h;
	}
	
	   public static void connect() throws SQLException {
	      try {
	         if(!main.getDataFolder().mkdirs()) {
	            main.getDataFolder().mkdirs();
	         }

	         if(Configuration.Database.equalsIgnoreCase("sqlite")) {
	            Class.forName("org.sqlite.JDBC").newInstance();
	            connection = DriverManager.getConnection("jdbc:sqlite://" + main.getDataFolder().getAbsolutePath() + "/data/blocks.db");
	            executeSync("CREATE TABLE IF NOT EXISTS `blocks` (`x` INTEGER NOT NULL,`y` INTEGER NOT NULL,`z` INTEGER NOT NULL,`id` INTEGER NOT NULL)");
	         } else {
	        	 Class.forName("com.mysql.jdbc.Driver").newInstance();
	        	 String url = "jdbc:mysql://" + Configuration.Host + ":" + Configuration.Port + "/" + Configuration.DBname;
	        	 
	        	 connection = DriverManager.getConnection(url, Configuration.Username, Configuration.Password);
	        	 executeSync("CREATE TABLE IF NOT EXISTS `blocks` (`x` int(11) NOT NULL,`y` int(11) NOT NULL,`z` int(11) NOT NULL,`id` int(11) NOT NULL)");
	         }
	        
	        console.sendMessage(" Connected to DB.");
	         
	      } catch (Exception var2) {
	    	  console.sendMessage(" An error occured while connecting to DB.");
	         var2.printStackTrace();
	      }

	   }

	   public static boolean hasConnected() {
	      try {
	         return !connection.isClosed();
	      } catch (Exception var1) {
	         return false;
	      }
	   }
	   
	   public static String strip(String str) {
	      str = str.replaceAll("<[^>]*>", "");
	      str = str.replace("\\", "\\\\");
	      str = str.trim();
	      return str;
	   }

	   public static void executeSync(String query) {
	      if(!hasConnected()) {
	         try {
				connect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	      }

	      try {
	    	  connection.createStatement().execute(strip(query));
	      } catch (Exception var2) {
	    	  console.sendMessage(" An error occured. Query can't be executed or DB is inavailable");
	    	  console.sendMessage(" " +query);
	    	  console.sendMessage(" " +var2.getMessage());
	      }

	   }

	   public static ResultSet executeQuery(String query) {
	      if(!hasConnected()) {
	         try {
				connect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	      }

	      ResultSet rs = null;

	      try {
	         rs = connection.createStatement().executeQuery(strip(query));
	      } catch (Exception var3) {
	         var3.printStackTrace();
	      }

	      return rs;
	   }
	   
	   
	   public static ResultSet query(String query) {
			Statement statement = null;
			ResultSet result = null;
			
			try {
				statement = connection.createStatement();
				
				switch(getStatement(query)) {
				case ALTER:
					result = statement.executeQuery(query);
					break;
				default:
					statement.executeUpdate(query);
				}
				
				return result;
			} catch (SQLException e) {
				console.sendMessage("Error in SQL query: " + e.getMessage());
			}
			
			return result;
		}
	   
	   public static void disconnect() {
	      try {
	         if(connection != null) {
	            connection.close();
	            
	            console.sendMessage(" Disconnected from DB.");
	         }
	      } catch (Exception var1) {
	         var1.printStackTrace();
	      }

	   }
	   
	   protected static enum Statements {
			SELECT, INSERT, UPDATE, DELETE, DO, REPLACE, LOAD, HANDLER, CALL, CREATE, ALTER, DROP, TRUNCATE, RENAME;
		}
	   
	   protected static Statements getStatement(String query) {
			String trimmedQuery = query.trim();
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("SELECT")) {
				return Statements.SELECT;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("INSERT")) {
				return Statements.INSERT;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("UPDATE")) {
				return Statements.UPDATE;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("DELETE")) {
				return Statements.DELETE;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("CREATE")) {
				return Statements.CREATE;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("ALTER")) {
				return Statements.ALTER;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("DROP")) {
				return Statements.DROP;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("TRUNCATE")) {
				return Statements.TRUNCATE;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("RENA,E")) {
				return Statements.RENAME;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("DO")) {
				return Statements.DO;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("REPLACE")) {
				return Statements.REPLACE;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("LOAD")) {
				return Statements.LOAD;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("HANDLER")) {
				return Statements.HANDLER;
			}
			
			if (trimmedQuery.substring(0, 6).equalsIgnoreCase("CALL")) {
				return Statements.CALL;
			}
			
			return Statements.SELECT;
		}
	   
	   
	   
	   public static void SQLUpdate(Integer x, Integer y, Integer z, Integer id) {
		   resultSet = executeQuery("SELECT * FROM blocks WHERE x='" + x + "' AND y='" + y + "' AND z='" + z + "'");
		   try {
			   boolean SQLexist = false;
			   
			   while(resultSet.next()) {
				   SQLexist = true;
			   }
			   
			   if(SQLexist) {
				   if(id != null)
					   query("UPDATE blocks SET id='" + id + "' WHERE x='" + x + "' AND y='" + y + "' AND z='" + z + "'");
				   else 
					   query("DELETE FROM blocks WHERE x='" + x + "' AND y='" + y + "' AND z='" + z + "'");
			   }
			   else if(id != null)
				   query("INSERT INTO `blocks`(`x`, `y`, `z`, `id`) VALUES ('"+ x +"', '"+ y +"', '"+ z +"', '"+ id +"')");
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }
	   }
	   
	   public static int getID(Integer x, Integer y, Integer z) {
		   resultSet = executeQuery("SELECT * FROM blocks WHERE x='" + x + "' AND y='" + y + "' AND z='" + z + "'");
		   try {
			   if(resultSet.next())
				   return resultSet.getInt("id");
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }
		   
		   return 0;
	   }
}
