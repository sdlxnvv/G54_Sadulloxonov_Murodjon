package uz.app.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import uz.app.entity.*;

import java.io.*;
import java.util.*;

public class DB {
    public static final Scanner strScanner = new Scanner(System.in);
    public static final Scanner intScanner = new Scanner(System.in);


    public static List<Waiter> waiters = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();
    public static List<Dish> dishes = new ArrayList<>();
    public static List<Table> tables = new ArrayList<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static Map<Waiter, Long> statistic = new HashMap<>();

    static {
        readTables();
        readDishes();
        readOrders();
        readWaiters();
    }

    @SneakyThrows
    public static void writeWaiters() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/murodsadulloxonov/Desktop/works/projects/module5/G54_Sadulloxonov_Murodjon/src/main/resources/waiters.json"))) {
            writer.write(gson.toJson(waiters));
        }
    }

    @SneakyThrows
    public static void writeOrders() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/murodsadulloxonov/Desktop/works/projects/module5/G54_Sadulloxonov_Murodjon/src/main/resources/orders.json"))) {
            writer.write(gson.toJson(orders));
        }
    }

    @SneakyThrows
    public static void writeDishes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/murodsadulloxonov/Desktop/works/projects/module5/G54_Sadulloxonov_Murodjon/src/main/resources/dishes.json"))) {
            writer.write(gson.toJson(dishes));
        }
    }



    @SneakyThrows
    public static void readDishes() {
        File file = new File("/Users/murodsadulloxonov/Desktop/works/projects/module5/G54_Sadulloxonov_Murodjon/src/main/resources/dishes.json");
        if (!file.exists() || file.length() == 0) {
            dishes = new ArrayList<>();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            if (!sb.toString().isBlank()) {
                Dish[] userArray = DB.gson.fromJson(sb.toString(), Dish[].class);
                dishes = new ArrayList<>(Arrays.asList(userArray));
            }
        }
    }

    @SneakyThrows
    public static void readOrders() {
        File file = new File("/Users/murodsadulloxonov/Desktop/works/projects/module5/G54_Sadulloxonov_Murodjon/src/main/resources/orders.json");
        if (!file.exists() || file.length() == 0) {
            orders = new ArrayList<>();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            if (!sb.toString().isBlank()) {
                Order[] userArray = DB.gson.fromJson(sb.toString(), Order[].class);
                orders = new ArrayList<>(Arrays.asList(userArray));
            }
        }
    }

    @SneakyThrows
    public static void readTables() {
        File file = new File("/Users/murodsadulloxonov/Desktop/works/projects/module5/G54_Sadulloxonov_Murodjon/src/main/resources/tables.json");
        if (!file.exists() || file.length() == 0) {
            tables = new ArrayList<>();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            if (!sb.toString().isBlank()) {
                Table[] userArray = DB.gson.fromJson(sb.toString(), Table[].class);
                tables = new ArrayList<>(Arrays.asList(userArray));
            }
        }
    }

    @SneakyThrows
    public static void readWaiters() {
        File file = new File("/Users/murodsadulloxonov/Desktop/works/projects/module5/G54_Sadulloxonov_Murodjon/src/main/resources/waiters.json");
        if (!file.exists() || file.length() == 0) {
            waiters = new ArrayList<>();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            if (!sb.toString().isBlank()) {
                Waiter[] userArray = DB.gson.fromJson(sb.toString(), Waiter[].class);
                waiters = new ArrayList<>(Arrays.asList(userArray));
            }
        }
    }
}
