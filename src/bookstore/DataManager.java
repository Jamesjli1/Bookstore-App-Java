package bookstore;

import java.io.*;

/**
 * Handles loading and saving of bookstore data (books and customers)
 * from and to text files.
 */
public class DataManager {

    // File names for storing data
    private static final String BOOKS_FILE = "books.txt";
    private static final String CUSTOMERS_FILE = "customers.txt";

    /**
     * Loads all data (books and customers) from files into a StoreData object.
     *
     * @return a StoreData object containing all loaded books and customers
     */
    public static StoreData loadData() {
        StoreData data = new StoreData();

        // Load books and customers from files
        loadBooks(data);
        loadCustomers(data);

        return data;
    }

    /**
     * Loads books from books.txt and adds them to StoreData.
     *
     * @param data the StoreData object to populate with books
     */
    private static void loadBooks(StoreData data) {
        File file = new File(BOOKS_FILE);

        // If file doesn't exist, nothing to load
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // Read each line and parse book data
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Expecting: name, price
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());

                    data.addBook(name, price);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads customers from customers.txt and adds them to StoreData.
     *
     * @param data the StoreData object to populate with customers
     */
    private static void loadCustomers(StoreData data) {
        File file = new File(CUSTOMERS_FILE);

        // If file doesn't exist, nothing to load
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // Read each line and parse customer data
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Expecting: username, password, points
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    int points = Integer.parseInt(parts[2].trim());

                    data.getCustomers().add(new Customer(username, password, points));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all current data (books and customers) to their respective files.
     *
     * @param data the StoreData object containing current app data
     */
    public static void saveData(StoreData data) {
        saveBooks(data);
        saveCustomers(data);
    }

    /**
     * Saves all books to books.txt.
     *
     * @param data the StoreData object containing books
     */
    private static void saveBooks(StoreData data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {

            // Write each book as: name,price
            for (Book book : data.getBooks()) {
                writer.println(book.getName() + "," + book.getPrice());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all customers to customers.txt.
     *
     * @param data the StoreData object containing customers
     */
    private static void saveCustomers(StoreData data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMERS_FILE))) {

            // Write each customer as: username,password,points
            for (Customer customer : data.getCustomers()) {
                writer.println(
                    customer.getUsername() + "," +
                    customer.getPassword() + "," +
                    customer.getPoints()
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}