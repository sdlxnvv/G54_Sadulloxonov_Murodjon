package uz.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private Table table;
    private Waiter waiter = new Waiter();
    private List<Dish> dishes = new ArrayList<>();
    private Boolean active = true;

    public Order(String id) {
        this.id = id;
    }
}
