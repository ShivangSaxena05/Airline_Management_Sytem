package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JButton submit, reset, close;
    JTextField tusername;
    JPasswordField tpassword;

    public Login() {
        getContentPane().setBackground(new Color(135, 206, 235));
        setLayout(null);

        JLabel lblusername = new JLabel("UserName");
        lblusername.setBounds(20, 20, 100, 20);
        add(lblusername);

        tusername = new JTextField();
        tusername.setBounds(110, 20, 200, 20);
        add(tusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(20, 60, 100, 20);
        add(lblpassword);

        tpassword = new JPasswordField();
        tpassword.setBounds(110, 60, 200, 20);
        add(tpassword);

        reset = new JButton("Reset");
        reset.setBounds(50, 120, 120, 20);
        reset.addActionListener(this);
        add(reset);

        submit = new JButton("Submit");
        submit.setBounds(200, 120, 120, 20);
        submit.addActionListener(this);
        add(submit);

        close = new JButton("Close");
        close.setBounds(120, 160, 120, 20);
        close.addActionListener(this);
        add(close);

        setSize(400, 250);
        setLocation(600, 270);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String username = tusername.getText();
            String password = new String(tpassword.getPassword());

            try {
                Conn c = new Conn();
                String query = "select * from login where username ='" + username + "' and password = '" + password + "'";
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    new Home(username); // Pass the username to Home
                    setVisible(false); // Close login window
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid UserName or Password");
                }
            } catch (Exception ae) {
                ae.printStackTrace();
            }
        } else if (e.getSource() == close) {
            setVisible(false);
        } else if (e.getSource() == reset) {
            tusername.setText("");
            tpassword.setText("");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
