package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class JourneyDetails extends JFrame {

    JTable table;
    JTextField tfpnr;
    JButton showButton;

    public JourneyDetails() {
        setTitle("Journey Details");
        getContentPane().setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel heading = new JLabel("Journey Details", JLabel.CENTER);
        heading.setFont(new Font("Verdana", Font.BOLD, 22));
        heading.setForeground(Color.WHITE);
        headerPanel.add(heading);
        add(headerPanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.DARK_GRAY);
        searchPanel.setLayout(new FlowLayout());

        JLabel lblPNR = new JLabel("Enter PNR:");
        lblPNR.setFont(new Font("Arial", Font.BOLD, 16));
        lblPNR.setForeground(Color.WHITE);
        searchPanel.add(lblPNR);

        tfpnr = new JTextField(15);
        tfpnr.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(tfpnr);

        showButton = new JButton("🔍 Show Details");
        showButton.setFont(new Font("Arial", Font.BOLD, 14));
        showButton.setBackground(new Color(0, 153, 76));
        showButton.setForeground(Color.WHITE);
        showButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        showButton.setFocusPainted(false);
        showButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadJourneyDetails();
            }
        });

        searchPanel.add(showButton);
        add(searchPanel, BorderLayout.CENTER);
        
        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(750, 250));
        add(sp, BorderLayout.SOUTH);

        setSize(1300, 500);
        setLocation(150, 200);
        setVisible(true);
    }

    private void loadJourneyDetails() {
        String pnr = tfpnr.getText().trim();
        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid PNR number.");
            return;
        }

        try {
            Conn conn = new Conn();
            Statement stmt = conn.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM reservation WHERE PNR = '" + pnr + "'");

            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "No information available for this PNR.");
                return;
            }

            rs.last();
            int row = rs.getRow();
            rs.beforeFirst();

            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();

            String[][] tab = new String[row][col];
            int r = 0;

            while (rs.next()) {
                for (int c = 0; c < col; c++) {
                    tab[r][c] = rs.getString(c + 1);
                }
                r++;
            }

            String[] coln = new String[col];
            for (int i = 0; i < col; i++) {
                coln[i] = rsmd.getColumnName(i + 1);
            }

            table.setModel(new javax.swing.table.DefaultTableModel(tab, coln));

            JTableHeader tableHeader = table.getTableHeader();
            tableHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
            tableHeader.setForeground(Color.WHITE);
            tableHeader.setBackground(new Color(0, 102, 204));

            table.setFont(new Font("Arial", Font.PLAIN, 14));
            table.setRowHeight(25);
            table.setBackground(Color.LIGHT_GRAY);
            table.setForeground(Color.BLACK);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            rs.close();
            stmt.close();
            conn.c.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving journey details: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
