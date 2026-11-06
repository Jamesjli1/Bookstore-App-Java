package bookstore;

/**
 * Represents a book in the bookstore.
 * Each book has a name and a price.
 */
public class Book {
    
    // Declare variables
    private String name;
    private double price;

    /**
     * Constructor to create a new Book object
     * @param name  name of book
     * @param price price of book
     */
    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Returns the name of the book
    public String getName() {
        return name;
    }

    // Returns the price of the book
    public double getPrice() {
        return price;
    }

    // Updates the name of the book
    public void setName(String name) {
        this.name = name;
    }

    // Updates the price of the book
    public void setPrice(double price) {
        this.price = price;
    }
}