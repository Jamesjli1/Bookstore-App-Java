package bookstore;

/**
 * Represents the Gold state of a customer.
 * This state is applied when the customer has 1000 or more points.
 */
public class GoldState implements CustomerState {

    /**
     * Returns the name of this state.
     *
     * @return "Gold"
     */
    @Override
    public String getStatusName() {
        return "Gold";
    }
}