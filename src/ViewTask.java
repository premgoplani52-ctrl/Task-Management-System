
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ViewTask extends JFrame {

    // ================= COLORS =================

    private final Color BLUE = new Color(63, 100, 220);
    private final Color HEADER_BLUE = new Color(231, 238, 255);
    private final Color BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT = new Color(38, 46, 60);
    private final Color GRAY = new Color(105, 115, 130);
    private final Color BORDER = new Color(218, 224, 234);

    // DASHBOARD REFERENCE
    private Dashboard dashboard;

    // TABLE
    private JTable taskTable;
    private DefaultTableModel tableModel;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public ViewTask(Dashboard dashboard) {

        this.dashboard = dashboard;

        setTitle("Task Management System - View Tasks");
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());


        // ========================================================
        // HEADER
        // ========================================================

        JPanel headerPanel = new JPanel(new GridBagLayout());

        headerPanel.setBackground(HEADER_BLUE);
        headerPanel.setPreferredSize(new Dimension(1050, 90));

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
            logoLabel.setFont(new Font("Arial", Font.BOLD, 22));
            logoLabel.setForeground(BLUE);
        }

        headerPanel.add(logoLabel);
        add(headerPanel, BorderLayout.NORTH);


        // ========================================================
        // MAIN PANEL
        // ========================================================

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND);


        // ========================================================
        // TASK CARD
        // ========================================================

        JPanel taskCard = new JPanel();

        taskCard.setLayout(
                new BoxLayout(taskCard, BoxLayout.Y_AXIS)
        );

        taskCard.setBackground(Color.WHITE);

        taskCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(25, 35, 25, 35)
                )
        );

        taskCard.setPreferredSize(new Dimension(950, 500));
        taskCard.setMinimumSize(new Dimension(950, 500));
        taskCard.setMaximumSize(new Dimension(950, 500));


        // ========================================================
        // HEADING
        // ========================================================

        JPanel headingPanel = new JPanel(new GridBagLayout());
        headingPanel.setBackground(Color.WHITE);

        JLabel heading = new JLabel("View Tasks");

        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "View and manage all assigned tasks"
        );

        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(GRAY);

        JPanel headingContent = new JPanel();

        headingContent.setLayout(
                new BoxLayout(headingContent, BoxLayout.Y_AXIS)
        );

        headingContent.setBackground(Color.WHITE);

        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headingContent.add(heading);
        headingContent.add(Box.createVerticalStrut(7));
        headingContent.add(subtitle);

        headingPanel.add(headingContent);

        taskCard.add(headingPanel);
        taskCard.add(Box.createVerticalStrut(25));


        // ========================================================
        // TABLE MODEL
        // ========================================================

        String[] columns = {
                "Task ID",
                "User Name",
                "Task Title",
                "Actions"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        // ========================================================
        // CREATE TABLE
        // ========================================================

        taskTable = new JTable(tableModel);

        taskTable.setRowHeight(55);
        taskTable.setFont(new Font("Arial", Font.PLAIN, 14));
        taskTable.setForeground(TEXT);
        taskTable.setBackground(Color.WHITE);
        taskTable.setGridColor(BORDER);
        taskTable.setShowVerticalLines(false);
        taskTable.setIntercellSpacing(new Dimension(0, 1));

        taskTable.setSelectionBackground(Color.WHITE);
        taskTable.setSelectionForeground(TEXT);
        taskTable.setRowSelectionAllowed(false);
        taskTable.setColumnSelectionAllowed(false);
        taskTable.setFocusable(false);


        // ========================================================
        // TABLE HEADER
        // ========================================================

        taskTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        taskTable.getTableHeader().setForeground(TEXT);
        taskTable.getTableHeader().setBackground(HEADER_BLUE);

        taskTable.getTableHeader().setPreferredSize(
                new Dimension(0, 45)
        );


        // ========================================================
        // COLUMN WIDTH
        // ========================================================

        taskTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(400);
        taskTable.getColumnModel().getColumn(3).setPreferredWidth(300);


        // ========================================================
        // HIDE TASK ID COLUMN
        // ========================================================

        TableColumn idColumn =
                taskTable.getColumnModel().getColumn(0);

        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setPreferredWidth(0);


        // ========================================================
        // TEXT PADDING
        // ========================================================

        DefaultTableCellRenderer paddedRenderer =
                new DefaultTableCellRenderer();

        paddedRenderer.setBorder(
                new EmptyBorder(0, 20, 0, 10)
        );

        paddedRenderer.setForeground(TEXT);
        paddedRenderer.setBackground(Color.WHITE);

        taskTable.getColumnModel().getColumn(1)
                .setCellRenderer(paddedRenderer);

        taskTable.getColumnModel().getColumn(2)
                .setCellRenderer(paddedRenderer);


        // ========================================================
        // ACTION BUTTON RENDERER
        // ========================================================

        taskTable.getColumnModel().getColumn(3)
                .setCellRenderer(new ActionButtonRenderer());


        // ========================================================
        // CLICK HANDLER
        // ========================================================

        taskTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int row = taskTable.rowAtPoint(e.getPoint());
                int column = taskTable.columnAtPoint(e.getPoint());

                if (row < 0 || column != 3) {
                    return;
                }

                Rectangle cellRect = taskTable.getCellRect(
                        row, column, true
                );

                int clickX = e.getX() - cellRect.x;

                int buttonWidth = 72;
                int gap = 6;

                int totalWidth = (buttonWidth * 3) + (gap * 2);

                int startX = (cellRect.width - totalWidth) / 2;


                // VIEW

                if (clickX >= startX
                        && clickX < startX + buttonWidth) {

                    showTaskDetails(row);
                }

                // EDIT

                else if (clickX >= startX + buttonWidth + gap
                        && clickX < startX + (buttonWidth * 2) + gap) {

                    editTask(row);
                }

                // DELETE

                else if (clickX >= startX + (buttonWidth * 2) + (gap * 2)
                        && clickX < startX + (buttonWidth * 3) + (gap * 2)) {

                    deleteTask(row);
                }
            }
        });


        // ========================================================
        // SCROLL PANE
        // ========================================================

        JScrollPane scrollPane = new JScrollPane(taskTable);

        scrollPane.setBorder(new LineBorder(BORDER, 1, true));

        scrollPane.getViewport().setBackground(Color.WHITE);

        scrollPane.setPreferredSize(new Dimension(880, 350));

        taskCard.add(scrollPane);


        // ========================================================
        // ADD CARD TO CENTER
        // ========================================================

        GridBagConstraints cardGbc = new GridBagConstraints();

        cardGbc.gridx = 0;
        cardGbc.gridy = 0;
        cardGbc.anchor = GridBagConstraints.CENTER;

        cardGbc.insets = new Insets(10, 20, 10, 20);

        mainPanel.add(taskCard, cardGbc);

        add(mainPanel, BorderLayout.CENTER);


        // ========================================================
        // FOOTER
        // ========================================================

        JPanel footerPanel = new JPanel(new BorderLayout());

        footerPanel.setBackground(HEADER_BLUE);
        footerPanel.setPreferredSize(new Dimension(1050, 42));

        footerPanel.setBorder(
                BorderFactory.createMatteBorder(
                        1, 0, 0, 0, BORDER
                )
        );

        JLabel footerLabel = new JLabel(
                "Made by \"Prem Goplani\"",
                SwingConstants.CENTER
        );

        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(GRAY);

        footerPanel.add(footerLabel, BorderLayout.CENTER);

        add(footerPanel, BorderLayout.SOUTH);


        // ========================================================
        // LOAD TASKS FROM MYSQL
        // ========================================================

        loadTasks();


        // ========================================================
        // WINDOW CLOSE BUTTON
        // ========================================================

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                if (dashboard != null) {
                    dashboard.setVisible(true);
                }

                dispose();
            }
        });


        setVisible(true);
    }


    // ============================================================
    // LOAD TASKS FROM MYSQL
    // ============================================================

    private void loadTasks() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT task_id, assigned_to, task_title "
                        + "FROM tasks ORDER BY task_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                int taskId = result.getInt("task_id");

                String userName = result.getString("assigned_to");
                String taskTitle = result.getString("task_title");

                tableModel.addRow(new Object[]{
                        taskId,
                        userName,
                        taskTitle,
                        ""
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load tasks from database:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }


    // ============================================================
    // ACTION BUTTON RENDERER
    // ============================================================

    class ActionButtonRenderer extends JPanel
            implements TableCellRenderer {

        public ActionButtonRenderer() {

            setLayout(
                    new FlowLayout(
                            FlowLayout.CENTER,
                            6,
                            8
                    )
            );

            setBackground(Color.WHITE);

            add(createActionButton("View"));
            add(createActionButton("Edit"));
            add(createActionButton("Delete"));
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            setBackground(Color.WHITE);

            return this;
        }
    }


    // ============================================================
    // CREATE ACTION BUTTON
    // ============================================================

    private JButton createActionButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(TEXT);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);

        button.setBorder(
                new LineBorder(BORDER, 1, true)
        );

        button.setPreferredSize(new Dimension(72, 32));

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }


    // ============================================================
    // VIEW TASK DETAILS
    // ============================================================

    private void showTaskDetails(int row) {

        int taskId = getTaskId(row);

        String sql =
                "SELECT * FROM tasks WHERE task_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, taskId);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    String details =
                            "Task ID          : "
                                    + result.getInt("task_id")
                                    + "\n\n"

                                    + "User ID          : "
                                    + result.getInt("user_id")
                                    + "\n\n"

                                    + "Task Title       : "
                                    + result.getString("task_title")
                                    + "\n\n"

                                    + "Start Date       : "
                                    + result.getDate("start_date")
                                    + "\n\n"

                                    + "Due Date         : "
                                    + result.getDate("due_date")
                                    + "\n\n"

                                    + "Assigned By      : "
                                    + result.getString("assigned_by")
                                    + "\n\n"

                                    + "Assigned To      : "
                                    + result.getString("assigned_to")
                                    + "\n\n"

                                    + "Department Role  : "
                                    + result.getString("department")
                                    + "\n\n"

                                    + "Status           : "
                                    + result.getString("status");


                    JTextArea textArea = new JTextArea(details);

                    textArea.setFont(
                            new Font("Arial", Font.PLAIN, 14)
                    );

                    textArea.setForeground(TEXT);
                    textArea.setBackground(Color.WHITE);
                    textArea.setEditable(false);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);

                    textArea.setBorder(
                            new EmptyBorder(10, 15, 10, 15)
                    );

                    JScrollPane scrollPane = new JScrollPane(textArea);

                    scrollPane.setBorder(
                            new LineBorder(BORDER, 1, true)
                    );

                    scrollPane.setPreferredSize(
                            new Dimension(480, 350)
                    );

                    JOptionPane.showMessageDialog(
                            this,
                            scrollPane,
                            "Task Details",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Task not found.",
                            "Information",
                            JOptionPane.WARNING_MESSAGE
                    );

                    loadTasks();
                }
            }

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }


    // ============================================================
    // EDIT TASK
    // ============================================================

    private void editTask(int row) {

        int taskId = getTaskId(row);

        String selectSql =
                "SELECT * FROM tasks WHERE task_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement selectStatement =
                     connection.prepareStatement(selectSql)) {

            selectStatement.setInt(1, taskId);

            try (ResultSet result = selectStatement.executeQuery()) {

                if (!result.next()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Task not found.",
                            "Information",
                            JOptionPane.WARNING_MESSAGE
                    );

                    loadTasks();
                    return;
                }

                // GET CURRENT DATABASE VALUES

                JTextField startDateField = createEditField(
                        result.getDate("start_date") == null
                                ? ""
                                : result.getDate("start_date").toString()
                );

                JTextField dueDateField = createEditField(
                        result.getDate("due_date") == null
                                ? ""
                                : result.getDate("due_date").toString()
                );

                JTextField userIdField = createEditField(
                        String.valueOf(result.getInt("user_id"))
                );

                JTextField taskTitleField = createEditField(
                        result.getString("task_title")
                );

                JTextField assignedByField = createEditField(
                        result.getString("assigned_by")
                );

                JTextField assignedToField = createEditField(
                        result.getString("assigned_to")
                );

                JTextField departmentRoleField = createEditField(
                        result.getString("department")
                );

                JTextField statusField = createEditField(
                        result.getString("status")
                );


                // FORM PANEL

                JPanel formPanel = new JPanel(
                        new GridBagLayout()
                );

                formPanel.setBackground(Color.WHITE);

                GridBagConstraints gbc = new GridBagConstraints();

                gbc.insets = new Insets(6, 8, 6, 8);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1;


                addEditField(formPanel, gbc, "Start Date (yyyy-MM-dd)",
                        startDateField, 0);

                addEditField(formPanel, gbc, "Due Date (yyyy-MM-dd)",
                        dueDateField, 1);

                addEditField(formPanel, gbc, "User ID",
                        userIdField, 2);

                addEditField(formPanel, gbc, "Task Title",
                        taskTitleField, 3);

                addEditField(formPanel, gbc, "Assigned By",
                        assignedByField, 4);

                addEditField(formPanel, gbc, "Assigned To",
                        assignedToField, 5);

                addEditField(formPanel, gbc, "Department Role",
                        departmentRoleField, 6);

                addEditField(formPanel, gbc, "Status",
                        statusField, 7);


                JScrollPane scrollPane = new JScrollPane(formPanel);

                scrollPane.setBorder(null);

                scrollPane.setPreferredSize(
                        new Dimension(520, 390)
                );


                // SHOW EDIT DIALOG

                int choice = JOptionPane.showConfirmDialog(
                        this,
                        scrollPane,
                        "Edit Task",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


                if (choice != JOptionPane.OK_OPTION) {
                    return;
                }


                // VALIDATE EMPTY FIELDS

                if (startDateField.getText().trim().isEmpty()
                        || dueDateField.getText().trim().isEmpty()
                        || userIdField.getText().trim().isEmpty()
                        || taskTitleField.getText().trim().isEmpty()
                        || assignedByField.getText().trim().isEmpty()
                        || assignedToField.getText().trim().isEmpty()
                        || departmentRoleField.getText().trim().isEmpty()
                        || statusField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please fill in all the fields.",
                            "Incomplete Information",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                // VALIDATE USER ID

                int userId;

                try {

                    userId = Integer.parseInt(
                            userIdField.getText().trim()
                    );

                    if (userId <= 0) {
                        throw new NumberFormatException();
                    }

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "User ID must be a positive number.",
                            "Invalid User ID",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }


                // VALIDATE DATES

                LocalDate startDate;
                LocalDate dueDate;

                try {

                    startDate = LocalDate.parse(
                            startDateField.getText().trim()
                    );

                    dueDate = LocalDate.parse(
                            dueDateField.getText().trim()
                    );

                } catch (DateTimeParseException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Enter dates in yyyy-MM-dd format.\n"
                                    + "Example: 2026-10-31",
                            "Invalid Date",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }


                if (dueDate.isBefore(startDate)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Due Date cannot be earlier than Start Date.",
                            "Invalid Date Range",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }


                // UPDATE MYSQL

                String updateSql =
                        "UPDATE tasks SET "
                                + "user_id = ?, "
                                + "task_title = ?, "
                                + "start_date = ?, "
                                + "due_date = ?, "
                                + "assigned_by = ?, "
                                + "assigned_to = ?, "
                                + "department = ?, "
                                + "status = ? "
                                + "WHERE task_id = ?";


                try (PreparedStatement updateStatement =
                             connection.prepareStatement(updateSql)) {

                    updateStatement.setInt(1, userId);

                    updateStatement.setString(
                            2,
                            taskTitleField.getText().trim()
                    );

                    updateStatement.setDate(
                            3,
                            java.sql.Date.valueOf(startDate)
                    );

                    updateStatement.setDate(
                            4,
                            java.sql.Date.valueOf(dueDate)
                    );

                    updateStatement.setString(
                            5,
                            assignedByField.getText().trim()
                    );

                    updateStatement.setString(
                            6,
                            assignedToField.getText().trim()
                    );

                    updateStatement.setString(
                            7,
                            departmentRoleField.getText().trim()
                    );

                    updateStatement.setString(
                            8,
                            statusField.getText().trim()
                    );

                    updateStatement.setInt(9, taskId);


                    int rowsUpdated = updateStatement.executeUpdate();


                    if (rowsUpdated > 0) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Task updated successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        loadTasks();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Task could not be updated.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }


    // ============================================================
    // CREATE EDIT FIELD
    // ============================================================

    private JTextField createEditField(String text) {

        JTextField field = new JTextField(
                text == null ? "" : text
        );

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(7, 10, 7, 10)
                )
        );

        field.setPreferredSize(new Dimension(320, 38));

        return field;
    }


    // ============================================================
    // ADD EDIT FIELD
    // ============================================================

    private void addEditField(
            JPanel panel,
            GridBagConstraints gbc,
            String labelText,
            JTextField field,
            int row
    ) {

        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.25;

        JLabel label = new JLabel(labelText);

        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(TEXT);

        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.75;

        panel.add(field, gbc);
    }


    // ============================================================
    // DELETE TASK
    // ============================================================

    private void deleteTask(int row) {

        int taskId = getTaskId(row);

        String userName = String.valueOf(
                tableModel.getValueAt(row, 1)
        );

        String taskTitle = String.valueOf(
                tableModel.getValueAt(row, 2)
        );


        int choice = JOptionPane.showConfirmDialog(
                this,

                "Are you sure you want to delete\n"
                        + "the task \""
                        + taskTitle
                        + "\" assigned to "
                        + userName
                        + "?",

                "Delete Task",

                JOptionPane.YES_NO_OPTION,

                JOptionPane.WARNING_MESSAGE
        );


        if (choice != JOptionPane.YES_OPTION) {
            return;
        }


        String sql =
                "DELETE FROM tasks WHERE task_id = ?";


        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, taskId);

            int rowsDeleted = statement.executeUpdate();


            if (rowsDeleted > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Task deleted successfully!",
                        "Task Deleted",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadTasks();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Task was not found in the database.",
                        "Information",
                        JOptionPane.WARNING_MESSAGE
                );

                loadTasks();
            }

        } catch (SQLException e) {

            showDatabaseError(e);
        }
    }


    // ============================================================
    // GET TASK ID FROM SELECTED ROW
    // ============================================================

    private int getTaskId(int row) {

        return Integer.parseInt(
                tableModel.getValueAt(row, 0).toString()
        );
    }


    // ============================================================
    // DATABASE ERROR
    // ============================================================

    private void showDatabaseError(SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Database error:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );

        e.printStackTrace();
    }


    // ============================================================
    // MAIN METHOD
    // ============================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ViewTask(null);
        });
    }
}
