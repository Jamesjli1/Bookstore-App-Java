package bookstore;

/**
 * Represents the Silver state of a customer.
 * This state is applied when the customer has less than 1000 points.
 */
public class SilverState implements CustomerState {

    /**
     * Returns the name of this state.
     *
     * @return "Silver"
     */
    @Override
    public String getStatusName() {
        return "Silver";
    }
}