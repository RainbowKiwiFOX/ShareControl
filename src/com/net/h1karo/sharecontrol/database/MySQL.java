/*******************************************************************************
 * Copyright (C) 2016 H1KaRo (h1karo)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.net.h1karo.sharecontrol.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;

public class MySQL {

	private static ShareControl main;
	public static Connection connection = null;

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
				executeSync("CREATE TABLE IF NOT EXISTS `" + Configuration.TableName + "` (`id` INTEGER PRIMARY KEY, `x` INTEGER NOT NULL,`y` INTEGER NOT NULL,`z` INTEGER NOT NULL,`material` STRING NOT NULL, `world` INTEGER NOT NULL)");
				console.sendMessage(" Connected to SQLite.");
			}
			if(Configuration.Database.equalsIgnoreCase("mysql")) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				String url = "jdbc:mysql://" + Configuration.Host + ":" + Configuration.Port + "/" + Configuration.DBname;

				connection = DriverManager.getConnection(url, Configuration.Username, Configuration.Password);
				executeSync("CREATE TABLE IF NOT EXISTS `" + Configuration.TableName + "` (`id` int(11) NOT NULL AUTO_INCREMENT, `x` int(11) NOT NULL,`y` int(11) NOT NULL,`z` int(11) NOT NULL, `material` text NOT NULL, `world` INTEGER NOT NULL, PRIMARY KEY (`id`)) DEFAULT CHARSET=utf8 AUTO_INCREMENT=0");
				console.sendMessage(" Connected to MySQL.");
			}
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


	public static ResultSet query(String query) {
		ResultSet result = null;

		try {
			switch(getStatement(query)) {
				case ALTER:
					result = connection.createStatement().executeQuery(query);
					break;
				case SELECT:
					result = connection.createStatement().executeQuery(query);
					break;
				default:
					connection.createStatement().executeUpdate(query);
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

		if (trimmedQuery.substring(0, 6).equalsIgnoreCase("RENAME")) {
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



	public static void SQLUpdate(Integer x, Integer y, Integer z, String material, Integer world) {
		if(!hasConnected()) {
			try {
				MySQL.connect();
				MySQL.loadCache();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		ResultSet resultSet = query("SELECT * FROM `" + Configuration.TableName + "` WHERE `x`='" + x + "' AND `y`='" + y + "' AND `z`='" + z + "' AND `world`='" + world + "'");

		try {
			boolean SQLexist = false;
			String Gmaterial = null;

			while(resultSet.next()) {
				SQLexist = true;
				Gmaterial = resultSet.getString("material");
			}

			if(SQLexist) {
				if(material.equals("AIR") && !Gmaterial.equals(material))
					query("UPDATE `" + Configuration.TableName + "` SET `material`='" + material + "' WHERE `x`='" + x + "' AND `y`='" + y + "' AND `z`='" + z + "' AND `world`='" + world + "'");
				else if(material.equals("AIR"))
					query("DELETE FROM `" + Configuration.TableName + "` WHERE `x`='" + x + "' AND `y`='" + y + "' AND `z`='" + z + "' AND `world`='" + world + "'");
			}
			else if(!material.equals("AIR"))
				query("INSERT INTO `" + Configuration.TableName + "`(`x`, `y`, `z`, `material`, `world`) VALUES ('"+ x +"', '"+ y +"', '"+ z +"', '"+ material +"', '" + world + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String getID(Integer x, Integer y, Integer z, String world) {
		if(!hasConnected()) {
			try {
				MySQL.connect();
				MySQL.loadCache();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		ResultSet resultSet = query("SELECT * FROM `" + Configuration.TableName + "` WHERE `x`='" + x + "' AND `y`='" + y + "' AND `z`='" + z + "' AND `world`='" + world + "'");
		try {
			while(resultSet.next())
				return resultSet.getString("material");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void loadCache() {
		if(!hasConnected()) {
			try {
				MySQL.connect();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		ResultSet resultSet = query("SELECT * FROM `" + Configuration.TableName + "`");
		try {
			while(resultSet.next()) {
				int x = resultSet.getInt("x"),
						y = resultSet.getInt("y"),
						z = resultSet.getInt("z"),
						w = resultSet.getInt("world");
				String material = resultSet.getString("material");

				List<Integer> key = new ArrayList<>();
				key.add(x); key.add(y); key.add(z); key.add(w);
				Database.fullcache.put(key, material);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
