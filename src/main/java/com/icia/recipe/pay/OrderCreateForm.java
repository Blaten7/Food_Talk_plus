package com.icia.recipe.pay;

import lombok.Data;

@Data
public class OrderCreateForm {
    private String name;
    private int totalPrice;
}
