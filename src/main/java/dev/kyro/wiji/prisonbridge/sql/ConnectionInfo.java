package dev.kyro.wiji.prisonbridge.sql;

import dev.kyro.wiji.prisonbridge.PrisonBridge;

import java.sql.Connection;
import java.sql.SQLException;

public enum ConnectionInfo {

	PRISON_BRIDGE(PrisonBridge.getConfiguration().getString("sql-url"),
			PrisonBridge.getConfiguration().getString("sql-user"),
			PrisonBridge.getConfiguration().getString("sql-pass"),
			1000L * 60 * 60 * 24 * 30)
	;

	 public final String URL;
	 public final String USERNAME;
	 public final String PASSWORD;
	 public final long MAX_TIME;


	 ConnectionInfo(String URL, String USERNAME, String PASSWORD, long MAX_TIME) {
		 this.URL = URL;
		 this.USERNAME = USERNAME;
		 this.PASSWORD = PASSWORD;
		 this.MAX_TIME = MAX_TIME;
	 }

	 public Connection getConnection() {
		 String URL = this.URL;
		 try { Class.forName("com.mysql.jdbc.Driver"); } catch(ClassNotFoundException e) { throw new RuntimeException(e); }
		 try { return java.sql.DriverManager.getConnection(URL, USERNAME, PASSWORD); } catch(SQLException e) { throw new RuntimeException(e); }
	 }
}
