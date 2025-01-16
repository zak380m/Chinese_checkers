package zak380mGazyli.Messages;

/**
 * The Message class represents a message with a specific content.
 */
public class Message {
    private final String message;

    /**
     * Constructs a Message instance with the specified content.
     *
     * @param message The content of the message.
     */
    public Message(String message) {
        this.message = message;
    }

    /**
     * Gets the content of the message.
     *
     * @return The content of the message.
     */
    public String getMessage() {
        return message;
    }
}
