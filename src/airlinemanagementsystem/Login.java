package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JTextField tfEmail;
    JPasswordField pfPassword;
    JButton login, back;

    public Login() {
        setTitle("Airline Management System - Login");
        setLayout(null);
        getContentPane().setBackground(new Color(173, 216, 230));

        JLabel heading = new JLabel("Login to Shiva Airlines");
        heading.setBounds(150, 20, 400, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setForeground(Color.BLUE);
        add(heading);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 80, 100, 25);
        add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(160, 80, 200, 25);
        add(tfEmail);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 120, 100, 25);
        add(lblPassword);

        pfPassword = new JPasswordField();
        pfPassword.setBounds(160, 120, 200, 25);
        add(pfPassword);

        login = new JButton("Login");
        login.setBounds(100, 180, 100, 30);
        login.setBackground(Color.BLUE);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        back = new JButton("Back");
        back.setBounds(250, 180, 100, 30);
        back.setBackground(Color.GRAY);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        setSize(500, 300);
        setLocation(500, 250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String email = tfEmail.getText();
            String password = new String(pfPassword.getPassword());

            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM login WHERE email = ? AND password = ?";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setString(1, email);
                pst.setString(2, password);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    setVisible(false);
                    new Home(rs.getString("full_name"));
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error!");
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home("");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
