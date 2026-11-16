package org.mohankalia.bazaar.service.product;

import org.mohankalia.bazaar.model.Product;
import org.mohankalia.bazaar.requests.AddProductRequest;
import org.mohankalia.bazaar.requests.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long id);


    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String name);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String Name);

    Long countPrdductsByBrandAndName(String brand, String Name);


}
