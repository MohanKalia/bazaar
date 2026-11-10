package org.mohankalia.bazaar.service.product;

import lombok.RequiredArgsConstructor;
import org.mohankalia.bazaar.Exceptions.ProductNotFoundException;
import org.mohankalia.bazaar.model.Category;
import org.mohankalia.bazaar.model.Product;
import org.mohankalia.bazaar.repository.CategoryRepository;
import org.mohankalia.bazaar.repository.ProductRepository;
import org.mohankalia.bazaar.requests.AddProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest product) {
        // check if category is found! in the database
        // if yes set it as the new product Category
        // if no then save it as a new category then set as the new product in the category

        Category category = Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(product.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        product.setCategory(category);
        return productRepository.save(createProduct(product, category));

    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getInventory(),
                request.getDescription(),
                request.getPrice(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {

        return productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () -> {
                    throw new ProductNotFoundException("Product Not Found");
                });
    }

    @Override
    public void updateProduct(Product product, Long id) {

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String name) {
        return productRepository.findByBrand(name);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countPrdductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
