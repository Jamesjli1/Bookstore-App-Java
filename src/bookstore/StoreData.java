package bookstore;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores all application data including books and customers.
 * Provides methods to manage and search this data.
 */
public class StoreData {

    // List of all books in the store
    private final List<Book> books;

    // List of all customers
    private final List<Customer> customers;

    /**
     * Initializes empty lists for books and customers.
     */
    public StoreData() {
        books = new ArrayList<>();
        customers = new ArrayList<>();
    }

    /**
     * Returns the list of all books.
     *
     * @return list of books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Returns the list of all customers.
     *
     * @return list of customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Finds a customer by matching username and password.
     *
     * @param username the customer's username
     * @param password the customer's password
     * @return the matching Customer if found, otherwise null
     */
    public Customer findCustomer(String username, String password) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username) &&
                customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Adds a new book if it does not already exist.
     * Book names are compared case-insensitively.
     *
     * @param name the name of the book
     * @param price the price of the book
     * @return true if added successfully, false if duplicate
     */
    public boolean addBook(String name, double price) {
        for (Book book : books) {
            if (book.getName().equalsIgnoreCase(name)) {
                return false; // Duplicate book name
            }
        }

        books.add(new Book(name, price));
        return true;
    }

    /**
     * Adds a new customer if the username does not already exist.
     * New customers start with 0 points.
     *
     * @param username the customer's username
     * @param password the customer's password
     * @return true if added successfully, false if duplicate username
     */
    public boolean addCustomer(String username, String password) {
        for (Customer customer : customers) {
            if (customer.getUsername().equalsIgnoreCase(username)) {
                return false; // Duplicate username
            }
        }

        customers.add(new Customer(username, password, 0));
        return true;
    }
}