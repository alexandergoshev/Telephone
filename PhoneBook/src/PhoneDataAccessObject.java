import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class PhoneDataAccessObject {

	private List<Phone> phoneList = new ArrayList<>();

	public void getData() {
		
		ConnectionToDb connection = new ConnectionToDb();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			String query = "SELECT * FROM phonebook";
			ps = connection.connect().prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				phoneList.add(new Phone(rs.getString("brand"), rs.getString("model"),
						Integer.parseInt(rs.getString("year")), Double.parseDouble(rs.getString("price"))));
			}

			connection.disconnect();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Could NOT read from database!");
		}
	}

	public void addData(Phone phone) {
		ConnectionToDb connection = new ConnectionToDb();
		PreparedStatement ps = null;

		try {

			String query = " INSERT INTO phonebook (brand, model, year, price)" + " values (?,?,?,?)";

			ps = connection.connect().prepareStatement(query);
			
			ps.setString(1, phone.getBrand());
			ps.setString(2, phone.getModel());
			ps.setInt(3, phone.getYear());
			ps.setDouble(4, phone.getPrice());

			ps.execute();
			
			connection.disconnect();

		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Could NOT add to database!");
		
		}
	}

	public List<Phone> getList() {
		if (phoneList.isEmpty() == true)
			return null;
		else
			return phoneList;
	}
}
