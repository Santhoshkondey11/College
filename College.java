package Main;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class College {

	private JFrame frame;
	private JTextField textField, textField_1, textField_2, textField_3;
	private JRadioButton rdbtnMale, rdbtnFemale;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				College window = new College();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public College() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(10, 41, 49, 14);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Roll No");
		lblNewLabel_1.setBounds(10, 66, 49, 14);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Ph No");
		lblNewLabel_2.setBounds(10, 91, 49, 14);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Place");
		lblNewLabel_3.setBounds(10, 116, 49, 14);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Gender");
		lblNewLabel_4.setBounds(10, 141, 49, 14);
		frame.getContentPane().add(lblNewLabel_4);

		textField = new JTextField();
		textField.setBounds(161, 38, 150, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(161, 63, 150, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(161, 88, 150, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(161, 113, 150, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(161, 137, 70, 23);
		frame.getContentPane().add(rdbtnMale);

		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(233, 137, 80, 23);
		frame.getContentPane().add(rdbtnFemale);

		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(rdbtnMale);
		genderGroup.add(rdbtnFemale);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(108, 195, 200, 23);
		frame.getContentPane().add(btnSubmit);

		// Submit button action
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertStudent();
			}
		});
	}

	private void insertStudent() {
		String name = textField.getText();
		String rollStr = textField_1.getText();
		String phone = textField_2.getText();
		String place = textField_3.getText();
		String gender = rdbtnMale.isSelected() ? "Male" : rdbtnFemale.isSelected() ? "Female" : "";

		// Basic validation
		if (name.isEmpty() || rollStr.isEmpty() || phone.isEmpty() || place.isEmpty() || gender.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Please fill all fields and select gender.");
			return;
		}

		int rollNo;
		try {
			rollNo = Integer.parseInt(rollStr);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Roll No must be a number.");
			return;
		}

		try {
			String url = "jdbc:sqlserver://localhost:1433;databaseName=collegeform;encrypt=true;trustServerCertificate=true";
			String user = "sa"; // Change if your username is different
			String password = "Santhu@11"; // Update with your actual password

			Connection conn = DriverManager.getConnection(url, user, password);
			String query = "INSERT INTO Students11 (Name, RollNo, Phone, Place, Gender) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, name);
			pst.setInt(2, rollNo);
			pst.setString(3, phone);
			pst.setString(4, place);
			pst.setString(5, gender);

			int rows = pst.executeUpdate();

			if (rows > 0) {
				JOptionPane.showMessageDialog(frame, "Student Registered Successfully!");
			} else {
				JOptionPane.showMessageDialog(frame, "Insert Failed!");
			}

			pst.close();
			conn.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Database Error: " + ex.getMessage());
		}
	}
}
