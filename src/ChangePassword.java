
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePassword extends JFrame {

    private Dashboard dashboard;

    // COLORS
    private final Color BLUE = new Color(63, 100, 220);
    private final Color HEADER_BLUE = new Color(231, 238, 255);
    private final Color BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT = new Color(38, 46, 60);
    private final Color GRAY = new Color(105, 115, 130);
    private final Color BORDER = new Color(218, 224, 234);

    // COMPONENTS
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ChangePassword(Dashboard dashboard) {

        this.dashboard = dashboard;

        setTitle("Task Management System - Change Password");
        setSize(950, 700);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND);

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(15, 25, 15, 25));
        header.setPreferredSize(new Dimension(950, 90));

        JLabel logoLabel = new JLabel();

        try {
            ImageIcon icon = new ImageIcon(
                    getClass().getResource("/Poornima.jpeg")
            );

            Image image = icon.getImage().getScaledInstance(
                    180, 60, Image.SCALE_SMOOTH
            );

            logoLabel.setIcon(new ImageIcon(image));

        } catch (Exception e) {
            logoLabel.setText("POORNIMA");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
            logoLabel.setForeground(BLUE);
        }

        header.add(logoLabel, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND);

        // CARD
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(500, 470));
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(BORDER, 2, true));
        card.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 35, 8, 35);

        // HEADING
        JLabel heading = new JLabel(
                "Change Password",
                SwingConstants.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 27));
        heading.setForeground(TEXT);

        gbc.gridy = 0;
        gbc.insets = new Insets(25, 35, 5, 35);
        card.add(heading, gbc);

        // SUBTITLE
        JLabel subtitle = new JLabel(
                "Update your account password",
                SwingConstants.CENTER
        );

        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(GRAY);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 35, 20, 35);
        card.add(subtitle, gbc);

        // CURRENT PASSWORD
        gbc.gridy = 2;
        gbc.insets = new Insets(8, 35, 8, 35);
        card.add(createLabel("Current Password"), gbc);

        currentPasswordField = createPasswordField();

        gbc.gridy = 3;
        card.add(currentPasswordField, gbc);

        // NEW PASSWORD
        gbc.gridy = 4;
        card.add(createLabel("New Password"), gbc);

        newPasswordField = createPasswordField();

        gbc.gridy = 5;
        card.add(newPasswordField, gbc);

        // CONFIRM PASSWORD
        gbc.gridy = 6;
        card.add(createLabel("Confirm New Password"), gbc);

        confirmPasswordField = createPasswordField();

        gbc.gridy = 7;
        card.add(confirmPasswordField, gbc);

        // UPDATE BUTTON
        JButton updateButton = new JButton("Update Password");

        updateButton.setPreferredSize(new Dimension(400, 43));
        updateButton.setBackground(BLUE);
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setFocusPainted(false);
        updateButton.setBorderPainted(false);
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 8;
        gbc.insets = new Insets(20, 35, 7, 35);
        card.add(updateButton, gbc);

        // BACK BUTTON
        JButton backButton = new JButton("Back to Dashboard");

        backButton.setFont(new Font("Arial", Font.PLAIN, 13));
        backButton.setForeground(BLUE);
        backButton.setBackground(Color.WHITE);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 9;
        gbc.insets = new Insets(3, 35, 20, 35);
        card.add(backButton, gbc);

        mainPanel.add(card);
        add(mainPanel, BorderLayout.CENTER);

        // FOOTER
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        footer.setPreferredSize(new Dimension(950, 45));
        footer.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel footerLabel = new JLabel(
                "Made by \"Prem Goplani\"",
                SwingConstants.CENTER
        );

        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(GRAY);

        footer.add(footerLabel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        // ACTIONS
        updateButton.addActionListener(e -> updatePassword());
        backButton.addActionListener(e -> goBack());

        // WINDOW CLOSE
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                goBack();
            }
        });
    }

    // LABEL
    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(TEXT);

        return label;
    }

    // PASSWORD FIELD
    private JPasswordField createPasswordField() {

        JPasswordField field = new JPasswordField();

        field.setFont(new Font("Arial", Font.PLAIN, 15));
        field.setPreferredSize(new Dimension(400, 40));
        field.setBorder(new LineBorder(BORDER, 2, true));

        return field;
    }

    // UPDATE PASSWORD USING MYSQL
    private void updatePassword() {

        String currentPassword = new String(
                currentPasswordField.getPassword()
        );

        String newPassword = new String(
                newPasswordField.getPassword()
        );

        String confirmPassword = new String(
                confirmPasswordField.getPassword()
        );

        // CHECK EMPTY FIELDS
        if (currentPassword.isEmpty()
                || newPassword.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // CHECK LOGGED-IN USER
        String username = project1.loggedInUsername;

        if (username == null || username.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to identify the logged-in user. Please log in again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // CHECK PASSWORD LENGTH
        if (newPassword.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "New password must contain at least 6 characters.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // CHECK PASSWORD MATCH
        if (!newPassword.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "New passwords do not match.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // PREVENT SAME PASSWORD
        if (currentPassword.equals(newPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "New password must be different from the current password.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // VERIFY AND UPDATE PASSWORD IN MYSQL
        String selectSQL =
                "SELECT password FROM users WHERE username = ?";

        String updateSQL =
                "UPDATE users SET password = ? WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement selectStatement =
                     connection.prepareStatement(selectSQL)) {

            selectStatement.setString(1, username);

            try (ResultSet resultSet =
                         selectStatement.executeQuery()) {

                // CHECK WHETHER USER EXISTS
                if (!resultSet.next()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "User account not found.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                String savedPassword = resultSet.getString("password");

                // VERIFY CURRENT PASSWORD
                if (!currentPassword.equals(savedPassword)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Current password is incorrect.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }
            }

            // UPDATE PASSWORD
            try (PreparedStatement updateStatement =
                         connection.prepareStatement(updateSQL)) {

                updateStatement.setString(1, newPassword);
                updateStatement.setString(2, username);

                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {

                    // Keep the old variable synchronized
                    project1.currentPassword = newPassword;

                    JOptionPane.showMessageDialog(
                            this,
                            "Password changed successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    goBack();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Password could not be updated.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // BACK TO DASHBOARD
    private void goBack() {

        dispose();

        if (dashboard != null) {
            dashboard.setVisible(true);
        }
    }

    // MAIN
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ChangePassword page = new ChangePassword(null);
            page.setVisible(true);
        });
    }
}
