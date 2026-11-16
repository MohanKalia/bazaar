package org.mohankalia.bazaar.service.category;

import lombok.RequiredArgsConstructor;
import org.mohankalia.bazaar.Exceptions.AlreadyExistsException;
import org.mohankalia.bazaar.Exceptions.ResourceNotFoundException;
import org.mohankalia.bazaar.model.Category;
import org.mohankalia.bazaar.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
    return Optional.of(category).filter(i -> !categoryRepository.existsByName(i.getName()))
            .map(categoryRepository::save).orElseThrow(()-> new AlreadyExistsException("Category already exists!"));
    }

    @Override
    public Optional<Category> updateCategory(Category category, Long id) {
        return Optional.of(categoryRepository.findById(id)
                .map(i -> {
                    i.setName(category.getName());
                    return categoryRepository.save(i);
                }).orElseThrow(()-> new ResourceNotFoundException("Category not Found!")));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Category not found");
                        });
    }
}
