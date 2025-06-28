package uz.app;

import com.google.gson.Gson;
import uz.app.db.DB;
import uz.app.service.OrderService;

public class Main {
    private static  final Gson gson = new Gson();
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        boolean isExit = false;

        while (!isExit) {
            System.out.print("""
                    1. add waiter
                    2. add dish
                    3. OrderService
                    
                    0. exit
                    =>\s""");
            switch (DB.intScanner.nextInt()) {
                case 1 -> orderService.addWaiter();
                case 2 -> orderService.addDish();
                case 3 -> orderService.showOrderMenu();
                case 0 -> isExit = true;
                default -> System.out.println("Invalid input!");
            }
        }
    }
}