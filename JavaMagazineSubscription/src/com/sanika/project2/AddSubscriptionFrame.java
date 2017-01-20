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

public class AddSubscriptionFrame extends Project2Frame {

	protected JLabel noOfMonthsL;
	protected JTextField noOfMonthsTF;
	protected JComboBox<Integer> noOfIssuesCombo = new JComboBox<>();

	public AddSubscriptionFrame() {

		initUI();
	}

	private void initUI() {
		noOfIssuesCombo.setVisible(false);
		String customerId = "Enter Customer ID:";
		JLabel customerIdL = new JLabel(customerId);
		customerIdL.setFont(new Font("Serif", Font.PLAIN, 14));
		customerIdL.setForeground(new Color(50, 50, 25));
		JTextField customerIdTF = new JTextField();
		customerIdTF.setFont(new Font("Serif", Font.PLAIN, 14));

		String startDate = "Enter Start Date (yyyy-mm-dd)";
		JLabel startDateL = new JLabel(startDate);
		startDateL.setFont(new Font("Serif", Font.PLAIN, 14));
		startDateL.setForeground(new Color(50, 50, 25));
		JTextField startDateTF = new JTextField();
		startDateTF.setFont(new Font("Serif", Font.PLAIN, 14));

		String publicationName = "Select Publication Name: ";
		JLabel publicationNameL = new JLabel(publicationName);
		publicationNameL.setFont(new Font("Serif", Font.PLAIN, 14));
		publicationNameL.setForeground(new Color(50, 50, 25));
		AddSubscriptionClass apr = new AddSubscriptionClass();
		String[] publicationNames;
		JComboBox<String> pubNamesCombo = new JComboBox<>();
		try {
			publicationNames = apr.getPublicationList();
			pubNamesCombo.removeAllItems();
			for (String name : publicationNames) {
				pubNamesCombo.addItem(name);
			}

			pubNamesCombo.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {

					if (e.getStateChange() == ItemEvent.SELECTED) {

						try {
							AddSubscriptionClass asc = new AddSubscriptionClass();
							String type;
							type = asc.getType(e.getItem().toString());
							if (type.equalsIgnoreCase("Newspaper")) {
								// noOfIssuesCombo.setVisible(false);
								noOfMonthsL.setVisible(true);
								noOfMonthsTF.setVisible(true);

							} else {
								noOfMonthsL.setText("Select no. of issues:");
								// noOfMonthsTF.setVisible(false);
								noOfMonthsL.setVisible(true);
								noOfIssuesCombo.setVisible(true);

								PublicationRateClass apr = new PublicationRateClass();
								Integer[] issues;
								issues = apr.getNoOfIssues(e.getItem().toString());
								noOfIssuesCombo.removeAllItems();
								for (Integer i : issues) {
									noOfIssuesCombo.addItem(i);
								}
							}
						} catch (SQLException e1) {
							showErrorDialog((Component) e.getSource(), e1);
						}

					}
				}

			});
		} catch (SQLException e2) {
			showErrorDialog(pubNamesCombo.getComponent(0), e2);
		}

		String noOfMonths = "Enter Duration in months";
		noOfMonthsL = new JLabel(noOfMonths);
		noOfMonthsL.setFont(new Font("Serif", Font.PLAIN, 14));
		noOfMonthsL.setForeground(new Color(50, 50, 25));
		noOfMonthsTF = new JTextField();
		noOfMonthsTF.setFont(new Font("Serif", Font.PLAIN, 14));
		noOfMonthsL.setVisible(false);
		noOfMonthsTF.setVisible(false);

		JButton addCustButton = new JButton("Add Subscription");
		addCustButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String customerID = customerIdTF.getText();
				String startDate = startDateTF.getText();

				String pubName = pubNamesCombo.getSelectedItem().toString();
				String noOfMonths = noOfMonthsTF.getText();
				String noOfIssues = "";
				if (noOfIssuesCombo.isVisible())
					noOfIssues = noOfIssuesCombo.getSelectedItem().toString();
				AddSubscriptionClass addSubs = new AddSubscriptionClass();
				int subsId;
				try {
					if (noOfMonthsTF.isVisible())
						subsId = addSubs.addNewspaper(customerID, startDate, noOfMonths, pubName);
					else
						subsId = addSubs.addMagazine(customerID, startDate, noOfIssues, pubName);
					JOptionPane optionPane = new JOptionPane();
					optionPane.setMessage("Your Subscription ID is:" + subsId);
					optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
					Component source = (Component) e.getSource();
					JDialog dialog = optionPane.createDialog(source, "Customer ID");
					dialog.setVisible(true);
				} catch (SQLException e1) {
					showErrorDialog((Component) e.getSource(), e1);
				} catch (Exception e1) {
					showExceptionDialog(e, e1);
				}

			}
		});
		createLayout(customerIdL, customerIdTF, startDateL, startDateTF, publicationNameL, pubNamesCombo, noOfMonthsL,
				noOfMonthsTF, noOfIssuesCombo, addCustButton);
		setTitle("Add Subscription");
		setSize(400, 250);
		setLocationRelativeTo(null);
	}

	private void createLayout(JComponent... arg) {

		Container pane = getContentPane();
		GroupLayout grLayout = new GroupLayout(pane);
		pane.setLayout(grLayout);

		grLayout.setAutoCreateContainerGaps(true);

		grLayout.setHorizontalGroup(grLayout.createSequentialGroup()
				.addGroup(grLayout.createParallelGroup().addComponent(arg[0]).addComponent(arg[1]).addComponent(arg[2])
						.addComponent(arg[3]).addComponent(arg[4]).addComponent(arg[5]).addComponent(arg[6])
						.addComponent(arg[7]).addComponent(arg[8]).addComponent(arg[9]))

		);

		grLayout.setVerticalGroup(grLayout.createParallelGroup()
				.addGroup(grLayout.createSequentialGroup().addComponent(arg[0]).addComponent(arg[1])
						.addComponent(arg[2]).addComponent(arg[3]).addComponent(arg[4]).addComponent(arg[5])
						.addComponent(arg[6]).addComponent(arg[7]).addComponent(arg[8]).addComponent(arg[9])));

	}

}
