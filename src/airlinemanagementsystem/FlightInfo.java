package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FlightInfo extends JFrame {

    JTable table;
    JButton refreshButton;

    public FlightInfo() {
        setTitle("Flight Information");
        getContentPane().setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel heading = new JLabel("Flight Information", JLabel.CENTER);
        heading.setFont(new Font("Verdana", Font.BOLD, 22));
        heading.setForeground(Color.WHITE);
        headerPanel.add(heading);
        add(headerPanel, BorderLayout.NORTH);

        table = new JTable();
        loadFlightData();

        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        refreshButton = new JButton("🔄 Refresh Data");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 16));
        refreshButton.setBackground(new Color(0, 153, 76));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        refreshButton.setFocusPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFlightData();
            }
        });

        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(800, 500);
        setLocation(400, 200);
        setVisible(true);
    }

    private void loadFlightData() {
        try {
            Conn conn = new Conn();
            Statement stmt = conn.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM flight");

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
            JOptionPane.showMessageDialog(this, "Error retrieving flight information: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new FlightInfo();
    }
}
