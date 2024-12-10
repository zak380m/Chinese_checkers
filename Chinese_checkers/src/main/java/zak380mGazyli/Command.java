package zak380mGazyli;

public class Command {
    private String name;
    private int[] args;

    public Command(String name) {
        this.name = name;
    }

    public Command(String name, int[] args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public int[] getArgs() {
        return args;
    }
}
