package zak380mGazyli.Messages;

/**
 * The Command class represents a command with a name, optional arguments, and an optional text argument.
 */
public class Command {
    private String name;
    private int[] args;
    private String textArg;

    /**
     * Default constructor for Command.
     */
    public Command() {
    }

    /**
     * Constructs a Command with the specified name.
     *
     * @param name The name of the command.
     */
    public Command(String name) {
        this.name = name;
    }

    /**
     * Constructs a Command with the specified name and arguments.
     *
     * @param name The name of the command.
     * @param args The arguments for the command.
     */
    public Command(String name, int[] args) {
        this.name = name;
        this.args = args;
    }

    /**
     * Constructs a Command with the specified name, arguments, and text argument.
     *
     * @param name The name of the command.
     * @param args The arguments for the command.
     * @param textArg The text argument for the command.
     */
    public Command(String name, int[] args, String textArg) {
        this.name = name;
        this.args = args;
        this.textArg = textArg;
    }

    /**
     * Constructs a Command with the specified name and text argument.
     *
     * @param name The name of the command.
     * @param textArg The text argument for the command.
     */
    public Command(String name, String textArg) {
        this.name = name;
        this.textArg = textArg;
    }

    /**
     * Gets the name of the command.
     *
     * @return The name of the command.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the arguments of the command.
     *
     * @return The arguments of the command.
     */
    public int[] getArgs() {
        return args;
    }

    /**
     * Gets the text argument of the command.
     *
     * @return The text argument of the command.
     */
    public String getTextArg() {
        return textArg;
    }
}
