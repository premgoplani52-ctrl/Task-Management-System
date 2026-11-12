
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPassword extends JFrame {

    private project1 loginPage;

    // COLORS
    private final Color BLUE = new Color(63, 100, 220);
    private final Color HEADER_BLUE = new Color(231, 238, 255);
    private final Color BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT = new Color(38, 46, 60);
    private final Color GRAY = new Color(105, 115, 130);
    private final Color BORDER = new Color(218, 224, 234);

    public ForgotPassword(project1 loginPage) {

        this.loginPage = loginPage;

        setTitle("Forgot Password");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());

        // HEADER
        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(HEADER_BLUE);
        header.setPreferredSize(new Dimension(950, 85));

        JLabel logoLabel = new JLabel();

        try {
            ImageIcon originalIcon = new ImageIcon(
                    getClass().getResource("/Poornima.jpeg")
            );

            Image image = originalIcon.getImage();
            Image scaledImage = image.getScaledInstance(
                    180, 60, Image.SCALE_SMOOTH
            );

            logoLabel.setIcon(new ImageIcon(scaledImage));

        } catch (Exception e) {
            logoLabel.setText("POORNIMA");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
            logoLabel.setForeground(BLUE);
        }

        header.add(logoLabel);
        add(header, BorderLayout.NORTH);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND);

        // CARD
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(500, 540));
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(BORDER, 2, true));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(Box.createVerticalStrut(25));

        // HEADING
        JLabel heading = new JLabel("Forgot Password");
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(heading);

        card.add(Box.createVerticalStrut(7));

        // SUBTITLE
        JLabel subtitle = new JLabel("Reset your account password");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(subtitle);

        card.add(Box.createVerticalStrut(20));

        // USERNAME
        addLabel(card, "Username");

        JTextField usernameField = createTextField();
        card.add(usernameField);

        card.add(Box.createVerticalStrut(13));

        // FAVORITE COLOR
        addLabel(card, "Favorite Color (Security Answer)");

        JTextField favoriteColorField = createTextField();
        card.add(favoriteColorField);

        card.add(Box.createVerticalStrut(13));

        // NEW PASSWORD
        addLabel(card, "New Password");

        JPasswordField newPasswordField = createPasswordField();
        card.add(newPasswordField);

        card.add(Box.createVerticalStrut(13));

        // CONFIRM PASSWORD
        addLabel(card, "Confirm Password");

        JPasswordField confirmPasswordField = createPasswordField();
        card.add(confirmPasswordField);

        card.add(Box.createVerticalStrut(22));

        // RESET BUTTON
        JButton resetButton = new JButton("Reset Password");
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(BLUE);
        resetButton.setFocusPainted(false);
        resetButton.setBorderPainted(false);
        resetButton.setMaximumSize(new Dimension(380, 42));
        resetButton.setPreferredSize(new Dimension(380, 42));
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(resetButton);

        card.add(Box.createVerticalStrut(8));

        // BACK BUTTON
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setForeground(BLUE);
        backButton.setBackground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(backButton);

        // ADD CARD
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(card, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // RESET PASSWORD ACTION
        resetButton.addActionListener(e -> {

            String username = usernameField.getText().trim();

            String favoriteColor =
                    favoriteColorField.getText().trim();

            String newPassword =
                    new String(newPasswordField.getPassword());

            String confirmPassword =
                    new String(confirmPasswordField.getPassword());

            // CHECK EMPTY FIELDS
            if (username.isEmpty()
                    || favoriteColor.isEmpty()
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

            // CHECK PASSWORD LENGTH
            if (newPassword.length() < 6) {

                JOptionPane.showMessageDialog(
                        this,
                        "Password must contain at least 6 characters.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // CHECK PASSWORD MATCH
            if (!newPassword.equals(confirmPassword)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Passwords do not match.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // VERIFY USER AND SECURITY ANSWER
            String verifySQL =
                    "SELECT user_id FROM users " +
                            "WHERE username = ? " +
                            "AND LOWER(TRIM(favorite_color)) = LOWER(TRIM(?))";

            // UPDATE PASSWORD
            String updateSQL =
                    "UPDATE users SET password = ? " +
                            "WHERE username = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement verifyStatement =
                         con.prepareStatement(verifySQL)) {

                verifyStatement.setString(1, username);
                verifyStatement.setString(2, favoriteColor);

                try (ResultSet rs = verifyStatement.executeQuery()) {

                    if (!rs.next()) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Username or security answer is incorrect.",
                                "Verification Failed",
                                JOptionPane.ERROR_MESSAGE
                        );

                        return;
                    }
                }

                try (PreparedStatement updateStatement =
                             con.prepareStatement(updateSQL)) {

                    updateStatement.setString(1, newPassword);
                    updateStatement.setString(2, username);

                    int rowsUpdated = updateStatement.executeUpdate();

                    if (rowsUpdated > 0) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Password reset successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        goBack();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Unable to reset password.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error: " + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }
        });

        // BACK BUTTON
        backButton.addActionListener(e -> goBack());

        // WINDOW CLOSE
        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                goBack();
            }
        });

        setVisible(true);
    }

    // LABEL HELPER
    private void addLabel(JPanel panel, String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(TEXT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(6));
    }

    // TEXT FIELD HELPER
    private JTextField createTextField() {

        JTextField field = new JTextField();

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(380, 38));
        field.setPreferredSize(new Dimension(380, 38));
        field.setBorder(new LineBorder(BORDER, 1, true));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        return field;
    }

    // PASSWORD FIELD HELPER
    private JPasswordField createPasswordField() {

        JPasswordField field = new JPasswordField();

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(380, 38));
        field.setPreferredSize(new Dimension(380, 38));
        field.setBorder(new LineBorder(BORDER, 1, true));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        return field;
    }

    // GO BACK
    private void goBack() {

        dispose();

        if (loginPage != null) {
            loginPage.setVisible(true);
        }
    }

    // MAIN
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ForgotPassword page = new ForgotPassword(null);
            page.setVisible(true);
        });
    }
}
