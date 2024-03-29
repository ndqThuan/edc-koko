package com.duro.edc_koko.models.category.service;

import com.duro.edc_koko.models.category.domain.Category;
import com.duro.edc_koko.models.category.model.CategoryDTO;
import com.duro.edc_koko.models.category.repos.CategoryRepository;
import com.duro.edc_koko.models.product.domain.Product;
import com.duro.edc_koko.models.product.repos.ProductRepository;
import com.duro.edc_koko.util.NotFoundException;
import com.duro.edc_koko.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(final CategoryRepository categoryRepository,
                           final ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<CategoryDTO> findAll() {
        final List<Category> categories = categoryRepository.findAll(Sort.by("id"));
        return categories.stream()
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .toList();
    }

    public CategoryDTO get(final Integer id) {
        return categoryRepository.findById(id)
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CategoryDTO categoryDTO) {
        final Category category = new Category();
        mapToEntity(categoryDTO, category);
        return categoryRepository.save(category).getId();
    }

    public void update(final Integer id, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }

    public void delete(final Integer id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO mapToDTO(final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    private Category mapToEntity(final CategoryDTO categoryDTO, final Category category) {
        category.setName(categoryDTO.getName());
        return category;
    }

    public boolean nameExists(final String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Product categoryProduct = productRepository.findFirstByCategory(category);
        if (categoryProduct != null) {
            referencedWarning.setKey("category.product.category.referenced");
            referencedWarning.addParam(categoryProduct.getId());
            return referencedWarning;
        }
        return null;
    }

}
