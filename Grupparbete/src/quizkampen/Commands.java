package quizkampen;

public enum Commands {
    WAIT("WAIT "),
    QUESTION("QUESTION "),
    RESULT("RESULT "),
    CHOOSECATEGORY("CHOOSECATEGORY "),
    STARTROUND("STARTROUND "),
    MESSAGE("MESSAGE "),
    ENDGAME("ENDGAME "),
    CATEGORY("CATEGORY "),
    ANSWER("ANSWER "),
    WELCOME("WELCOME ");

    private final String name;

    Commands(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
