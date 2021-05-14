package com.redbeard.stream.domain;

import lombok.Data;

@Data
public class Offering {
    private String productId;
    private String name;
    private String description;
    private Double price;
}
