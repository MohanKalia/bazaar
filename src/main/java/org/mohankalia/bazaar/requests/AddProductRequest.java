package org.mohankalia.bazaar.requests;

import lombok.Data;
import org.mohankalia.bazaar.model.Category;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    private Category category;


}
