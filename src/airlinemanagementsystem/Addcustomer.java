package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Addcustomer extends JFrame implements ActionListener {

    JTextField tname, tfnationality, tfaadhar, tfAddress, tfphone;
    JRadioButton btmale, btfemale;
    JButton submit;

    public Addcustomer() {
        getContentPane().setBackground(new Color(135, 206, 235));
        setLayout(null);

        JLabel head = new JLabel("Enter Customer Details");
        head.setBounds(220, 20, 500, 40);
        head.setFont(new Font("Tahoma", Font.BOLD, 35));
        head.setForeground(new Color(0, 51, 102));
        add(head);

        addLabel("Enter Customer Name:", 60, 80);
        tname = addTextField(280, 80);

        addLabel("Enter Your Nationality:", 60, 130);
        tfnationality = addTextField(280, 130);

        addLabel("Enter Your Aadhar Number:", 60, 180);
        tfaadhar = addTextField(280, 180);

        addLabel("Enter Your Address:", 60, 230);
        tfAddress = addTextField(280, 230);

        addLabel("Gender:", 60, 280);
        btmale = new JRadioButton("Male");
        btmale.setBounds(280, 280, 70, 25);
        btmale.setBackground(new Color(135, 206, 235));
        btmale.setFont(new Font("Arial", Font.PLAIN, 14));
        add(btmale);

        btfemale = new JRadioButton("Female");
        btfemale.setBounds(360, 280, 80, 25);
        btfemale.setBackground(new Color(135, 206, 235));
        btfemale.setFont(new Font("Arial", Font.PLAIN, 14));
        add(btfemale);

        ButtonGroup gendergrp = new ButtonGroup();
        gendergrp.add(btmale);
        gendergrp.add(btfemale);

        addLabel("Enter Your Phone Number:", 60, 330);
        tfphone = addTextField(280, 330);

        submit = new JButton("Submit");
        submit.setBounds(350, 400, 120, 35);
        submit.setBackground(new Color(0, 102, 204));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Tahoma", Font.BOLD, 17));
        submit.addActionListener(this);
        add(submit);

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/ICONS/people.png"));
        Image image = img.getImage().getScaledInstance(250, 450, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(600, 70, 250, 450);
        add(imageLabel);

        setSize(900, 600);
        setLocation(300, 150);
        setVisible(true);
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 250, 25);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setForeground(new Color(0, 51, 102));
        add(label);
    }

    private JTextField addTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 250, 25);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(textField);
        return textField;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String name = tname.getText().trim();
            String nationality = tfnationality.getText().trim();
            String phone = tfphone.getText().trim();
            String aadhar = tfaadhar.getText().trim();
            String address = tfAddress.getText().trim();
            String gender = btmale.isSelected() ? "Male" : (btfemale.isSelected() ? "Female" : "");

            if (name.isEmpty() || nationality.isEmpty() || phone.isEmpty() || aadhar.isEmpty() || address.isEmpty() || gender.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all the details!");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "INSERT INTO passenger VALUES('" + name + "','" + nationality + "','" + phone + "','" + address + "','" + aadhar + "','" + gender + "')";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
                setVisible(false);
            } catch (Exception ae) {
                ae.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding customer details!");
            }
        }
    }

    public static void main(String[] args) {
        new Addcustomer();
    }
}
