package org.mohankalia.bazaar.repository;

import org.mohankalia.bazaar.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(Category category);
    Category findByName(String name);
}
