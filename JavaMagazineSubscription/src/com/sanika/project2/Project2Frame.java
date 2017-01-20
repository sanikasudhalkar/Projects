package com.sanika.project2;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Project2Frame extends JFrame {
	protected static void showErrorDialog(Component source, SQLException e1) {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage("ERROR " + e1.getErrorCode() + ": " + e1.getMessage());
		optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = optionPane.createDialog(source, "ERROR!!");
		dialog.setVisible(true);
	}

	private void showErrorDialog(SQLException e2) {
		// TODO Auto-generated method stub
		
	}
	protected static void showExceptionDialog(ActionEvent e, Exception e1) {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage("ERROR!!: " + e1.getMessage());
		optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		Component source = (Component) e.getSource();
		JDialog dialog = optionPane.createDialog(source, "ERROR");
		dialog.setVisible(true);
	}

}
