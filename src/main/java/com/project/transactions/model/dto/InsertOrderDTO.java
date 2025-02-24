package com.project.transactions.model.dto;

import lombok.Data;

@Data
public class InsertOrderDTO {
    private Long pid;
    private Long oid;
    private String address;
    private Integer paymentMethod;
}
