package com.sanika.project2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class UpdateMagazineCostFrame extends Project2Frame {
	JComboBox<Integer> noOfIssuesCombo = new JComboBox<>();

	public UpdateMagazineCostFrame() {
		init();
	}

	private void init() {
		String magName = "Select Publication Name: ";
		JLabel magNameL = new JLabel(magName);
		magNameL.setFont(new Font("Serif", Font.PLAIN, 14));
		magNameL.setForeground(new Color(50, 50, 25));
		PublicationRateClass apr = new PublicationRateClass();
		String[] magazineNames;
		JComboBox<String> magNames = new JComboBox<>();
		try {
			magazineNames = apr.getMagazineList();
			magNames.removeAllItems();
			for (String mag : magazineNames) {
				magNames.addItem(mag);
			}
			apr = new PublicationRateClass();
			Integer[] issues = apr.getNoOfIssues(magNames.getSelectedItem().toString());
			noOfIssuesCombo.removeAllItems();
			for (Integer i : issues) {
				noOfIssuesCombo.addItem(i);
			}
			magNames.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {

					if (e.getStateChange() == ItemEvent.SELECTED) {

						try {
							PublicationRateClass apr = new PublicationRateClass();
							Integer[] issues;
							issues = apr.getNoOfIssues(e.getItem().toString());
							noOfIssuesCombo.removeAllItems();
							for (Integer i : issues) {
								noOfIssuesCombo.addItem(i);
							}
						} catch (SQLException e1) {
							showErrorDialog((Component)e.getSource(),e1);
						}

					}
				}
			});

		} catch (SQLException e2) {
			showErrorDialog(magNames.getComponent(0), e2);
		}

		String noOfIssues = "Select number of issues: ";
		JLabel noOfIssuesL = new JLabel(noOfIssues);
		noOfIssuesL.setFont(new Font("Serif", Font.PLAIN, 14));
		noOfIssuesL.setForeground(new Color(50, 50, 25));

		String cost = "Enter cost: ";
		JLabel costL = new JLabel(cost);
		costL.setFont(new Font("Serif", Font.PLAIN, 14));
		costL.setForeground(new Color(50, 50, 25));
		JTextField costTF = new JTextField();
		costTF.setFont(new Font("Serif", Font.PLAIN, 14));

		JButton updateMagButton = new JButton("Update Issue Cost");
		updateMagButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String magName = magNames.getSelectedItem().toString();
					String noOfIssues = noOfIssuesCombo.getSelectedItem().toString();
					String cost = costTF.getText();
					PublicationRateClass addMagazine = new PublicationRateClass();

					addMagazine.updateIssueRate(magName, noOfIssues, cost);
				} catch (SQLException sqlEx) {
					showErrorDialog((Component)e.getSource(),sqlEx);
				} catch (Exception e1) {
					showExceptionDialog(e, e1);
				}
			}
		});
		createLayout(magNameL, magNames, noOfIssuesL, noOfIssuesCombo, costL, costTF, updateMagButton);
		setTitle("Update Issue Cost");
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
