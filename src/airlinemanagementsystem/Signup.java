package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {

	JTextField tfName, tfEmail, tfPhone, tfNationality;
    JPasswordField pfPassword;
    JComboBox<String> cbGender;
    JButton submit, back;

    public Signup() {
        setTitle("Airline Management System - Signup");
        setLayout(null);
        getContentPane().setBackground(new Color(173, 216, 230));

        JLabel heading = new JLabel("Sign Up for Shiva Airlines");
        heading.setBounds(150, 20, 400, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setForeground(Color.BLUE);
        add(heading);

        JLabel lblName = new JLabel("Full Name:");
        lblName.setBounds(50, 80, 100, 25);
        add(lblName);

        tfName = new JTextField();
        tfName.setBounds(160, 80, 200, 25);
        add(tfName);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 120, 100, 25);
        add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(160, 120, 200, 25);
        add(tfEmail);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 160, 100, 25);
        add(lblPassword);

        pfPassword = new JPasswordField();
        pfPassword.setBounds(160, 160, 200, 25);
        add(pfPassword);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(50, 200, 100, 25);
        add(lblPhone);

        tfPhone = new JTextField();
        tfPhone.setBounds(160, 200, 200, 25);
        add(tfPhone);

        JLabel lblNationality = new JLabel("Nationality:");
        lblNationality.setBounds(50, 240, 100, 25);
        add(lblNationality);

        tfNationality = new JTextField();
        tfNationality.setBounds(160, 240, 200, 25);
        add(tfNationality);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(50, 280, 100, 25);
        add(lblGender);

        String[] genderOptions = {"Male", "Female", "Other"};
        cbGender = new JComboBox<>(genderOptions);
        cbGender.setBounds(160, 280, 200, 25);
        add(cbGender);

        submit = new JButton("Sign Up");
        submit.setBounds(100, 350, 100, 30);
        submit.setBackground(Color.BLUE);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        add(submit);

        back = new JButton("Back");
        back.setBounds(250, 350, 100, 30);
        back.setBackground(Color.GRAY);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        setSize(600, 450);
        setLocation(500, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String name = tfName.getText();
            String email = tfEmail.getText();
            String password = new String(pfPassword.getPassword());
            String phone = tfPhone.getText();
            String nationality = tfNationality.getText();
            String gender = cbGender.getSelectedItem().toString();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || nationality.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields!");
                return;
            }

            try {
                Conn conn = new Conn();

                String checkQuery = "SELECT * FROM login WHERE email = ? OR phone_number = ?";
                PreparedStatement checkStmt = conn.c.prepareStatement(checkQuery);
                checkStmt.setString(1, email);
                checkStmt.setString(2, phone);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "User already exists with this Email or Phone!");
                } else {
                    String query = "INSERT INTO login (full_name, email, password, phone_number, nationality, gender) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst = conn.c.prepareStatement(query);
                    pst.setString(1, name);
                    pst.setString(2, email);
                    pst.setString(3, password);
                    pst.setString(4, phone);
                    pst.setString(5, nationality);
                    pst.setString(6, gender);

                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "User Registered Successfully!");
                        setVisible(false);
                        new Login();
                    } else {
                        JOptionPane.showMessageDialog(null, "Signup Failed!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error!");
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home(tfName.getText());
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
