package supermarket;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class CashContext {
    List<CashStrategy> strategyList = new ArrayList<>();
    private static final Pattern stategyPattern = Pattern
            .compile("正常收费|满\\s*(?<threshold>\\d+)\\s*[返减]\\s*(?<back>\\d+)\\s*|打\\s*(?<fold>\\d+)\\s*折");

    public CashContext(String type) {
        var matcher = stategyPattern.matcher(type);
        while (matcher.find()) {
            if (matcher.group("fold") != null) {
                strategyList.add(new CashRebate(matcher.group("fold")));
            } else if (matcher.group("threshold") != null && matcher.group("back") != null) {
                strategyList.add(new CashReturn(matcher.group("threshold"),
                        matcher.group("back")));
            } else {
                strategyList.add(new CashNormal());
            }
        }
    }

    public double getResult(double money) {
        return money - strategyList.stream().mapToDouble(strategy -> strategy.acceptCash(money)).sum();
    }
}
