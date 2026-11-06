package bookstore;

/**
 * Represents a customer in the bookstore.
 * Stores login info, points, and current membership state (Silver or Gold).
 */
public class Customer {

    // Customer login credentials
    private String username;
    private String password;

    // Points accumulated from purchases
    private int points;

    // State pattern: determines whether customer is Silver or Gold
    private CustomerState state;

    /**
     * Constructor to create a new customer.
     * Initializes username, password, and points, then sets the correct state.
     */
    public Customer(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;

        // Set initial state based on points
        updateState();
    }

    // Returns the customer's username
    public String getUsername() {
        return username;
    }

    // Returns the customer's password
    public String getPassword() {
        return password;
    }

    // Returns the customer's current points
    public int getPoints() {
        return points;
    }

    /**
     * Returns the current status (Silver or Gold)
     * using the State Pattern.
     */
    public String getStatus() {
        return state.getStatusName();
    }

    /**
     * Updates the customer's points.
     * Ensures points never go below 0.
     * Also updates the state after change.
     */
    public void setPoints(int points) {
        this.points = Math.max(points, 0);

        // Update state (Silver/Gold) after points change
        updateState();
    }

    /**
     * Adds points after a purchase.
     * Rule: 1 CAD = 10 points
     */
    public void addPointsFromPurchase(double amountPaid) {
        int earned = (int) Math.round(amountPaid * 10);
        setPoints(points + earned);
    }

    /**
     * Redeems points to reduce total cost.
     * Rule: 100 points = 1 CAD discount
     * Ensures cost does not go below 0.
     * Returns the final cost after discount.
     */
    public double redeemPoints(double totalCost) {

        // Maximum discount possible based on available points
        double maxDiscount = points / 100.0;

        // Cannot discount more than total cost
        double discount = Math.min(maxDiscount, totalCost);

        // Convert discount back to points used
        int usedPoints = (int) Math.round(discount * 100);

        // Deduct used points and update state
        setPoints(points - usedPoints);

        // Return final cost after discount
        return totalCost - discount;
    }

    /**
     * Updates the customer's state based on points.
     * >= 1000 points → Gold
     * < 1000 points → Silver
     */
    private void updateState() {
        if (points >= 1000) {
            state = new GoldState();
        } else {
            state = new SilverState();
        }
    }
}