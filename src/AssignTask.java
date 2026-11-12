
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AssignTask extends JFrame {

    // ================= COLORS =================

    private final Color BLUE = new Color(63, 100, 220);
    private final Color HEADER_BLUE = new Color(231, 238, 255);
    private final Color BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT = new Color(38, 46, 60);
    private final Color GRAY = new Color(105, 115, 130);
    private final Color BORDER = new Color(218, 224, 234);

    // DASHBOARD REFERENCE
    private Dashboard dashboard;

    // TEXT FIELDS
    private JTextField startDateField;
    private JTextField dueDateField;
    private JTextField userIdField;
    private JTextField taskTitleField;
    private JTextField assignedByField;
    private JTextField assignedToField;
    private JTextField departmentRoleField;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public AssignTask(Dashboard dashboard) {

        this.dashboard = dashboard;

        setTitle("Task Management System - Assign Task");
        setSize(950, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        setContentPane(mainPanel);


        // ========================================================
        // HEADER
        // ========================================================

        JPanel header = new JPanel(new BorderLayout());

        header.setBackground(HEADER_BLUE);
        header.setBorder(new EmptyBorder(10, 25, 10, 25));

        JLabel logoLabel;

        try {

            ImageIcon logoIcon = new ImageIcon(
                    getClass().getResource("/Poornima.jpeg")
            );

            Image image = logoIcon.getImage().getScaledInstance(
                    180, 60, Image.SCALE_SMOOTH
            );

            logoLabel = new JLabel(new ImageIcon(image));

        } catch (Exception e) {

            logoLabel = new JLabel("POORNIMA");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 22));
            logoLabel.setForeground(BLUE);
        }

        header.add(logoLabel, BorderLayout.WEST);
        mainPanel.add(header, BorderLayout.NORTH);


        // ========================================================
        // CENTER PANEL
        // ========================================================

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND);


        // ========================================================
        // FORM CARD
        // ========================================================

        JPanel formCard = new JPanel();

        formCard.setLayout(
                new BoxLayout(formCard, BoxLayout.Y_AXIS)
        );

        formCard.setBackground(Color.WHITE);

        formCard.setPreferredSize(new Dimension(650, 560));
        formCard.setMinimumSize(new Dimension(650, 560));
        formCard.setMaximumSize(new Dimension(650, 560));

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(25, 45, 25, 45)
                )
        );


        // ========================================================
        // HEADING
        // ========================================================

        JLabel heading = new JLabel("Assign Task");

        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Create and assign a new task"
        );

        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        formCard.add(heading);
        formCard.add(Box.createVerticalStrut(5));
        formCard.add(subtitle);
        formCard.add(Box.createVerticalStrut(15));


        // ========================================================
        // CREATE TEXT FIELDS
        // ========================================================

        startDateField = new JTextField();
        dueDateField = new JTextField();
        userIdField = new JTextField();
        taskTitleField = new JTextField();
        assignedByField = new JTextField();
        assignedToField = new JTextField();
        departmentRoleField = new JTextField();


        // ========================================================
        // ADD ALL FIELDS
        // Date format: yyyy-MM-dd
        // Example: 2026-10-31
        // ========================================================

        addField(formCard, "Start Date (yyyy-MM-dd)", startDateField);
        addField(formCard, "Due Date (yyyy-MM-dd)", dueDateField);
        addField(formCard, "User ID", userIdField);
        addField(formCard, "Task Title", taskTitleField);
        addField(formCard, "Assigned By", assignedByField);
        addField(formCard, "Assigned To", assignedToField);
        addField(formCard, "Department Role", departmentRoleField);


        // ========================================================
        // BUTTON PANEL
        // ========================================================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 0)
        );

        buttonPanel.setBackground(Color.WHITE);

        JButton submitButton = createButton("Submit");
        JButton resetButton = createButton("Reset");
        JButton backButton = createButton("Back");

        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(backButton);

        formCard.add(Box.createVerticalStrut(8));
        formCard.add(buttonPanel);


        // ========================================================
        // ADD CARD TO CENTER
        // ========================================================

        centerPanel.add(formCard);

        mainPanel.add(centerPanel, BorderLayout.CENTER);


        // ========================================================
        // FOOTER
        // ========================================================

        JPanel footer = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(8, 0, 8, 0));

        JLabel footerText = new JLabel(
                "Made by \"Prem Goplani\""
        );

        footerText.setFont(new Font("Arial", Font.PLAIN, 13));
        footerText.setForeground(GRAY);

        footer.add(footerText);

        mainPanel.add(footer, BorderLayout.SOUTH);


        // ========================================================
        // SUBMIT BUTTON - SAVE TASK IN MYSQL
        // ========================================================

        submitButton.addActionListener(e -> saveTask());


        // ========================================================
        // RESET BUTTON
        // ========================================================

        resetButton.addActionListener(e -> {

            startDateField.setText("");
            dueDateField.setText("");
            userIdField.setText("");
            taskTitleField.setText("");
            assignedByField.setText("");
            assignedToField.setText("");
            departmentRoleField.setText("");

        });


        // ========================================================
        // BACK BUTTON
        // ========================================================

        backButton.addActionListener(e -> goBack());


        // ========================================================
        // WINDOW CLOSE BUTTON
        // ========================================================

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });


        // SHOW WINDOW
        setVisible(true);
    }


    // ============================================================
    // SAVE TASK IN MYSQL
    // ============================================================

    private void saveTask() {

        String startDateText = startDateField.getText().trim();
        String dueDateText = dueDateField.getText().trim();
        String userIdText = userIdField.getText().trim();
        String taskTitle = taskTitleField.getText().trim();
        String assignedBy = assignedByField.getText().trim();
        String assignedTo = assignedToField.getText().trim();
        String departmentRole = departmentRoleField.getText().trim();


        // CHECK EMPTY FIELDS

        if (startDateText.isEmpty()
                || dueDateText.isEmpty()
                || userIdText.isEmpty()
                || taskTitle.isEmpty()
                || assignedBy.isEmpty()
                || assignedTo.isEmpty()
                || departmentRole.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all the fields.",
                    "Incomplete Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // CHECK USER ID

        int userId;

        try {

            userId = Integer.parseInt(userIdText);

            if (userId <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "User ID must be a valid positive number.",
                    "Invalid User ID",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // VALIDATE DATES

        LocalDate startDate;
        LocalDate dueDate;

        try {

            startDate = LocalDate.parse(startDateText);
            dueDate = LocalDate.parse(dueDateText);

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter dates in yyyy-MM-dd format.\n"
                            + "Example: 2026-10-31",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // CHECK DUE DATE

        if (dueDate.isBefore(startDate)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Due Date cannot be earlier than Start Date.",
                    "Invalid Date Range",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // MYSQL INSERT QUERY

        String sql =
                "INSERT INTO tasks "
                        + "(user_id, task_title, start_date, due_date, "
                        + "assigned_by, assigned_to, department) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";


        // DATABASE CONNECTION AND INSERT

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setString(2, taskTitle);
            statement.setDate(3, Date.valueOf(startDate));
            statement.setDate(4, Date.valueOf(dueDate));
            statement.setString(5, assignedBy);
            statement.setString(6, assignedTo);
            statement.setString(7, departmentRole);


            int rowsInserted = statement.executeUpdate();


            if (rowsInserted > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Task assigned and saved successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Close AssignTask and return to Dashboard
                goBack();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Task could not be saved.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }


    // ============================================================
    // ADD FIELD METHOD
    // ============================================================

    private void addField(
            JPanel panel,
            String labelText,
            JTextField textField
    ) {

        JPanel fieldPanel = new JPanel(
                new BorderLayout(10, 5)
        );

        fieldPanel.setBackground(Color.WHITE);

        fieldPanel.setPreferredSize(new Dimension(560, 45));
        fieldPanel.setMinimumSize(new Dimension(560, 45));
        fieldPanel.setMaximumSize(new Dimension(560, 45));


        // LABEL

        JLabel label = new JLabel(labelText);

        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(TEXT);
        label.setPreferredSize(new Dimension(180, 35));


        // TEXT FIELD

        textField.setFont(new Font("Arial", Font.PLAIN, 14));

        textField.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(5, 10, 5, 10)
                )
        );


        fieldPanel.add(label, BorderLayout.WEST);
        fieldPanel.add(textField, BorderLayout.CENTER);

        panel.add(fieldPanel);
        panel.add(Box.createVerticalStrut(3));
    }


    // ============================================================
    // CREATE BUTTON METHOD
    // ============================================================

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(BLUE);
        button.setFocusPainted(false);

        button.setBorder(
                new LineBorder(BLUE, 1, true)
        );

        button.setPreferredSize(new Dimension(110, 40));
        button.setMinimumSize(new Dimension(110, 40));
        button.setMaximumSize(new Dimension(110, 40));

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }


    // ============================================================
    // BACK TO DASHBOARD
    // ============================================================

    private void goBack() {

        dispose();

        if (dashboard != null) {
            dashboard.setVisible(true);
        }
    }


    // ============================================================
    // MAIN METHOD
    // ============================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new AssignTask(null);
        });
    }
}
