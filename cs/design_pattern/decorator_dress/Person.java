package decorator_dress;

public class Person {
    private String name;

    public Person() {
        this("");
    }

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "人: " + name + "\n";
    }
}
