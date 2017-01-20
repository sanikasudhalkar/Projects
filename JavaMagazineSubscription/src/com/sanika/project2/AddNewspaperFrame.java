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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AddNewspaperFrame extends Project2Frame {
	public AddNewspaperFrame() {
		init();
	}

	private void init() {
		String npNameT = "Enter Newspaper Name: ";
		JLabel npNameL = new JLabel(npNameT);
		npNameL.setFont(new Font("Serif", Font.PLAIN, 14));
		npNameL.setForeground(new Color(50, 50, 25));
		JTextField npNameTF = new JTextField();
		npNameTF.setFont(new Font("Serif", Font.PLAIN, 14));

		String frequencyL = "Select Frequency ";
		JLabel frequencyLB = new JLabel(frequencyL);
		frequencyLB.setFont(new Font("Serif", Font.PLAIN, 14));
		frequencyLB.setForeground(new Color(50, 50, 25));

		JRadioButton dailyButton = new JRadioButton("Daily");
		dailyButton.setActionCommand("D");
		dailyButton.setSelected(true);
		
		JRadioButton weeklyButton = new JRadioButton("Weekly");
		weeklyButton.setActionCommand("W");

		// Group the radio buttons.
		ButtonGroup frequencyGroup = new ButtonGroup();
		frequencyGroup.add(dailyButton);
		frequencyGroup.add(weeklyButton);

		JButton addNewsPaperButton = new JButton("Add Newspaper");
		addNewsPaperButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				String npName = npNameTF.getText();
				String frequency = frequencyGroup.getSelection().getActionCommand();
				AddPublicationClass addNewspaper = new AddPublicationClass();
				
					addNewspaper.addPublication(npName, frequency, "Newspaper");
				} catch (SQLException e1) {
					showErrorDialog((Component) e.getSource(),e1);
				}
			}
		});
		createLayout(npNameL, npNameTF, frequencyLB, dailyButton, weeklyButton, addNewsPaperButton);
		setTitle("Add Newspaper");
		setSize(300, 200);
		setLocationRelativeTo(null);

	}

	private void createLayout(JComponent... arg) {

		Container pane = getContentPane();
		GroupLayout grLayout = new GroupLayout(pane);
		pane.setLayout(grLayout);

		grLayout.setAutoCreateContainerGaps(true);

		grLayout.setHorizontalGroup(grLayout.createSequentialGroup()
				.addGroup(grLayout.createParallelGroup().addComponent(arg[0]).addComponent(arg[1]).addComponent(arg[2])
						.addComponent(arg[3]).addComponent(arg[4]).addComponent(arg[5]))

		);

		grLayout.setVerticalGroup(grLayout.createParallelGroup()
				.addGroup(grLayout.createSequentialGroup().addComponent(arg[0]).addComponent(arg[1])
						.addComponent(arg[2]).addComponent(arg[3]).addComponent(arg[4]).addComponent(arg[5])));

	}
}
