package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {
    private JButton log;
    private JButton sign;
    private JButton logout;
    private JLabel userLabel;
    private boolean isLoggedIn = false;
    private String username;

    public Home(String username) {
        this.username = username;
        this.isLoggedIn = (username != null && !username.isEmpty());

        setLayout(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        ImageIcon il = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/ICONS/airplane.jpg"));
        Image img = il.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel image = new JLabel(scaledIcon);
        image.setBounds(0, 0, width, height);
        add(image);
        
        JLabel head = new JLabel("Welcome To Shiva Airlines") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                FontMetrics fm = g2d.getFontMetrics();
                int x = 0;
                int y = fm.getAscent();
                
                g2d.setColor(Color.BLACK); // Stroke Color
                g2d.setStroke(new BasicStroke(5)); // Stroke Thickness
                g2d.drawString(getText(), x + 2, y + 2); // Draw Shadow/Stroke
                
                g2d.setColor(Color.YELLOW); // Original Text Color
                g2d.drawString(getText(), x, y); // Draw Original Text
            }
        };
        head.setFont(new Font("Tahoma", Font.BOLD, 40));
        head.setBounds(width / 3, 100, 600, 50);
        image.add(head);

        // Add Subheading (Tagline)
        JLabel tagline = new JLabel("Your Trust, Our Wings!") {
        	@Override
        	protected void paintComponent(Graphics gg) {
        		super.paintComponent(gg);
        		Graphics2D g2dd = (Graphics2D) gg;
        		g2dd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        		
        		FontMetrics fm = g2dd.getFontMetrics();
        		int x = 0;
        		int y = fm.getAscent();
        		g2dd.setColor(Color.black);
        		g2dd.setStroke(new BasicStroke(5));
        		g2dd.drawString(getText(), x+2, y+2);
        		
        		g2dd.setColor(Color.white);
        		g2dd.drawString(getText(), x, y);
        	}
        };
        tagline.setBounds(width / 3 + 50, 160, 600, 50);
        tagline.setFont(new Font("Tahoma", Font.BOLD, 30));
        image.add(tagline);


        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(new Color(95, 169, 201));
        setJMenuBar(menubar);

        menubar.add(new JMenu("Home"));

        JMenu details = new JMenu("Flight Info");
        menubar.add(details);

        JMenuItem flightdet = new JMenuItem("View Flights");
        flightdet.addActionListener(this);
        JMenuItem pasdet = new JMenuItem("View Passengers");
        pasdet.addActionListener(this);
        JMenuItem jrndet = new JMenuItem("View Journeys");
        jrndet.addActionListener(this);
        JMenuItem addCustomer = new JMenuItem("Add Customer");
        addCustomer.addActionListener(this);

        details.add(flightdet);
        details.add(pasdet);
        details.add(jrndet);
        details.add(addCustomer);

        JMenu bookings = new JMenu("Bookings");
        menubar.add(bookings);
        JMenuItem bookflt = new JMenuItem("Book a Flight");
        bookings.add(bookflt);
        JMenuItem cnclbook = new JMenuItem("Cancel Booking");
        bookings.add(cnclbook);

        bookflt.addActionListener(this);
        cnclbook.addActionListener(this);

        JMenu ticket = new JMenu("Tickets");
        menubar.add(ticket);
        JMenuItem boardingpass = new JMenuItem("Boarding Pass");
        boardingpass.addActionListener(this);
        ticket.add(boardingpass);

        menubar.add(Box.createHorizontalGlue());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        userLabel = new JLabel();
        userLabel.setForeground(Color.BLACK);
        userLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        log = new JButton("Login");
        sign = new JButton("Signup");
        logout = new JButton("Logout");
        styleButton(log);
        styleButton(sign);
        styleButton(logout);

        buttonPanel.add(userLabel);

        if (isLoggedIn) {
            userLabel.setText("Welcome, " + username);
            log.setVisible(false);
            sign.setVisible(false);
            buttonPanel.add(logout);
        } else {
            userLabel.setText("Welcome, Guest");
            buttonPanel.add(log);
            buttonPanel.add(sign);
            logout.setVisible(false);
        }

        menubar.add(buttonPanel);

        log.addActionListener(this);
        sign.addActionListener(this);
        logout.addActionListener(this);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setMargin(new Insets(15, 25, 15, 25));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = e.getActionCommand();

        if (e.getSource() == log) {
            new Login();
            setVisible(false);
        } else if (e.getSource() == logout) {
            new Home("");
            setVisible(false);
        } else if (text.equals("View Flights")) {
            if (isLoggedIn) {
                new FlightInfo();
            } else {
                JOptionPane.showMessageDialog(null, "Please login first to view flight details.");
            }
        } else if (text.equals("Add Customer")) {
            if (isLoggedIn) {
                new Addcustomer();
            } else {
                JOptionPane.showMessageDialog(null, "Please login first to add a customer.");
            }
        } else if (text.equals("View Journeys")) {
            if (isLoggedIn) {
                new JourneyDetails();
            } else {
                JOptionPane.showMessageDialog(null, "Please login first to view journeys.");
            }
        } else if (text.equals("Book a Flight")) {
            if (isLoggedIn) {
                new BookFlight();
            } else {
                JOptionPane.showMessageDialog(null, "Please login first to book a flight.");
            }
        } else if (text.equals("View Passengers")) {
            if (isLoggedIn) {
                new passenger();
            } else {
                JOptionPane.showMessageDialog(null, "Please login first to view passenger details.");
            }
        } else if (text.equals("Cancel Booking")) {
            if (isLoggedIn) {
                new CancelTicket();
            } else {
                JOptionPane.showMessageDialog(null, "Please login first to cancel a booking.");
            }
        } else if (text.equals("Boarding Pass")) {
            if (isLoggedIn) {
                new BoardingPass();
            } else {
                JOptionPane.showMessageDialog(null, "Please login first to view the boarding pass.");
            }
        }
    }

    public static void main(String[] args) {
        new Home("");
    }
}
