package decorator_dress;

public class DecoratedPerson extends Person {
    protected Person component;

    public DecoratedPerson(Person component) {
        this.component = component;
    }
}
