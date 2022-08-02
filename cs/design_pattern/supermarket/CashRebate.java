package supermarket;

public class CashRebate implements CashStrategy {
    private double moneyRebate;

    public CashRebate(String moneyRebate) {
        this.moneyRebate = (10 - Double.parseDouble(moneyRebate)) / 10;
    }

    @Override
    public double acceptCash(double money) {
        return money * moneyRebate;
    }

}
