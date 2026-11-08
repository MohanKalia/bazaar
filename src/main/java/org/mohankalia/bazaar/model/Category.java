package org.mohankalia.bazaar.model;

import java.util.List;

// A category can have multiple products
// adding an id and a name for category
// as well as a list of products that belong to the category
public class Category {

    private Long id;
    private String name;

    private List<Product> products;



}
