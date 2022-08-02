package calculator;

class Add extends Operation {
    public Add() {
        super();
    }

    public Add(double opl, double opr) {
        super(opl, opr);
    }

    public double getResult() {
        return leftOperand + rightOperand;
    }
}

class Sub extends Operation {
    public Sub() {
        super();
    }

    public Sub(double opl, double opr) {
        super(opl, opr);
    }

    public double getResult() {
        return leftOperand - rightOperand;
    }
}

class Div extends Operation {
    public Div() {
        super();
    }

    public Div(double opl, double opr) {
        super(opl, opr);
    }

    public double getResult() {
        if (rightOperand == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return leftOperand / rightOperand;
    }
}

class Mul extends Operation {
    public Mul() {
        super();
    }

    public Mul(double opl, double opr) {
        super(opl, opr);
    }

    public double getResult() {
        return leftOperand * rightOperand;
    }
}