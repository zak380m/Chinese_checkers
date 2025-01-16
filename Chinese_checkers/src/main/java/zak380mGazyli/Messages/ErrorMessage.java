package zak380mGazyli.Messages;

/**
 * The ErrorMessage class represents an error message with a specific error description.
 */
public class ErrorMessage {

    private final String error;

    /**
     * Constructs an ErrorMessage instance with the specified error description.
     *
     * @param error The error description.
     */
    public ErrorMessage(String error) {
        this.error = error;
    }

    /**
     * Gets the error description.
     *
     * @return The error description.
     */
    public String getError() {
        return error;
    }

}
