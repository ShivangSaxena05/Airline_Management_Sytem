package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class BoardingPass extends JFrame implements ActionListener {

    JTextField tfpnr;
    JButton enter;
    JLabel cname, cphone, ldate, cnationality, lbgender, lblfname, lblfcode, source, destt;

    public BoardingPass() {
        setTitle("Boarding Pass - Shiva Airlines");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JPanel ticketPanel = new JPanel();
        ticketPanel.setBackground(new Color(240, 248, 255));
        ticketPanel.setBounds(50, 50, 1000, 300);
        ticketPanel.setLayout(null);
        ticketPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        add(ticketPanel);

        JLabel head = new JLabel("SHIVA AIRLINES", SwingConstants.CENTER);
        head.setBounds(300, 10, 300, 35);
        head.setFont(new Font("Tahoma", Font.BOLD, 28));
        head.setForeground(Color.BLUE);
        ticketPanel.add(head);

        JLabel lblPNR = new JLabel("Enter PNR:");
        lblPNR.setFont(new Font("Arial", Font.BOLD, 16));
        lblPNR.setBounds(50, 60, 100, 30);
        ticketPanel.add(lblPNR);

        tfpnr = new JTextField(15);
        tfpnr.setFont(new Font("Arial", Font.PLAIN, 14));
        tfpnr.setBounds(150, 60, 200, 30);
        ticketPanel.add(tfpnr);

        enter = new JButton("Enter");
        enter.setBounds(370, 60, 100, 30);
        enter.setBackground(Color.BLUE);
        enter.setForeground(Color.WHITE);
        enter.addActionListener(this);
        ticketPanel.add(enter);

        JLabel lblname = new JLabel("Passenger Name:");
        lblname.setBounds(50, 110, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ticketPanel.add(lblname);

        cname = new JLabel();
        cname.setBounds(200, 110, 200, 25);
        ticketPanel.add(cname);

        JLabel lblnationality = new JLabel("Nationality:");
        lblnationality.setBounds(450, 110, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ticketPanel.add(lblnationality);

        cnationality = new JLabel();
        cnationality.setBounds(600, 110, 200, 25);
        ticketPanel.add(cnationality);

        JLabel lblsource = new JLabel("From:");
        lblsource.setBounds(50, 150, 150, 25);
        lblsource.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ticketPanel.add(lblsource);

        source = new JLabel();
        source.setBounds(150, 150, 200, 25);
        ticketPanel.add(source);

        JLabel lbldest = new JLabel("To:");
        lbldest.setBounds(450, 150, 150, 25);
        lbldest.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ticketPanel.add(lbldest);

        destt = new JLabel();
        destt.setBounds(550, 150, 200, 25);
        ticketPanel.add(destt);

        JLabel lblgender = new JLabel("Gender:");
        lblgender.setBounds(50, 190, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ticketPanel.add(lblgender);

        lbgender = new JLabel();
        lbgender.setBounds(150, 190, 200, 25);
        ticketPanel.add(lbgender);

        JLabel lblphone = new JLabel("Phone:");
        lblphone.setBounds(450, 190, 150, 25);
        lblphone.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ticketPanel.add(lblphone);

        cphone = new JLabel();
        cphone.setBounds(550, 190, 200, 25);
        ticketPanel.add(cphone);

        JLabel lblfname = new JLabel("Flight:");
        lblfname.setBounds(50, 230, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ticketPanel.add(lblfname);

        this.lblfname = new JLabel();
        this.lblfname.setBounds(150, 230, 200, 25);
        ticketPanel.add(this.lblfname);

        JLabel lblfcode = new JLabel("Flight Code:");
        lblfcode.setBounds(450, 230, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ticketPanel.add(lblfcode);

        this.lblfcode = new JLabel();
        this.lblfcode.setBounds(600, 230, 200, 25);
        ticketPanel.add(this.lblfcode);

        JLabel lbldate = new JLabel("Travel Date:");
        lbldate.setBounds(50, 270, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ticketPanel.add(lbldate);

        ldate = new JLabel();
        ldate.setBounds(150, 270, 200, 25);
        ticketPanel.add(ldate);

        setSize(1100, 450);
        setLocation(300, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enter) {
            String pnr = tfpnr.getText();

            try {
                Conn conn = new Conn();
                String query = "SELECT * from reservation WHERE PNR='" + pnr + "'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    cname.setText(rs.getString("name"));
                    cphone.setText(rs.getString("phone"));
                    lbgender.setText(rs.getString("gender"));
                    cnationality.setText(rs.getString("nationality"));
                    lblfcode.setText(rs.getString("flightcode"));
                    lblfname.setText(rs.getString("flightname"));
                    ldate.setText(rs.getString("ddate"));
                    source.setText(rs.getString("src"));
                    destt.setText(rs.getString("des"));
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid PNR. Please try again.");
                }
            } catch (Exception ae) {
                ae.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new BoardingPass();
    }
}
