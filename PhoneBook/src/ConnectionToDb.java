import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionToDb {
	
	private Connection connection = null;

	public Connection connect() {
		try {
			//C:\Program Files\Java\jre1.8.0_181\lib\ext
			//C:\Program Files\Java\jdk1.8.0_181\jre\lib\ext
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Could NOT connect to database!");
		}
		return connection;
	}

	public void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, "Could NOT close database");
		}
	}
	
}
