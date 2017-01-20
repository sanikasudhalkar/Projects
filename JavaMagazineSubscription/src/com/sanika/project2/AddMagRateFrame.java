package com.sanika.project2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddMagRateFrame extends Project2Frame {
	public AddMagRateFrame() {
		init();
	}

	private void init() {
		String magName = "Enter Magazine Name: ";
		JLabel magNameL = new JLabel(magName);
		magNameL.setFont(new Font("Serif", Font.PLAIN, 14));
		magNameL.setForeground(new Color(50, 50, 25));
		JTextField magNameTF = new JTextField();
		magNameTF.setFont(new Font("Serif", Font.PLAIN, 14));

		String noOfIssues = "Enter number of issues: ";
		JLabel noOfIssuesL = new JLabel(noOfIssues);
		noOfIssuesL.setFont(new Font("Serif", Font.PLAIN, 14));
		noOfIssuesL.setForeground(new Color(50, 50, 25));
		JTextField noOfIssuesTF = new JTextField();
		noOfIssuesTF.setFont(new Font("Serif", Font.PLAIN, 14));

		String cost = "Enter cost: ";
		JLabel costL = new JLabel(cost);
		costL.setFont(new Font("Serif", Font.PLAIN, 14));
		costL.setForeground(new Color(50, 50, 25));
		JTextField costTF = new JTextField();
		costTF.setFont(new Font("Serif", Font.PLAIN, 14));

		JButton addMagazineButton = new JButton("Add Issue Rate");
		addMagazineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String magName = magNameTF.getText();
					String noOfIssues = noOfIssuesTF.getText();
					String cost = costTF.getText();
					PublicationRateClass addMagazine = new PublicationRateClass();

					addMagazine.addIssueRate(magName, noOfIssues, cost);
				} catch (SQLException sql) {
					showErrorDialog((Component) e.getSource(), sql);
				} catch (Exception e1) {
					showExceptionDialog(e, e1);
				}
			}

		});
		createLayout(magNameL, magNameTF, noOfIssuesL, noOfIssuesTF, costL, costTF, addMagazineButton);
		setTitle("Add Issue Rate");
		setSize(300, 250);
		setLocationRelativeTo(null);

	}

	private void createLayout(JComponent... arg) {

		Container pane = getContentPane();
		GroupLayout grLayout = new GroupLayout(pane);
		pane.setLayout(grLayout);

		grLayout.setAutoCreateContainerGaps(true);

		grLayout.setHorizontalGroup(
				grLayout.createSequentialGroup()
						.addGroup(grLayout.createParallelGroup().addComponent(arg[0]).addComponent(arg[1])
								.addComponent(arg[2]).addComponent(arg[3]).addComponent(arg[4]).addComponent(arg[5])
								.addComponent(arg[6]))

		);

		grLayout.setVerticalGroup(
				grLayout.createParallelGroup()
						.addGroup(grLayout.createSequentialGroup().addComponent(arg[0]).addComponent(arg[1])
								.addComponent(arg[2]).addComponent(arg[3]).addComponent(arg[4]).addComponent(arg[5])
								.addComponent(arg[6])));

	}
}
