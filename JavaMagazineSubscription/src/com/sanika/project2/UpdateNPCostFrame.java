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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UpdateNPCostFrame extends Project2Frame {
	JComboBox<Integer> noOfDaysCombo = new JComboBox<>();

	public UpdateNPCostFrame() {
		init();
	}

	private void init() {
		String npName = "Select Daily Newspaper Name: ";
		JLabel npNameL = new JLabel(npName);
		npNameL.setFont(new Font("Serif", Font.PLAIN, 14));
		npNameL.setForeground(new Color(50, 50, 25));
		PublicationRateClass apr = new PublicationRateClass();
		String[] newspaperNames;
		JComboBox<String> npNamesCombo = new JComboBox<>();
		try {
			newspaperNames = apr.getNewspaperList();
			npNamesCombo.removeAllItems();
			for (String np : newspaperNames) {
				npNamesCombo.addItem(np);
			}
			apr = new PublicationRateClass();
			Integer[] issues = apr.getNoOfDays(npNamesCombo.getSelectedItem().toString());
			noOfDaysCombo.removeAllItems();
			for (Integer i : issues) {
				noOfDaysCombo.addItem(i);
			}
			npNamesCombo.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {

					if (e.getStateChange() == ItemEvent.SELECTED) {
						
						try {
							PublicationRateClass apr = new PublicationRateClass();
							Integer[] issues;
							issues = apr.getNoOfDays(e.getItem().toString());
							noOfDaysCombo.removeAllItems();
							for (Integer i : issues) {
								noOfDaysCombo.addItem(i);
							}
						} catch (SQLException e1) {
							showErrorDialog((Component)e.getSource(),e1);
						}
						
					}
				}
			});

		} catch (SQLException e2) {
			showErrorDialog(npNamesCombo.getComponent(0),e2);
		}

		String noOfDays = "Select number of days: ";
		JLabel noOfDaysL = new JLabel(noOfDays);
		noOfDaysL.setFont(new Font("Serif", Font.PLAIN, 14));
		noOfDaysL.setForeground(new Color(50, 50, 25));

		String cost = "Enter cost: ";
		JLabel costL = new JLabel(cost);
		costL.setFont(new Font("Serif", Font.PLAIN, 14));
		costL.setForeground(new Color(50, 50, 25));
		JTextField costTF = new JTextField();
		costTF.setFont(new Font("Serif", Font.PLAIN, 14));

		JButton updateNPButton = new JButton("Update daily newspaper cost:");
		updateNPButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String npName = npNamesCombo.getSelectedItem().toString();
					String noOfIssues = noOfDaysCombo.getSelectedItem().toString();
					String cost = costTF.getText();
					PublicationRateClass addMagazine = new PublicationRateClass();

					addMagazine.updateDayRates(npName, noOfIssues, cost);
				} catch (SQLException e1) {
					showErrorDialog((Component)e.getSource(),e1);
				} catch (Exception ex) {
					showExceptionDialog(e, ex);
				}
			}
		});
		createLayout(npNameL, npNamesCombo, noOfDaysL, noOfDaysCombo, costL, costTF, updateNPButton);
		setTitle("Add Daily Newspaper Cost");
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
