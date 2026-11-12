
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Dashboard extends JFrame {

    // ================= COLORS =================

    private final Color BLUE = new Color(63, 100, 220);
    private final Color HEADER_BLUE = new Color(231, 238, 255);
    private final Color BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT = new Color(38, 46, 60);
    private final Color GRAY = new Color(105, 115, 130);
    private final Color BORDER = new Color(218, 224, 234);

    // ================= CONSTRUCTOR =================

    public Dashboard() {

        setTitle("Task Management System - Dashboard");

        setSize(950, 650);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setResizable(true);

        setLayout(new BorderLayout());

        // ================= HEADER =================

        JPanel headerPanel =
                new JPanel(new GridBagLayout());

        headerPanel.setBackground(HEADER_BLUE);

        headerPanel.setPreferredSize(
                new Dimension(950, 90)
        );

        JLabel logoLabel = new JLabel();

        try {

            ImageIcon icon =
                    new ImageIcon(
                            getClass().getResource(
                                    "/Poornima.jpeg"
                            )
                    );

            Image image =
                    icon.getImage().getScaledInstance(
                            180,
                            60,
                            Image.SCALE_SMOOTH
                    );

            logoLabel.setIcon(
                    new ImageIcon(image)
            );

        } catch (Exception e) {

            logoLabel.setText("POORNIMA");

            logoLabel.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            22
                    )
            );

            logoLabel.setForeground(BLUE);
        }

        GridBagConstraints logoGbc =
                new GridBagConstraints();

        logoGbc.gridx = 0;
        logoGbc.gridy = 0;

        logoGbc.anchor =
                GridBagConstraints.CENTER;

        headerPanel.add(
                logoLabel,
                logoGbc
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ================= MAIN PANEL =================

        JPanel mainPanel =
                new JPanel(
                        new GridBagLayout()
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        // ================= DASHBOARD CARD =================

        JPanel dashboardCard =
                new JPanel();

        dashboardCard.setLayout(
                new BoxLayout(
                        dashboardCard,
                        BoxLayout.Y_AXIS
                )
        );

        dashboardCard.setBackground(
                Color.WHITE
        );

        dashboardCard.setBorder(
                BorderFactory.createCompoundBorder(

                        new LineBorder(
                                BORDER,
                                1,
                                true
                        ),

                        new EmptyBorder(
                                35,
                                55,
                                35,
                                55
                        )
                )
        );

        dashboardCard.setPreferredSize(
                new Dimension(
                        500,
                        400
                )
        );

        // ================= HEADING =================

        JLabel heading =
                new JLabel("Dashboard");

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        heading.setForeground(TEXT);

        heading.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // ================= SUBTITLE =================

        JLabel subtitle =
                new JLabel(
                        "Manage your tasks efficiently"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        subtitle.setForeground(GRAY);

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        dashboardCard.add(heading);

        dashboardCard.add(
                Box.createVerticalStrut(7)
        );

        dashboardCard.add(subtitle);

        dashboardCard.add(
                Box.createVerticalStrut(35)
        );

        // ================= ASSIGN TASK =================

        JButton assignTaskButton =
                new JButton("Assign Task");

        createDashboardButton(
                assignTaskButton
        );

        // ================= VIEW TASK =================

        JButton viewTaskButton =
                new JButton("View Task");

        createDashboardButton(
                viewTaskButton
        );

        // ================= CHANGE PASSWORD =================

        JButton changePasswordButton =
                new JButton("Change Password");

        createDashboardButton(
                changePasswordButton
        );

        // ================= ADD BUTTONS =================

        dashboardCard.add(
                assignTaskButton
        );

        dashboardCard.add(
                Box.createVerticalStrut(15)
        );

        dashboardCard.add(
                viewTaskButton
        );

        dashboardCard.add(
                Box.createVerticalStrut(15)
        );

        dashboardCard.add(
                changePasswordButton
        );

        // ================= ADD CARD =================

        GridBagConstraints cardGbc =
                new GridBagConstraints();

        cardGbc.gridx = 0;
        cardGbc.gridy = 0;

        cardGbc.anchor =
                GridBagConstraints.CENTER;

        cardGbc.insets =
                new Insets(
                        20,
                        20,
                        20,
                        20
                );

        mainPanel.add(
                dashboardCard,
                cardGbc
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // ASSIGN TASK
        // =====================================================

        assignTaskButton.addActionListener(
                e -> {

                    setVisible(false);

                    AssignTask page =
                            new AssignTask(this);

                    page.setVisible(true);
                }
        );

        // =====================================================
        // VIEW TASK
        // =====================================================

        viewTaskButton.addActionListener(
                e -> {

                    setVisible(false);

                    ViewTask page =
                            new ViewTask(this);

                    page.setVisible(true);
                }
        );

        // =====================================================
        // CHANGE PASSWORD
        // =====================================================

        changePasswordButton.addActionListener(
                e -> {

                    setVisible(false);

                    ChangePassword page =
                            new ChangePassword(this);

                    page.setVisible(true);
                }
        );

        // ================= FOOTER =================

        JPanel footerPanel =
                new JPanel(
                        new BorderLayout()
                );

        footerPanel.setBackground(
                HEADER_BLUE
        );

        footerPanel.setPreferredSize(
                new Dimension(
                        950,
                        42
                )
        );

        footerPanel.setBorder(
                BorderFactory.createMatteBorder(
                        1,
                        0,
                        0,
                        0,
                        BORDER
                )
        );

        JLabel footerLabel =
                new JLabel(
                        "Made by \"Prem Goplani\"",
                        SwingConstants.CENTER
                );

        footerLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        footerLabel.setForeground(GRAY);

        footerPanel.add(
                footerLabel,
                BorderLayout.CENTER
        );

        add(
                footerPanel,
                BorderLayout.SOUTH
        );

        // ================= SHOW =================

        setVisible(true);
    }

    // =====================================================
    // BUTTON STYLE
    // =====================================================

    private void createDashboardButton(
            JButton button
    ) {

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(BLUE);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        button.setPreferredSize(
                new Dimension(
                        320,
                        55
                )
        );

        button.setMaximumSize(
                new Dimension(
                        320,
                        55
                )
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> new Dashboard()
        );
    }
}

