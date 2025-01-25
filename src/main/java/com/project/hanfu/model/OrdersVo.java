package com.project.hanfu.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrdersVo extends Orders {

    private String username;
    private String phone;
    private String address;

}



