package com.sanika.project2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
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

public class AddCustomerFrame extends Project2Frame {

	public AddCustomerFrame() {

		initUI();
	}

	private void initUI() {
		String firstNameT = "Enter First Name Initial: ";
		JLabel firstNameL = new JLabel(firstNameT);
		firstNameL.setFont(new Font("Serif", Font.PLAIN, 14));
		firstNameL.setForeground(new Color(50, 50, 25));
		JTextField firstNameTF = new JTextField();
		firstNameTF.setFont(new Font("Serif", Font.PLAIN, 14));

		String lastNameT = "Enter Last Name: ";
		JLabel lastNameL = new JLabel(lastNameT);
		lastNameL.setFont(new Font("Serif", Font.PLAIN, 14));
		lastNameL.setForeground(new Color(50, 50, 25));
		JTextField lastNameTF = new JTextField();
		lastNameTF.setFont(new Font("Serif", Font.PLAIN, 14));

		String addressT = "Enter Address: ";
		JLabel addressL = new JLabel(addressT);
		addressL.setFont(new Font("Serif", Font.PLAIN, 14));
		addressL.setForeground(new Color(50, 50, 25));
		JTextField addressTF = new JTextField();
		addressTF.setFont(new Font("Serif", Font.PLAIN, 14));

		JButton addCustButton = new JButton("Add Customer");
		addCustButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String fName = firstNameTF.getText();
				String lName = lastNameTF.getText();
				String address = addressTF.getText();
				AddCustomerClass addCust = new AddCustomerClass();
				int custID;
				try {
					custID = addCust.addCustomer(fName, lName, address);
					JOptionPane optionPane = new JOptionPane();
					optionPane.setMessage("Your Customer ID is:" + custID);
					optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
					Component source = (Component) e.getSource();
					JDialog dialog = optionPane.createDialog(source, "Customer ID");
					dialog.setVisible(true);
				} catch (SQLException e1) {
					showErrorDialog((Component) e.getSource(), e1);
				}
			}

		});
		createLayout(firstNameL, firstNameTF, lastNameL, lastNameTF, addressL, addressTF, addCustButton);
		setTitle("Add Customer");
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