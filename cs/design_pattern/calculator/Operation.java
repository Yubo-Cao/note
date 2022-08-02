package calculator;

abstract public class Operation {
    protected double leftOperand = 0;
    protected double rightOperand = 0;

    public Operation() {
    }

    public Operation(double opl, double opr) {
        this.leftOperand = opl;
        this.rightOperand = opr;
    }

    public double getLeftOperand() {
        return leftOperand;
    }

    public double getRightOperand() {
        return rightOperand;
    }

    public double setRightOperand(double opr) {
        return rightOperand = opr;
    }

    public double setLeftOperand(double opl) {
        return leftOperand = opl;
    }

    public abstract double getResult();
}