package uz.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Waiter {
    private String id;
    private String fullName;
    private Integer finishOrders = 0;
}
