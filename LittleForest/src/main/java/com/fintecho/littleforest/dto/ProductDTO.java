package com.fintecho.littleforest.dto;

import lombok.Data;

@Data
public class ProductDTO {
	
    private String name;
    private Integer price;
    private String category;
    private String des;
    private String image_Path;
	
}
