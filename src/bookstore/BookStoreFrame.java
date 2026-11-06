package bookstore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Main GUI window for the BookStore application.
 * Uses CardLayout to switch between all screens in a single frame.
 */
public class BookStoreFrame extends JFrame {

    // Layout used to switch between screens in the same window
    private final CardLayout cardLayout;

    // Main container panel that holds all screens
    private final JPanel mainPanel;

    // Shared data for books and customers
    private final StoreData data;

    // Currently logged in customer
    private Customer currentCustomer;

    // Login screen input fields
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    // Owner books screen components
    private DefaultTableModel ownerBooksModel;
    private JTable ownerBooksTable;
    private JTextField bookNameField;
    private JTextField bookPriceField;

    // Owner customers screen components
    private DefaultTableModel ownerCustomersModel;
    private JTable ownerCustomersTable;
    private JTextField customerUsernameField;
    private JTextField customerPasswordField;

    // Customer start screen components
    private JLabel customerWelcomeLabel;
    private JTable customerBooksTable;

    // Customer cost screen labels
    private JLabel costTotalLabel;
    private JLabel costPointsStatusLabel;

    /**
     * Constructs the main frame and initializes all screens.
     * Also saves data when the application window is closed.
     */
    public BookStoreFrame(StoreData data) {
        this.data = data;

        // Set basic frame properties
        setTitle("BookStore App");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        // Prevent immediate close so data can be saved first
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Save books and customers to files when user closes the app
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataManager.saveData(data);
                dispose();
                System.exit(0);
            }
        });

        // Create layout manager for switching screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add all application screens to the main panel
        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createOwnerStartPanel(), "ownerStart");
        mainPanel.add(createOwnerBooksPanel(), "ownerBooks");
        mainPanel.add(createOwnerCustomersPanel(), "ownerCustomers");
        mainPanel.add(createCustomerStartPanel(), "customerStart");
        mainPanel.add(createCustomerCostPanel(), "customerCost");

        // Add main panel to frame and show login screen first
        add(mainPanel);
        showLogin();
    }

    /**
     * Creates the login screen panel.
     * Allows either the owner or a customer to log in.
     */
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        // Title label for login screen
        JLabel title = new JLabel("Welcome to the BookStore App");
        title.setFont(new Font("Arial", Font.BOLD, 20));

        // Input fields and login button
        loginUsernameField = new JTextField(15);
        loginPasswordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        // Attempt login when button is clicked
        loginButton.addActionListener(e -> handleLogin());

        // Add title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Add username label and field
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        panel.add(loginUsernameField, gbc);

        // Add password label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        panel.add(loginPasswordField, gbc);

        // Add login button
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(loginButton, gbc);

        return panel;
    }

    /**
     * Creates the owner home screen with navigation buttons.
     */
    private JPanel createOwnerStartPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Buttons for owner actions
        JButton booksButton = new JButton("Books");
        JButton customersButton = new JButton("Customers");
        JButton logoutButton = new JButton("Logout");

        // Open books screen and refresh its table
        booksButton.addActionListener(e -> {
            refreshOwnerBooksTable();
            cardLayout.show(mainPanel, "ownerBooks");
        });

        // Open customers screen and refresh its table
        customersButton.addActionListener(e -> {
            refreshOwnerCustomersTable();
            cardLayout.show(mainPanel, "ownerCustomers");
        });

        // Return to login screen
        logoutButton.addActionListener(e -> showLogin());

        // Add buttons vertically
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(booksButton, gbc);

        gbc.gridy = 1;
        panel.add(customersButton, gbc);

        gbc.gridy = 2;
        panel.add(logoutButton, gbc);

        return panel;
    }

    /**
     * Creates the owner books management screen.
     * Owner can add, view, and delete books here.
     */
    private JPanel createOwnerBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table model for books, cells are read-only
        ownerBooksModel = new DefaultTableModel(new Object[]{"Book Name", "Book Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Table for displaying books
        ownerBooksTable = new JTable(ownerBooksModel);
        ownerBooksTable.setRowHeight(22);

        // Add books table to top of screen
        JScrollPane scrollPane = new JScrollPane(ownerBooksTable);
        scrollPane.setPreferredSize(new Dimension(650, 180));
        panel.add(scrollPane, BorderLayout.NORTH);

        // Middle panel for adding a new book
        JPanel middlePanel = new JPanel(new GridLayout(2, 3, 10, 10));
        bookNameField = new JTextField();
        bookPriceField = new JTextField();
        JButton addButton = new JButton("Add");

        middlePanel.add(new JLabel("Name"));
        middlePanel.add(new JLabel("Price"));
        middlePanel.add(new JLabel(""));

        middlePanel.add(bookNameField);
        middlePanel.add(bookPriceField);
        middlePanel.add(addButton);

        // Add new book when button is clicked
        addButton.addActionListener(e -> addBook());

        panel.add(middlePanel, BorderLayout.CENTER);

        // Bottom panel for delete/back actions
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);

        // Delete selected book or go back to owner home screen
        deleteButton.addActionListener(e -> deleteSelectedBook());
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ownerStart"));

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the owner customers management screen.
     * Owner can add, view, and delete customers here.
     */
    private JPanel createOwnerCustomersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table model for customers, cells are read-only
        ownerCustomersModel = new DefaultTableModel(new Object[]{"Username", "Password", "Points"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Table for displaying customers
        ownerCustomersTable = new JTable(ownerCustomersModel);
        ownerCustomersTable.setRowHeight(22);

        // Add customers table to top of screen
        JScrollPane scrollPane = new JScrollPane(ownerCustomersTable);
        scrollPane.setPreferredSize(new Dimension(650, 180));
        panel.add(scrollPane, BorderLayout.NORTH);

        // Middle panel for adding a new customer
        JPanel middlePanel = new JPanel(new GridLayout(2, 3, 10, 10));
        customerUsernameField = new JTextField();
        customerPasswordField = new JTextField();
        JButton addButton = new JButton("Add");

        middlePanel.add(new JLabel("Username"));
        middlePanel.add(new JLabel("Password"));
        middlePanel.add(new JLabel(""));

        middlePanel.add(customerUsernameField);
        middlePanel.add(customerPasswordField);
        middlePanel.add(addButton);

        // Add new customer when button is clicked
        addButton.addActionListener(e -> addCustomer());

        panel.add(middlePanel, BorderLayout.CENTER);

        // Bottom panel for delete/back actions
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);

        // Delete selected customer or go back to owner home screen
        deleteButton.addActionListener(e -> deleteSelectedCustomer());
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ownerStart"));

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the customer start screen.
     * Displays current customer info and available books for purchase.
     */
    private JPanel createCustomerStartPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Welcome message with username, points, and status
        customerWelcomeLabel = new JLabel();
        customerWelcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(customerWelcomeLabel, BorderLayout.NORTH);

        // Table of books that customer can select and buy
        customerBooksTable = new JTable();
        panel.add(new JScrollPane(customerBooksTable), BorderLayout.CENTER);

        // Bottom action buttons
        JPanel bottomPanel = new JPanel();
        JButton buyButton = new JButton("Buy");
        JButton redeemButton = new JButton("Redeem points and Buy");
        JButton logoutButton = new JButton("Logout");

        bottomPanel.add(buyButton);
        bottomPanel.add(redeemButton);
        bottomPanel.add(logoutButton);

        // Buy without redeeming points
        buyButton.addActionListener(e -> processPurchase(false));

        // Buy while redeeming points if possible
        redeemButton.addActionListener(e -> processPurchase(true));

        // Logout and return to login screen
        logoutButton.addActionListener(e -> showLogin());

        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Creates the customer cost screen shown after a purchase.
     * Displays total cost, points, and current status.
     */
    private JPanel createCustomerCostPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Labels showing total cost and customer points/status
        costTotalLabel = new JLabel("Total Cost: 0.00");
        costPointsStatusLabel = new JLabel("Points: 0, Status: Silver");

        // Logout button to return to login screen
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> showLogin());

        // Add components vertically
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(costTotalLabel, gbc);

        gbc.gridy = 1;
        panel.add(costPointsStatusLabel, gbc);

        gbc.gridy = 2;
        panel.add(logoutButton, gbc);

        return panel;
    }

    /**
     * Handles login for owner and customers.
     * Owner login is admin/admin.
     * Customer login is checked against stored customer data.
     */
    private void handleLogin() {
        String username = loginUsernameField.getText().trim();
        String password = new String(loginPasswordField.getPassword()).trim();

        // Check owner login
        if (username.equals("admin") && password.equals("admin")) {
            cardLayout.show(mainPanel, "ownerStart");
            return;
        }

        // Check customer login
        Customer customer = data.findCustomer(username, password);
        if (customer != null) {
            currentCustomer = customer;
            refreshCustomerScreen();
            cardLayout.show(mainPanel, "customerStart");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid login.");
        }
    }

    /**
     * Reloads the books table on the owner books screen.
     */
    private void refreshOwnerBooksTable() {
        ownerBooksModel.setRowCount(0);
        for (Book book : data.getBooks()) {
            ownerBooksModel.addRow(new Object[]{book.getName(), String.format("%.2f", book.getPrice())});
        }
    }

    /**
     * Reloads the customers table on the owner customers screen.
     */
    private void refreshOwnerCustomersTable() {
        ownerCustomersModel.setRowCount(0);
        for (Customer customer : data.getCustomers()) {
            ownerCustomersModel.addRow(new Object[]{customer.getUsername(), customer.getPassword(), customer.getPoints()});
        }
    }

    /**
     * Updates the customer screen with current customer info and available books.
     */
    private void refreshCustomerScreen() {
        // Update the welcome message
        customerWelcomeLabel.setText(
                "Welcome " + currentCustomer.getUsername() + ". You have " + currentCustomer.getPoints() +
                " points. Your status is " + currentCustomer.getStatus()
        );

        // Table model with a checkbox column for selecting books
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Book Name", "Book Price", "Select"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) {
                    return Boolean.class;
                }
                return String.class;
            }
        };

        // Load all available books into the customer table
        for (Book book : data.getBooks()) {
            model.addRow(new Object[]{book.getName(), String.format("%.2f", book.getPrice()), false});
        }

        customerBooksTable.setModel(model);
    }

    /**
     * Adds a new book after validating the input.
     */
    private void addBook() {
        String name = bookNameField.getText().trim();
        String priceText = bookPriceField.getText().trim();

        // Ensure both fields are filled
        if (name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both name and price.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);

            // Ensure price is not negative
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Price must be non-negative.");
                return;
            }

            // Attempt to add the book to store data
            boolean added = data.addBook(name, price);
            if (!added) {
                JOptionPane.showMessageDialog(this, "Book already exists.");
                return;
            }

            // Refresh table and clear input fields
            refreshOwnerBooksTable();
            bookNameField.setText("");
            bookPriceField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price.");
        }
    }

    /**
     * Deletes the currently selected book from the owner books table.
     */
    private void deleteSelectedBook() {
        int row = ownerBooksTable.getSelectedRow();

        // Make sure a row is selected before deleting
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book first.");
            return;
        }

        data.getBooks().remove(row);
        refreshOwnerBooksTable();
    }

    /**
     * Adds a new customer after validating the input.
     */
    private void addCustomer() {
        String username = customerUsernameField.getText().trim();
        String password = customerPasswordField.getText().trim();

        // Ensure both fields are filled
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both username and password.");
            return;
        }

        // Attempt to add the customer to store data
        boolean added = data.addCustomer(username, password);
        if (!added) {
            JOptionPane.showMessageDialog(this, "Customer already exists.");
            return;
        }

        // Refresh table and clear input fields
        refreshOwnerCustomersTable();
        customerUsernameField.setText("");
        customerPasswordField.setText("");
    }

    /**
     * Deletes the currently selected customer from the owner customers table.
     */
    private void deleteSelectedCustomer() {
        int row = ownerCustomersTable.getSelectedRow();

        // Make sure a row is selected before deleting
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer first.");
            return;
        }

        data.getCustomers().remove(row);
        refreshOwnerCustomersTable();
    }

    /**
     * Processes a purchase for the current customer.
     * If redeem is true, customer points are redeemed before calculating final cost.
     */
    private void processPurchase(boolean redeem) {
        DefaultTableModel model = (DefaultTableModel) customerBooksTable.getModel();

        // Store selected book rows
        List<Integer> selectedRows = new ArrayList<>();

        // Total price before redeeming points
        double totalCost = 0;

        // Check which books are selected and sum their prices
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean selected = (Boolean) model.getValueAt(i, 2);
            if (selected != null && selected) {
                selectedRows.add(i);
                totalCost += Double.parseDouble(model.getValueAt(i, 1).toString());
            }
        }

        // Prevent purchase if no books were selected
        if (selectedRows.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select at least one book.");
            return;
        }

        // Final cost may change if redeeming points
        double finalCost = totalCost;
        if (redeem) {
            finalCost = currentCustomer.redeemPoints(totalCost);
        }

        // Add points earned from the final amount paid
        currentCustomer.addPointsFromPurchase(finalCost);

        // Remove purchased books from the store
        for (int i = selectedRows.size() - 1; i >= 0; i--) {
            int rowIndex = selectedRows.get(i);
            data.getBooks().remove(rowIndex);
        }

        // Update labels on the cost screen
        costTotalLabel.setText(String.format("Total Cost: %.2f", finalCost));
        costPointsStatusLabel.setText("Points: " + currentCustomer.getPoints() + ", Status: " + currentCustomer.getStatus());

        // Show the cost screen after purchase
        cardLayout.show(mainPanel, "customerCost");
    }

    /**
     * Clears login fields, resets current customer, and shows login screen.
     */
    private void showLogin() {
        loginUsernameField.setText("");
        loginPasswordField.setText("");
        currentCustomer = null;
        cardLayout.show(mainPanel, "login");
    }
}