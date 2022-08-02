package calculator;

import java.util.Scanner;

public class Calculator {
    /**
     * 通过简单工厂的设计模式来创建策略对象进行计算。这样，
     * - 可复用：策略对象可以继续使用
     * - 可扩展：加入新的策略对象和修改工厂就可以实现扩展
     * - 可维护：策略对象可以被维护和管理，而不需要知道其他的策略对象
     * - 灵活
     * @param args
     */
    public static void main(String[] args) {
        try (var in = new Scanner(System.in)) {
            var leftOperand = in.nextDouble();
            var operator = in.next();
            var rightOperand = in.nextDouble();
            var operation = OperationFactory.createOperation(operator);
            operation.setLeftOperand(leftOperand);
            operation.setRightOperand(rightOperand);

            System.out.println(operation.getResult());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
