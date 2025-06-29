package uz.app.service;

import uz.app.db.DB;
import uz.app.entity.Dish;
import uz.app.entity.Order;
import uz.app.entity.Waiter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrderService {

    public void addWaiter() {
        Waiter waiter = new Waiter();
        System.out.print("Enter full name: ");
        waiter.setFullName(DB.strScanner.nextLine());
        waiter.setId(UUID.randomUUID().toString());

        DB.waiters.add(waiter);
        DB.writeWaiters();
    }

    public void addDish() {
        Dish dish = new Dish();
        System.out.print("Enter dish name: ");
        dish.setName(DB.strScanner.nextLine());
        System.out.print("Enter price: ");
        dish.setPrice(DB.intScanner.nextLong());
        dish.setId(UUID.randomUUID().toString());

        DB.dishes.add(dish);
        DB.writeDishes();
    }

    public void showOrderMenu() {
        boolean isExit = false;

        while (!isExit) {
            System.out.println("""
                    1. open order
                    2. edit order
                    3. show orders
                    4. close order
                    5. show statistics
                    
                    0. exit
                    """);
            switch (DB.intScanner.nextInt()) {
                case 1 -> openOrder();
                case 2 -> editOrder();
                case 3 -> showOrders();
                case 4 -> closeOrder();
                case 5 -> showStatistics();
                case 0 -> isExit = true;
                default -> System.out.println("Invalid input!");
            }
        }
    }

    private void showStatistics() {
        Map<Waiter, Long> sum = new HashMap<>();
        for (Order order : DB.orders) {
            if (!order.getActive()) {
                for (Dish dish : order.getDishes()) {
                    sum.put(order.getWaiter(), sum.getOrDefault(order.getWaiter(), 0L) + dish.getPrice());
                }
            }
        }
        System.out.println("Total waiters: " + sum);
    }


    private void openOrder() {
        Order order = new Order(UUID.randomUUID().toString());

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        DB.tables.forEach(System.out::println);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.print("Which table are you sitting?(enter id): ");
        String tableId = DB.strScanner.nextLine();

        DB.tables.stream()
                .filter(t -> t.getId().equals(tableId))
                .findFirst()
                .ifPresentOrElse(order::setTable, () -> System.out.println("Table not found!"));


        showDishes();

        System.out.print("Which dish would you like to order?(enter id): ");
        String dishId = DB.strScanner.nextLine();

        DB.dishes.stream()
                .filter(dish -> dish.getId().equals(dishId))
                .findAny()
                .ifPresentOrElse(dish -> order.getDishes().add(dish), () -> System.out.println("No found dish!"));

        if (order.getDishes().isEmpty() || order.getTable() == null) {
            System.out.println("Nothing to order!");
        } else {
            DB.orders.add(order);
            DB.writeOrders();
        }

    }

    private void editOrder() {
        Order order = null;
        showOrders();
        System.out.print("Which order would you like to add dish?(enter id): ");
        String orderId = DB.strScanner.nextLine();

        for (Order o : DB.orders) {
            if (o.getId().equals(orderId) && o.getActive().equals(true)) {
                order = o;
            } else {
                System.out.println("May you input not active order");
                return;
            }
        }

        showDishes();
        System.out.print("Which dish would you like to add?(enter id): ");
        String dishId = DB.strScanner.nextLine();
        Order finalOrder = order;
        DB.dishes.stream()
                .filter(dish -> dish.getId().equals(dishId))
                .findAny()
                .ifPresentOrElse(dish -> {
                    assert finalOrder != null;
                    finalOrder.getDishes().add(dish);
                }, () -> System.out.println("No found dish!"));
        DB.writeOrders();

    }

    private void showOrders() {
       if (DB.orders.isEmpty()) {
           System.out.println("Nothing to show!");
       } else {
           System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
           DB.orders.forEach(System.out::println);
           System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
       }
    }

    private void closeOrder() {
        showOrders();
        System.out.print("Which order would  you like to close?(enter id): ");
        String orderId = DB.strScanner.nextLine();

        for (Order o : DB.orders) {
            if (o.getId().equals(orderId) &&  o.getActive().equals(true)) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
                DB.waiters.forEach(System.out::println);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");

                System.out.print("Which waiter are looking for?(enter id): ");
                String waiterId = DB.strScanner.nextLine();

                DB.waiters.stream()
                        .filter(wa -> wa.getId().equals(waiterId))
                        .findAny()
                        .ifPresentOrElse(o::setWaiter, () -> System.out.println("No found waiter!"));
                if (o.getWaiter() != null) {
                    o.setActive(false);
                } else {
                    System.out.println("No found waiter!");
                }

                if (o.getActive()) {
                    System.out.println("Order hasn't been closed!");
                } else {
                    o.getWaiter().setFinishOrders(o.getWaiter().getFinishOrders() + 1);
                    System.out.println("Order has been closed!");
                    DB.writeOrders();
                    DB.writeWaiters();
                }
            }
        }
    }


    private void showDishes() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        DB.dishes.forEach(System.out::println);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
