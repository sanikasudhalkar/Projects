package com.sanika.project2;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class StartupFrame extends Project2Frame {
	public StartupFrame() {
		init();
	}

	private void init() {
		JLabel selectTransL = new JLabel("Select the database transaction to perform on the Subscriptions database: ");
		selectTransL.setFont(new Font("Serif", Font.PLAIN, 16));
		selectTransL.setForeground(new Color(50, 50, 25));
		// 3.1 The first transaction is to add information about a new CUSTOMER.
		JButton addCustButton = new JButton("Add Customer");
		addCustButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddCustomerFrame addButtonFrame = new AddCustomerFrame();
				addButtonFrame.setVisible(true);
			}
		});

		// 3.2 The second transaction is to add all the information about a new
		// MAGAZINE PUBLICATION.
		JButton addMagazine = new JButton("Add Magazine Publication");
		addMagazine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddMagazineFrame addMagazineFrame = new AddMagazineFrame();
				addMagazineFrame.setVisible(true);
			}
		});

		// 3.3 The third transaction is to add all the information about a new
		// NEWSPAPER PUBLICATION.
		JButton addNewspaper = new JButton("Add Newspaper Publication");
		addNewspaper.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddNewspaperFrame addNewspaper = new AddNewspaperFrame();
				addNewspaper.setVisible(true);
			}
		});
		// 3.4 The fourth transaction is to add information about a new Rate
		// based on a new NoOfIssues for a PUBLICATION (this applies to
		// magazines).
		JButton addMagazineRate = new JButton("Add Issue Rate");
		addMagazineRate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddMagRateFrame addMagRate = new AddMagRateFrame();
				addMagRate.setVisible(true);
			}
		});
		// 3.5 The fifth transaction is to update a particular rate for a
		// MAGAZINE PUBLICATION (here we are changing only the subscription Cost
		// amount for a given NoOfIssues).
		JButton updateMagCost = new JButton("Update Issue Cost");
		updateMagCost.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateMagazineCostFrame updateMagCostFr = new UpdateMagazineCostFrame();
				updateMagCostFr.setVisible(true);
			}
		});

		// 3.6 The sixth transaction is to update a particular rate for a
		// NEWSPAPER PUBLICATION (here we are changing only the subscription
		// Cost amount for a given NoOfDays).
		JButton updateNPCost = new JButton("Update Daily Newspaper Cost");
		updateNPCost.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateNPCostFrame updateNPCFrame = new UpdateNPCostFrame();
				updateNPCFrame.setVisible(true);
			}
		});

		// 3.7 The seventh transaction is to enter information about a new
		// SUBSCRIPTION (assume the subscription is between an existing customer
		// and an existing publication using an existing rate).
		JButton addSubscription = new JButton("Add Subscription");
		addSubscription.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddSubscriptionFrame addSubscriptionFrame = new AddSubscriptionFrame();
				addSubscriptionFrame.setVisible(true);
			}
		});

		// 3.8 The eighth transaction will retrieve and print all subscriptions
		// (include customer info, publication info, and end date of
		// subscription) for a particular customer.
		JButton printAllSubscriptions = new JButton("Print All Subscriptions");
		printAllSubscriptions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PrintSubscriptionsFrame printSubscriptionsFrame = new PrintSubscriptionsFrame();
				printSubscriptionsFrame.setVisible(true);
			}
		});
		createLayout(selectTransL, addCustButton, addMagazine, addNewspaper, addMagazineRate, updateMagCost,
				updateNPCost, addSubscription, printAllSubscriptions);
		setTitle("Subscription Database Operations");
		setSize(500, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void createLayout(JComponent... buttons) {
		Container pane = getContentPane();
		GroupLayout grLayout = new GroupLayout(pane);
		pane.setLayout(grLayout);

		grLayout.setAutoCreateContainerGaps(true);
		grLayout.setAutoCreateGaps(true);
		grLayout.setHorizontalGroup(grLayout.createSequentialGroup()
				.addGroup(grLayout.createParallelGroup().addComponent(buttons[0]).addComponent(buttons[1])
						.addComponent(buttons[2]).addComponent(buttons[3]).addComponent(buttons[4])
						.addComponent(buttons[5]).addComponent(buttons[6]).addComponent(buttons[7])
						.addComponent(buttons[8]))

		);

		grLayout.setVerticalGroup(grLayout.createParallelGroup()
				.addGroup(grLayout.createSequentialGroup().addComponent(buttons[0]).addComponent(buttons[1])
						.addComponent(buttons[2]).addComponent(buttons[3]).addComponent(buttons[4])
						.addComponent(buttons[5]).addComponent(buttons[6]).addComponent(buttons[7])
						.addComponent(buttons[8])));
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				StartupFrame startupFrame = new StartupFrame();
				startupFrame.setVisible(true);
			}
		});
	}

}
