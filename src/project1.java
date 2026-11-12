
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class project1 extends JFrame {

    // =========================================================
    // SHARED LOGIN INFORMATION
    // =========================================================

    // Kept for compatibility with existing project classes
    public static final String USERNAME = "admin";
    public static String currentPassword = "123456";

    // Stores the username of the currently logged-in user
    public static String loggedInUsername = "";


    // =========================================================
    // COLORS
    // =========================================================

    private final Color BLUE = new Color(63, 100, 220);
    private final Color HEADER_BLUE = new Color(231, 238, 255);
    private final Color BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT = new Color(38, 46, 60);
    private final Color GRAY = new Color(105, 115, 130);
    private final Color BORDER = new Color(218, 224, 234);


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public project1() {

        setTitle("Task Management System");

        setSize(950, 650);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(true);

        setLocationRelativeTo(null);

        getContentPane().setBackground(BACKGROUND);

        setLayout(new BorderLayout());


        // =====================================================
        // HEADER
        // =====================================================

        JPanel header = new JPanel(new GridBagLayout());

        header.setBackground(HEADER_BLUE);

        header.setPreferredSize(new Dimension(0, 110));


        // =====================================================
        // LOGO
        // =====================================================

        try {

            ImageIcon originalIcon =
                    new ImageIcon(
                            getClass().getResource("/Poornima.jpeg")
                    );

            Image originalImage = originalIcon.getImage();

            int originalWidth = originalIcon.getIconWidth();

            int originalHeight = originalIcon.getIconHeight();

            int logoWidth = 170;

            int logoHeight =
                    (originalHeight * logoWidth) / originalWidth;

            Image resizedImage =
                    originalImage.getScaledInstance(
                            logoWidth,
                            logoHeight,
                            Image.SCALE_SMOOTH
                    );

            ImageIcon logoIcon = new ImageIcon(resizedImage);

            JLabel logoLabel = new JLabel(logoIcon);

            GridBagConstraints logoGbc =
                    new GridBagConstraints();

            logoGbc.gridx = 0;
            logoGbc.gridy = 0;

            logoGbc.anchor = GridBagConstraints.CENTER;

            header.add(logoLabel, logoGbc);

        } catch (Exception e) {

            JLabel logoError = new JLabel("Poornima");

            logoError.setFont(
                    new Font("Arial", Font.BOLD, 22)
            );

            logoError.setForeground(BLUE);

            header.add(logoError);
        }

        add(header, BorderLayout.NORTH);


        // =====================================================
        // CENTER AREA
        // =====================================================

        JPanel centerPanel =
                new JPanel(new GridBagLayout());

        centerPanel.setBackground(BACKGROUND);


        // =====================================================
        // LOGIN CARD
        // =====================================================

        JPanel loginCard = new JPanel();

        loginCard.setBackground(Color.WHITE);

        loginCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(35, 55, 35, 55)
                )
        );

        loginCard.setPreferredSize(
                new Dimension(430, 430)
        );

        loginCard.setMinimumSize(
                new Dimension(430, 430)
        );

        loginCard.setMaximumSize(
                new Dimension(430, 430)
        );

        loginCard.setLayout(
                new BoxLayout(
                        loginCard,
                        BoxLayout.Y_AXIS
                )
        );


        // =====================================================
        // TITLE
        // =====================================================

        JLabel titleLabel = new JLabel("Welcome Back");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        titleLabel.setForeground(TEXT);

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(titleLabel);

        loginCard.add(Box.createVerticalStrut(8));


        // =====================================================
        // SUBTITLE
        // =====================================================

        JLabel subtitleLabel =
                new JLabel("Login to your account");

        subtitleLabel.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        subtitleLabel.setForeground(GRAY);

        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(subtitleLabel);

        loginCard.add(Box.createVerticalStrut(30));


        // =====================================================
        // USERNAME LABEL
        // =====================================================

        JLabel usernameLabel = new JLabel("Username");

        usernameLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        usernameLabel.setForeground(TEXT);

        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(usernameLabel);

        loginCard.add(Box.createVerticalStrut(7));


        // =====================================================
        // USERNAME FIELD
        // =====================================================

        JTextField usernameField = new JTextField();

        usernameField.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        usernameField.setForeground(TEXT);

        usernameField.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(10, 12, 10, 12)
                )
        );

        usernameField.setPreferredSize(
                new Dimension(300, 45)
        );

        usernameField.setMinimumSize(
                new Dimension(300, 45)
        );

        usernameField.setMaximumSize(
                new Dimension(300, 45)
        );

        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(usernameField);

        loginCard.add(Box.createVerticalStrut(18));


        // =====================================================
        // PASSWORD LABEL
        // =====================================================

        JLabel passwordLabel = new JLabel("Password");

        passwordLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        passwordLabel.setForeground(TEXT);

        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(passwordLabel);

        loginCard.add(Box.createVerticalStrut(7));


        // =====================================================
        // PASSWORD FIELD
        // =====================================================

        JPasswordField passwordField = new JPasswordField();

        passwordField.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        passwordField.setForeground(TEXT);

        passwordField.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(10, 12, 10, 12)
                )
        );

        passwordField.setPreferredSize(
                new Dimension(300, 45)
        );

        passwordField.setMinimumSize(
                new Dimension(300, 45)
        );

        passwordField.setMaximumSize(
                new Dimension(300, 45)
        );

        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(passwordField);

        loginCard.add(Box.createVerticalStrut(25));


        // =====================================================
        // LOGIN BUTTON
        // =====================================================

        JButton loginButton = new JButton("Login");

        styleButton(loginButton);

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(loginButton);

        loginCard.add(Box.createVerticalStrut(12));


        // =====================================================
        // FORGOT PASSWORD
        // =====================================================

        JButton forgotButton =
                new JButton("Forgot Password?");

        forgotButton.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        forgotButton.setForeground(BLUE);

        forgotButton.setBackground(Color.WHITE);

        forgotButton.setBorderPainted(false);

        forgotButton.setFocusPainted(false);

        forgotButton.setContentAreaFilled(false);

        forgotButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        forgotButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(forgotButton);


        // =====================================================
        // ADD LOGIN CARD TO CENTER
        // =====================================================

        GridBagConstraints cardGbc =
                new GridBagConstraints();

        cardGbc.gridx = 0;
        cardGbc.gridy = 0;

        cardGbc.anchor = GridBagConstraints.CENTER;

        centerPanel.add(loginCard, cardGbc);

        add(centerPanel, BorderLayout.CENTER);


        // =====================================================
        // FOOTER
        // =====================================================

        JPanel footer =
                new JPanel(new GridBagLayout());

        footer.setBackground(HEADER_BLUE);

        footer.setPreferredSize(
                new Dimension(0, 55)
        );

        JLabel footerLabel =
                new JLabel("Made by \"Prem Goplani\"");

        footerLabel.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        footerLabel.setForeground(GRAY);

        footer.add(footerLabel);

        add(footer, BorderLayout.SOUTH);


        // =====================================================
        // LOGIN ACTION - MYSQL DATABASE
        // =====================================================

        loginButton.addActionListener(e -> {

            String username =
                    usernameField.getText().trim();

            String password =
                    new String(passwordField.getPassword());


            // CHECK EMPTY FIELDS

            if (username.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter username and password.",
                        "Login Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            // SQL QUERY

            String sql =
                    "SELECT password FROM users WHERE username = ?";


            // DATABASE CONNECTION

            try (Connection connection =
                         DBConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {


                statement.setString(1, username);


                // EXECUTE QUERY

                try (ResultSet resultSet =
                             statement.executeQuery()) {


                    // CHECK USERNAME

                    if (resultSet.next()) {

                        String savedPassword =
                                resultSet.getString("password");


                        // VERIFY PASSWORD

                        if (password.equals(savedPassword)) {


                            // STORE LOGGED-IN USERNAME

                            loggedInUsername = username;

                            currentPassword = savedPassword;


                            JOptionPane.showMessageDialog(
                                    this,
                                    "Login successful!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );


                            // OPEN DASHBOARD

                            setVisible(false);

                            Dashboard dashboard =
                                    new Dashboard();

                            dashboard.setVisible(true);


                        } else {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Invalid username or password.",
                                    "Login Failed",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Invalid username or password.",
                                "Login Failed",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database connection error:\n" + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }

        });


        // =====================================================
        // ENTER KEY
        // =====================================================

        passwordField.addActionListener(
                e -> loginButton.doClick()
        );


        // =====================================================
        // FORGOT PASSWORD ACTION
        // =====================================================

        forgotButton.addActionListener(e -> {

            setVisible(false);

            ForgotPassword page =
                    new ForgotPassword(this);

            page.setVisible(true);

        });


        // =====================================================
        // SHOW WINDOW
        // =====================================================

        setVisible(true);
    }


    // =========================================================
    // BUTTON DESIGN
    // =========================================================

    private void styleButton(JButton button) {

        button.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        button.setForeground(Color.WHITE);

        button.setBackground(BLUE);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BLUE.darker(), 1, true),
                        new EmptyBorder(10, 30, 10, 30)
                )
        );

        button.setPreferredSize(
                new Dimension(300, 48)
        );

        button.setMinimumSize(
                new Dimension(300, 48)
        );

        button.setMaximumSize(
                new Dimension(300, 48)
        );

        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }


    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new project1();

        });

    }
}
