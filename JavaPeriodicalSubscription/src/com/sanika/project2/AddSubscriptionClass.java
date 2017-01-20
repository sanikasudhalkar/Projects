package com.sanika.project2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.mysql.jdbc.ResultSetImpl;

public class AddSubscriptionClass extends OpenDBConnection {

	public String getType(String pubName) throws SQLException {
		String fetchMagNames = "Select publication_type from publication where name=?";
		try {
			ps = c.prepareStatement(fetchMagNames);
			ps.setString(1, pubName);
			ResultSet rs = ps.executeQuery();
			String pubType = null;
			while (rs.next()) {
				pubType = rs.getString("publication_type");

			}
			return pubType;

		} catch (SQLException e) {
			throw e;
		} finally {
			executeFinally();
		}
	}

	public String[] getPublicationList() throws SQLException {
		String fetchMagNames = "Select name from publication";
		String[] publications = null;
		try {
			ps = c.prepareStatement(fetchMagNames);
			ResultSet rs = ps.executeQuery();
			double rowCount = ((ResultSetImpl) rs).getUpdateCount();
			publications = new String[(int) rowCount];
			int i = 0;
			while (rs.next()) {
				publications[i] = rs.getString("name");
				i++;

			}
			return publications;

		} catch (SQLException e) {
			throw e;
		} finally {
			executeFinally();
		}
	}

	public int addNewspaper(String customerID, String startDate, String noOfMonths, String pubName) throws Exception {
		try {

			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date date = (Date) formatter.parse(startDate);
			Date endDate = date;
			endDate.setMonth((date.getMonth() - 1 + Integer.parseInt(noOfMonths)) % 12 + 1);
			String addSubscription = "INSERT INTO SUBSCRIPTION (cust_id, start_date, end_date, pub_name) values (?,?,?,?) ";
			ps = c.prepareStatement(addSubscription, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, Integer.parseInt(customerID));
			ps.setString(2, startDate);
			ps.setString(3, formatter.format(endDate));
			ps.setString(4, pubName);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			int subID = 0;
			if (rs != null && rs.next()) {
				subID = rs.getInt(1);
			}
			if (subID != 0) {
				String addNPSubs = "INSERT INTO NEWSPAPER_SUBSCRIPTION values (?,?,?) ";
				ps = c.prepareStatement(addNPSubs);
				ps.setInt(1, subID);
				ps.setInt(2, Integer.parseInt(customerID));
				ps.setInt(3, Integer.parseInt(noOfMonths));

				ps.executeUpdate();
			}
			return subID;

		} catch (ParseException e) {
			throw new Exception("Parse Exception: Error parsing StartDate format!");
		} finally {
			executeFinally();
		}
	}

	public int addMagazine(String customerID, String startDate, String noOfIssues, String pubName) throws Exception {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date;
		try {
			date = (Date) formatter.parse(startDate);
			String getFreq = "select frequency from publication where name=?";
			ps = c.prepareStatement(getFreq, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pubName);
			ResultSet rs = ps.executeQuery();
			String freq = null;
			while (rs.next()) {
				freq = rs.getString("frequency");

			}
			int noOfWeeks = 0;
			if (freq.equalsIgnoreCase("W")) {
				noOfWeeks = Integer.parseInt(noOfIssues) - 1;
			} else if (freq.equalsIgnoreCase("M")) {
				noOfWeeks = (Integer.parseInt(noOfIssues) - 1) * 4;
			} else if (freq.equalsIgnoreCase("Q")) {
				noOfWeeks = (Integer.parseInt(noOfIssues) - 1) * 12; // since a
																		// quarter
																		// is 3
																		// months,
																		// 4*3=12
			}
			int noOfDays = 7 * noOfWeeks;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
			Date endDate = calendar.getTime();
			String finalEndDate = formatter.format(endDate);
			String addSubscription = "INSERT INTO SUBSCRIPTION (cust_id, start_date, end_date, pub_name) values (?,?,?,?) ";
			ps = c.prepareStatement(addSubscription, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, Integer.parseInt(customerID));
			ps.setString(2, startDate);
			ps.setString(3, finalEndDate);
			ps.setString(4, pubName);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				return rs.getInt(1);
			}
		} catch (ParseException e) {
			throw new Exception("Parse Exception: Error parsing StartDate format!");
		} finally {
			executeFinally();
		}

		return 0;
	}

}
