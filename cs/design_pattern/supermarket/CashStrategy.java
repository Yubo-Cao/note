package supermarket;

@FunctionalInterface
/**
 * 策略类，定义了一系列算法的方法。所有的算法完成的都是相同的工作
 * 只不过实现不同。这样，就可以用相同的方式调用所有的算法
 * 减少各种算法类和使用算法类之间的耦合
 * 
 * 策略模式封装了算法——但事实上，他可以封装变化，封装各种类型的规则。
 * 利用抽象的方式（reflections.getSubTypesOf(Class<T> clazz)），可以获取所有的子类。
 * 然后，就可以动态dynamicly load all the subclasses of CashStrategy and dispatch them.
 * 反射！
 */
public interface CashStrategy {
    /*
     * Return reduce amount.
     */
    double acceptCash(double money);
}
