package calculator;

public class OperationFactory {
    public static Operation createOperation(String operator) {
        return switch (operator) {
            case "+" -> new Add();
            case "-" -> new Sub();
            case "*" -> new Mul();
            case "/" -> new Div();
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    private OperationFactory() {
    }
}
