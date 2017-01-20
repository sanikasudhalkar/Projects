package com.sanika.project2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AddMagazineFrame extends Project2Frame {
	public AddMagazineFrame() {
		init();
	}

	private void init() {
		String magName = "Enter Magazine Name: ";
		JLabel magNameL = new JLabel(magName);
		magNameL.setFont(new Font("Serif", Font.PLAIN, 14));
		magNameL.setForeground(new Color(50, 50, 25));
		JTextField magNameTF = new JTextField();
		magNameTF.setFont(new Font("Serif", Font.PLAIN, 14));

		String frequencyL = "Select Frequency ";
		JLabel frequencyLB = new JLabel(frequencyL);
		frequencyLB.setFont(new Font("Serif", Font.PLAIN, 14));
		frequencyLB.setForeground(new Color(50, 50, 25));

		JRadioButton weeklyButton = new JRadioButton("Weekly");
		weeklyButton.setActionCommand("W");
		weeklyButton.setSelected(true);
		JRadioButton monthlyButton = new JRadioButton("Monthly");
		monthlyButton.setActionCommand("M");

		JRadioButton quarterlyButton = new JRadioButton("Quarterly");
		quarterlyButton.setActionCommand("Q");

		// Group the radio buttons.
		ButtonGroup frequencyGroup = new ButtonGroup();
		frequencyGroup.add(weeklyButton);
		frequencyGroup.add(monthlyButton);
		frequencyGroup.add(quarterlyButton);

		JButton addMagazineButton = new JButton("Add Magazine");
		addMagazineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String npName = magNameTF.getText();
					String frequency = frequencyGroup.getSelection().getActionCommand();
					AddPublicationClass addMagazine = new AddPublicationClass();

					addMagazine.addPublication(npName, frequency, "Magazine");
				} catch (SQLException e1) {
					showErrorDialog((Component) e.getSource(), e1);
				}
			}
		});
		createLayout(magNameL, magNameTF, frequencyLB, weeklyButton, monthlyButton, quarterlyButton, addMagazineButton);
		setTitle("Add Magazine");
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
