package bookstore;

/**
 * Interface for the State Pattern representing a customer's status.
 * Implemented by different states such as SilverState and GoldState.
 */
public interface CustomerState {

    /**
     * Returns the name of the current state (e.g., "Silver" or "Gold").
     * 
     * @return the status name of the customer
     */
    String getStatusName();
}