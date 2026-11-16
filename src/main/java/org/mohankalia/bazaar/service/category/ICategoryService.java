package org.mohankalia.bazaar.service.category;

import org.mohankalia.bazaar.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Optional<Category> updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);

}
