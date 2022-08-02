package supermarket;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try (var in = new Scanner(System.in)) {
            System.out.println("请输入收费策略：");
            var context = new CashContext(in.nextLine());
            System.out.println("请输入商品金额：");
            var money = in.nextDouble();
            System.out.println("应付金额：" + context.getResult(money));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
