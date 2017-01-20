package com.sanika.project2;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.ResultSetImpl;

public class PublicationRateClass extends OpenDBConnection {

	public void addIssueRate(String magName, String noOfIssues, String cost) throws SQLException, Exception {
		String fetchPubRec = "Select frequency from publication where name = ?";
		String addNewspaper = "INSERT INTO ISSUE_SUBSCRIPTION_RATES VALUES (?,?,?)";
		try {
			ps = c.prepareStatement(fetchPubRec);
			ps.setString(1, magName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String frequency = rs.getString("frequency");
				if (frequency.equalsIgnoreCase("D")) {
					throw new Exception("Publication is of Daily type!! Issue rate cannot be added.");
				}
			}

			ps = c.prepareStatement(addNewspaper);
			ps.setString(1, magName);
			ps.setString(2, noOfIssues);
			ps.setString(3, cost);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			executeFinally();
		}

	}

	public String[] getMagazineList() throws SQLException {
		String fetchMagNames = "Select distinct isr.pub_name as mag_name from publication as p, issue_subscription_rates as isr where isr.pub_name = p.name AND p.publication_type = ?";
		String[] magazines = null;
		try {
			ps = c.prepareStatement(fetchMagNames);
			ps.setString(1, "Magazine");
			ResultSet rs = ps.executeQuery();
			double rowCount = ((ResultSetImpl) rs).getUpdateCount();
			magazines = new String[(int) rowCount];
			int i = 0;
			while (rs.next()) {
				magazines[i] = rs.getString("mag_name");
				i++;

			}
			return magazines;

		} catch (SQLException e) {
			throw e;
		} finally {
			executeFinally();
		}
	}

	public Integer[] getNoOfIssues(String pubName) throws SQLException {
		String noOfIssues = "Select no_of_issues from issue_subscription_rates where pub_name=?";
		Integer[] no_of_issues = null;
		try {
			ps = c.prepareStatement(noOfIssues);
			ps.setString(1, pubName);
			ResultSet rs = ps.executeQuery();
			double rowCount = ((ResultSetImpl) rs).getUpdateCount();
			no_of_issues = new Integer[(int) rowCount];
			int i = 0;
			while (rs.next()) {
				no_of_issues[i] = rs.getInt("no_of_issues");
				i++;

			}
			return no_of_issues;

		} catch (SQLException e) {
			throw e;
		} finally {
			executeFinally();
		}

	}

	public void updateIssueRate(String magName, String noOfIssues, String cost) throws Exception {
		String updateIssueRate = "UPDATE ISSUE_SUBSCRIPTION_RATES SET COST=? WHERE PUB_NAME=? AND NO_OF_ISSUES=?";
		try {
			ps = c.prepareStatement(updateIssueRate);
			ps.setInt(1, Integer.parseInt(cost));
			ps.setString(2, magName);
			ps.setInt(3, Integer.parseInt(noOfIssues));

			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			executeFinally();
		}

	}

	public String[] getNewspaperList() throws SQLException {
		String fetchMagNames = "Select distinct daily_name from daily_subscription_rates";
		String[] newspapers = null;
		try {
			ps = c.prepareStatement(fetchMagNames);
			// ps.setString(1, "Magazine");
			ResultSet rs = ps.executeQuery();
			double rowCount = ((ResultSetImpl) rs).getUpdateCount();
			newspapers = new String[(int) rowCount];
			int i = 0;
			while (rs.next()) {
				newspapers[i] = rs.getString("daily_name");
				i++;

			}
			return newspapers;

		} catch (SQLException e) {
			throw e;
		} finally {
			executeFinally();
		}
	}

	public Integer[] getNoOfDays(String npName) throws SQLException {
		String noOfDays = "Select no_of_days from daily_subscription_rates where daily_name=?";
		Integer[] no_of_days = null;
		try {
			ps = c.prepareStatement(noOfDays);
			ps.setString(1, npName);
			ResultSet rs = ps.executeQuery();
			double rowCount = ((ResultSetImpl) rs).getUpdateCount();
			no_of_days = new Integer[(int) rowCount];
			int i = 0;
			while (rs.next()) {
				no_of_days[i] = rs.getInt("no_of_days");
				i++;

			}
			return no_of_days;

		} catch (SQLException e) {
			throw e;
		} finally {
			executeFinally();
		}
	}

	public void updateDayRates(String npName, String noOfDays, String cost) throws Exception {
		String updateDailyRates = "UPDATE DAILY_SUBSCRIPTION_RATES SET COST=? WHERE DAILY_NAME=? AND NO_OF_DAYS=?";
		try {
			ps = c.prepareStatement(updateDailyRates);
			ps.setInt(1, Integer.parseInt(cost));
			ps.setString(2, npName);
			ps.setString(3, noOfDays);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			executeFinally();
		}

	}
}
