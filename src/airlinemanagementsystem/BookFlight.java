package airlinemanagementsystem;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import com.toedter.calendar.*;

public class BookFlight extends JFrame implements ActionListener {

    JTextField tfaadhar;
    JButton bookflight, flight, fetch;
    JLabel cname, cphone, cnationality, caddress, lbgender, lblfname, lblfcode;
    Choice source, destt;
    JDateChooser jdate;

    public BookFlight() {
        getContentPane().setBackground(new Color(135, 206, 235));
        setLayout(null);

        JLabel head = new JLabel("Book Flight");
        head.setBounds(350, 20, 400, 40);
        head.setFont(new Font("Tahoma", Font.BOLD, 36));
        head.setForeground(new Color(0, 51, 102));
        add(head);

        int xLabel = 50, xField = 220, yStart = 80, gap = 40, widthLabel = 160, widthField = 250, height = 30;

        addLabel("Aadhar Number:", xLabel, yStart);
        tfaadhar = addTextField(xField, yStart);

        fetch = addButton("Get Details", xField + 270, yStart, 120, height);
        fetch.addActionListener(this);

        cname = addLabelAndField("Enter Your Name:", xLabel, yStart + gap);
        cnationality = addLabelAndField("Nationality:", xLabel, yStart + 2 * gap);
        caddress = addLabelAndField("Address:", xLabel, yStart + 3 * gap);
        lbgender = addLabelAndField("Gender:", xLabel, yStart + 4 * gap);
        cphone = addLabelAndField("Phone Number:", xLabel, yStart + 5 * gap);

        addLabel("Source:", xLabel, yStart + 6 * gap);
        source = new Choice();
        source.setBounds(xField, yStart + 6 * gap, widthField, height);
        add(source);

        addLabel("Destination:", xLabel, yStart + 7 * gap);
        destt = new Choice();
        destt.setBounds(xField, yStart + 7 * gap, widthField, height);
        add(destt);

        loadFlightData();

        flight = addButton("Check Flights", xField + 270, yStart + 7 * gap, 150, height);
        flight.addActionListener(this);

        lblfname = addLabelAndField("Flight Name:", xLabel, yStart + 8 * gap);
        lblfcode = addLabelAndField("Flight Code:", xLabel, yStart + 9 * gap);

        addLabel("Date of Travel:", xLabel, yStart + 10 * gap);
        jdate = new JDateChooser();
        jdate.setBounds(xField, yStart + 10 * gap, widthField, height);
        jdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(jdate);

        bookflight = addButton("Book Flight", 350, yStart + 12 * gap, 150, height);
        bookflight.addActionListener(this);

        setSize(1000, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 160, 30);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setForeground(new Color(0, 51, 102));
        add(label);
    }

    private JTextField addTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 250, 30);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(textField);
        return textField;
    }

    private JLabel addLabelAndField(String text, int x, int y) {
        addLabel(text, x, y);
        JLabel label = new JLabel();
        label.setBounds(220, y, 250, 30);
        label.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        add(label);
        return label;
    }

    private JButton addButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(button);
        return button;
    }

    private void loadFlightData() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM flight");
            while (rs.next()) {
                source.add(rs.getString("source"));
                destt.add(rs.getString("destination"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetch) {
            fetchDetails();
        } else if (e.getSource() == flight) {
            checkFlights();
        } else if (e.getSource() == bookflight) {
            bookFlight();
        }
    }

    private void fetchDetails() {
        String aadhar = tfaadhar.getText().trim();
        if (aadhar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Aadhar number.");
            return;
        }
        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM passenger WHERE aadhar='" + aadhar + "'";
            ResultSet rs = conn.s.executeQuery(query);
            if (rs.next()) {
                cname.setText(rs.getString("name"));
                cphone.setText(rs.getString("phone"));
                cnationality.setText(rs.getString("nationality"));
                caddress.setText(rs.getString("address"));
                lbgender.setText(rs.getString("gender"));
            } else {
                JOptionPane.showMessageDialog(null, "No passenger found with this Aadhar.");
            }
        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }

    private void checkFlights() {
        String src = source.getSelectedItem();
        String dest = destt.getSelectedItem();
        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM flight WHERE source='" + src + "' AND destination='" + dest + "'";
            ResultSet rs = conn.s.executeQuery(query);
            if (rs.next()) {
                lblfname.setText(rs.getString("f_name"));
                lblfcode.setText(rs.getString("f_code"));
            } else {
                lblfname.setText("");
                lblfcode.setText("");
                JOptionPane.showMessageDialog(null, "No flights found between selected locations.");
            }
        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }

    private void bookFlight() {
        Random rndm = new Random();
        String aadhar = tfaadhar.getText().trim();
        String name = cname.getText().trim();
        String phone = cphone.getText().trim();
        String nationality = cnationality.getText().trim();
        String address = caddress.getText().trim();
        String gender = lbgender.getText().trim();
        String fname = lblfname.getText().trim();
        String fcode = lblfcode.getText().trim();
        String src = source.getSelectedItem();
        String dest = destt.getSelectedItem();
        String ddate = ((JTextField) jdate.getDateEditor().getUiComponent()).getText();

        if (aadhar.isEmpty() || name.isEmpty() || fname.isEmpty() || ddate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all required details.");
            return;
        }

        try {
            Conn conn = new Conn();
            String query = "INSERT INTO reservation VALUES('PNR - " + rndm.nextInt(1000000) + "','TIC-" + rndm.nextInt(10000) + "','" + aadhar + "','" + name + "','" + phone + "','" + nationality + "','" + gender + "','" + fname + "','" + fcode + "','" + src + "','" + dest + "','" + ddate + "')";
            conn.s.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Ticket Booked Successfully!");
            setVisible(false);
        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BookFlight();
    }
}
