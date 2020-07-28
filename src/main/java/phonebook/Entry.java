package phonebook;

public class Entry {
    private final String number;
    private final String name;

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Entry(String number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + number + ")";
    }
}
