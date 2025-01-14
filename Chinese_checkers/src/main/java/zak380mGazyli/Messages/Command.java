package zak380mGazyli.Messages;

public class Command {
    private String name;
    private int[] args;
    private String textArg;

    public Command() {
    }

    public Command(String name) {
        this.name = name;
    }

    public Command(String name, int[] args) {
        this.name = name;
        this.args = args;
    }

    public Command(String name, int[] args, String textArg) {
        this.name = name;
        this.args = args;
        this.textArg = textArg;
    }

    public Command(String name, String textArg) {
        this.name = name;
        this.textArg = textArg;
    }

    public String getName() {
        return name;
    }

    public int[] getArgs() {
        return args;
    }

    public String getTextArg() {
        return textArg;
    }
}
