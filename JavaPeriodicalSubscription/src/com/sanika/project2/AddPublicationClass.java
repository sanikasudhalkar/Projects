package com.sanika.project2;

import java.sql.SQLException;

public class AddPublicationClass extends OpenDBConnection {

	public void addPublication(String npName, String frequency, String type) throws SQLException {
		String addNewspaper = "INSERT INTO PUBLICATION VALUES (?,?,?)";
		try {
			ps = c.prepareStatement(addNewspaper);
			ps.setString(1, npName);
			ps.setString(2, frequency);
			ps.setString(3, type);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			executeFinally();
		}

	}

}
