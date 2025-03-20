package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Passenger extends JFrame implements ActionListener {
    JTextField tfaadhar;
    JButton fetch;
    JLabel cname, cnationality, cphone, caddress, cgender;

    public Passenger() {
        getContentPane().setBackground(new Color(135, 206, 235));
        setLayout(null);

        JLabel head = new JLabel("Passenger Details");
        head.setFont(new Font("Tahoma", Font.BOLD, 30));
        head.setForeground(new Color(0, 51, 102));
        head.setBounds(180, 20, 400, 40);
        add(head);

        JLabel lblAadhar = new JLabel("Enter Aadhar:");
        lblAadhar.setFont(new Font("Arial", Font.BOLD, 18));
        lblAadhar.setForeground(new Color(0, 51, 102));
        lblAadhar.setBounds(50, 80, 150, 30);
        add(lblAadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(200, 80, 200, 30);
        tfaadhar.setFont(new Font("Arial", Font.PLAIN, 16));
        add(tfaadhar);

        fetch = new JButton("Fetch Details");
        fetch.setBounds(420, 80, 150, 30);
        fetch.setBackground(new Color(0, 102, 204));
        fetch.setForeground(Color.WHITE);
        fetch.addActionListener(this);
        fetch.setFont(new Font("Arial", Font.BOLD, 16));
        add(fetch);

        int xLabel = 50, xField = 200, yStart = 140, gap = 40, widthLabel = 150, widthField = 250, height = 30;

        addLabel("Name:", xLabel, yStart, widthLabel, height, new Color(0, 51, 102));
        cname = addAnswerLabel(xField, yStart, widthField, height);

        addLabel("Nationality:", xLabel, yStart + gap, widthLabel, height, new Color(0, 51, 102));
        cnationality = addAnswerLabel(xField, yStart + gap, widthField, height);

        addLabel("Phone:", xLabel, yStart + 2 * gap, widthLabel, height, new Color(0, 51, 102));
        cphone = addAnswerLabel(xField, yStart + 2 * gap, widthField, height);

        addLabel("Address:", xLabel, yStart + 3 * gap, widthLabel, height, new Color(0, 51, 102));
        caddress = addAnswerLabel(xField, yStart + 3 * gap, widthField, height);

        addLabel("Gender:", xLabel, yStart + 4 * gap, widthLabel, height, new Color(0, 51, 102));
        cgender = addAnswerLabel(xField, yStart + 4 * gap, widthField, height);

        setSize(650, 450);
        setLocation(400, 200);
        setVisible(true);
    }

    private void addLabel(String text, int x, int y, int width, int height, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(color);
        label.setBounds(x, y, width, height);
        add(label);
    }

    private JLabel addAnswerLabel(int x, int y, int width, int height) {
        JLabel label = new JLabel();
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        add(label);
        return label;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetch) {
            String aadhar = tfaadhar.getText().trim();
            if (aadhar.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Aadhar number!");
                return;
            }
            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM passenger WHERE aadhar='" + aadhar + "'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    cname.setText(rs.getString("name"));
                    cnationality.setText(rs.getString("nationality"));
                    cphone.setText(rs.getString("phone"));
                    caddress.setText(rs.getString("address"));
                    cgender.setText(rs.getString("gender"));
                } else {
                    JOptionPane.showMessageDialog(null, "No passenger found with this Aadhar.");
                    clearFields();
                }
            } catch (Exception ae) {
                ae.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching details.");
            }
        }
    }

    private void clearFields() {
        cname.setText("");
        cnationality.setText("");
        cphone.setText("");
        caddress.setText("");
        cgender.setText("");
    }

    public static void main(String[] args) {
        new Passenger();
    }
}
