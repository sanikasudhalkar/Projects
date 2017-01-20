package com.sanika.project2;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.ResultSetImpl;

public class SubscriptionInformationClass extends OpenDBConnection {

	public String getInfo(String custID) throws SQLException {
		String noOfIssues = "select s.sub_id as 'subs_id', c.fname_initial as 'First_Name', c.lname as 'Last_Name', s.pub_name as 'Newspaper_Name', s.end_date as 'End_Date' from subscription as s, customer as c, publication as p where s.cust_id = c.customer_id and s.pub_name =p.name and s.cust_id=?";
		Integer[] no_of_issues = null;
		try {
			ps = c.prepareStatement(noOfIssues);
			ps.setInt(1, Integer.parseInt(custID));
			ResultSet rs = ps.executeQuery();
			double rowCount = ((ResultSetImpl) rs).getUpdateCount();
			String output = "";
			while (rs.next()) {
				output = output + rs.getInt("subs_id") + "      " + rs.getString("First_Name") + " "
						+ rs.getString("Last_Name") + "      " + rs.getString("Newspaper_Name") + "      "
						+ rs.getString("End_Date") + "\n";

			}
			return output;

		} catch (SQLException e) {
			throw e;
		}

	}

}
