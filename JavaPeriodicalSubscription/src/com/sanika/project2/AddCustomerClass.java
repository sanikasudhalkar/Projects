package com.sanika.project2;

import java.sql.SQLException;
import java.sql.Statement;

public class AddCustomerClass extends OpenDBConnection {

	public int addCustomer(String fName, String lName, String address) throws SQLException {
		try {
			String addCustomerStmt = "INSERT INTO CUSTOMER (fname_initial, lname, address) values (?,?,?) ";
			ps = c.prepareStatement(addCustomerStmt, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, fName);
			ps.setString(2, lName);
			ps.setString(3, address);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			executeFinally();
		}
		return -1;
	}

}
