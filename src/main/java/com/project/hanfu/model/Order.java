package com.project.hanfu.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Order {

    private int id;
    private String order_guid;
    private String flower;
    private int amount;
    private float price;
    private float state;
    // 用户 id
    private int uid;

}



