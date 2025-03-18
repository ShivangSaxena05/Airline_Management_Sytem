package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class CancelTicket extends JFrame implements ActionListener {
    JTextField tfpnr;
    JButton fetch, cancelFlight;
    JLabel cname, cphone, cnationality, lblcode, lblddt, lblsource, lbldestination, cancellationno;

    public CancelTicket() {
        Random rnd = new Random();
        getContentPane().setBackground(new Color(135, 206, 235));
        setLayout(null);

        JLabel head = new JLabel("Cancel Ticket");
        head.setBounds(350, 20, 400, 40);
        head.setFont(new Font("Tahoma", Font.BOLD, 40));
        head.setForeground(new Color(0, 0, 139));
        add(head);

        int xLabel = 50, xField = 220, yStart = 80, gap = 45, widthLabel = 160, widthField = 250, height = 35;

        addLabel("Enter PNR:", xLabel, yStart);
        tfpnr = addTextField(xField, yStart);

        fetch = addButton("Show Details", xField + 270, yStart, 140, height);
        fetch.addActionListener(this);

        cancellationno = addLabelAndField("Cancellation No:", xLabel, yStart + 2 * gap);
        cancellationno.setText("" + rnd.nextInt(100000));

        cname = addLabelAndField("Name:", xLabel, yStart + gap);
        cphone = addLabelAndField("Phone:", xLabel, yStart + 3 * gap);
        cnationality = addLabelAndField("Nationality:", xLabel, yStart + 4 * gap);
        lblsource = addLabelAndField("Source:", xLabel, yStart + 5 * gap);
        lbldestination = addLabelAndField("Destination:", xLabel, yStart + 6 * gap);
        lblcode = addLabelAndField("Flight Code:", xLabel, yStart + 7 * gap);
        lblddt = addLabelAndField("Date of Travel:", xLabel, yStart + 8 * gap);

        cancelFlight = addButton("Cancel Flight", xField + 100, yStart +9 * gap, 160, height);
        cancelFlight.setBackground(new Color(255, 77, 77));
        cancelFlight.addActionListener(this);

        setSize(1000, 600);
        setLocation(300, 100);
        setVisible(true);
    }

    private JLabel addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 180, 35);
        label.setFont(new Font("Tahoma", Font.PLAIN, 18));
        label.setForeground(new Color(0, 51, 153));
        add(label);
        return label;
    }

    private JTextField addTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 250, 35);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(textField);
        return textField;
    }

    private JLabel addLabelAndField(String text, int x, int y) {
        addLabel(text, x, y);
        JLabel label = new JLabel();
        label.setBounds(220, y, 250, 35);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(label);
        return label;
    }

    private JButton addButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(button);
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetch) {
            String pnr = tfpnr.getText();
            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM reservation WHERE PNR='" + pnr + "'";
                ResultSet rs = conn.s.executeQuery(query);
                if (rs.next()) {
                    cname.setText(rs.getString("name"));
                    cphone.setText(rs.getString("phone"));
                    cnationality.setText(rs.getString("nationality"));
                    lblcode.setText(rs.getString("flightcode"));
                    lblddt.setText(rs.getString("ddate"));
                    lblsource.setText(rs.getString("src"));
                    lbldestination.setText(rs.getString("des"));
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid PNR!");
                }
            } catch (Exception ae) {
                ae.printStackTrace();
            }
        } else if (e.getSource() == cancelFlight) {
            String pnr = tfpnr.getText();
            try {
                Conn conn = new Conn();
                String query = "INSERT INTO cancellation VALUES('" + pnr + "', '" + cname.getText() + "', '"
                        + cancellationno.getText() + "', '" + cphone.getText() + "', '" + cnationality.getText() + "', '"
                        + lblsource.getText() + "', '" + lbldestination.getText() + "', '" + lblcode.getText() + "', '"
                        + lblddt.getText() + "')";
                conn.s.executeUpdate(query);
                String deleteQuery = "DELETE FROM reservation WHERE PNR='" + pnr + "'";
                conn.s.executeUpdate(deleteQuery);
                JOptionPane.showMessageDialog(null, "Flight Cancelled Successfully");
                setVisible(false);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new CancelTicket();
    }
}
