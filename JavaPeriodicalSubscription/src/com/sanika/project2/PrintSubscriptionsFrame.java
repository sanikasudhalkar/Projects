package com.sanika.project2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PrintSubscriptionsFrame extends Project2Frame {
	public PrintSubscriptionsFrame() {

		initUI();
	}

	private void initUI() {

		JLabel custIDL = new JLabel("Enter Customer ID");
		custIDL.setFont(new Font("Serif", Font.PLAIN, 14));
		custIDL.setForeground(new Color(50, 50, 25));
		JTextField custIDTF = new JTextField();
		custIDTF.setFont(new Font("Serif", Font.PLAIN, 14));
		custIDTF.setForeground(new Color(50, 50, 25));

		String subInfo = "SubID Customer Name Publication Name  End Date";
		JLabel subInfoL = new JLabel(subInfo);
		subInfoL.setFont(new Font("Serif", Font.PLAIN, 14));
		subInfoL.setForeground(new Color(50, 50, 25));
		JTextArea subInfoTA = new JTextArea();
		subInfoTA.setFont(new Font("Serif", Font.PLAIN, 14));
		subInfoL.setVisible(false);
		subInfoTA.setVisible(false);
		subInfoTA.setEditable(false);
		JScrollPane areaScrollPane = new JScrollPane(subInfoTA);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 250));
		areaScrollPane.setVisible(false);

		JButton fetchSubInfo = new JButton("Fetch Subscription Info");
		fetchSubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String custID = custIDTF.getText();
				SubscriptionInformationClass si = new SubscriptionInformationClass();
				String info = "";
				try {
					info = si.getInfo(custID);
					subInfoTA.setText(info);
					subInfoL.setVisible(true);
					subInfoTA.setVisible(true);
					areaScrollPane.setVisible(true);

				} catch (SQLException sqlEx) {
					showErrorDialog((Component) e.getSource(), sqlEx);
				}

			}

		});

		createLayout(custIDL, custIDTF, subInfoL, areaScrollPane, fetchSubInfo);
		setTitle("Print All Subscriptions");
		setSize(350, 400);
		setLocationRelativeTo(null);
	}

	private void createLayout(JComponent... arg) {

		Container pane = getContentPane();
		GroupLayout grLayout = new GroupLayout(pane);
		pane.setLayout(grLayout);

		grLayout.setAutoCreateContainerGaps(true);

		grLayout.setHorizontalGroup(
				grLayout.createSequentialGroup().addGroup(grLayout.createParallelGroup().addComponent(arg[0])
						.addComponent(arg[1]).addComponent(arg[2]).addComponent(arg[3]).addComponent(arg[4]))

		);

		grLayout.setVerticalGroup(
				grLayout.createParallelGroup().addGroup(grLayout.createSequentialGroup().addComponent(arg[0])
						.addComponent(arg[1]).addComponent(arg[2]).addComponent(arg[3]).addComponent(arg[4])));

	}
}
