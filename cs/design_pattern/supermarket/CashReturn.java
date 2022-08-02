package supermarket;

public class CashReturn implements CashStrategy {

    private double threshold;
    private double back;

    public CashReturn(String threshold, String back) {
        this.threshold = Double.parseDouble(threshold);
        this.back = Double.parseDouble(back);
    }

    @Override
    public double acceptCash(double money) {
        return (money >= threshold) ? back : 0;
    }

}
