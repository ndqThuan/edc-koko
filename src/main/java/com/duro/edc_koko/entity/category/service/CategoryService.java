package com.duro.edc_koko.entity.category.service;

import com.duro.edc_koko.entity.category.domain.Category;
import com.duro.edc_koko.entity.category.model.CategoryDTO;
import com.duro.edc_koko.entity.category.repos.CategoryRepository;
import com.duro.edc_koko.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll () {
        final List<Category> categories = categoryRepository.findAll(Sort.by("id"));
        return categories.stream()
                         .map(category -> mapToDTO(category, new CategoryDTO()))
                         .toList();
    }

    public List<CategoryDTO> find5Categories () {
        final List<Category> categories = categoryRepository.find5Categories();
        return categories.stream()
                         .map(category -> mapToDTO(category, new CategoryDTO()))
                         .toList();
    }

    public CategoryDTO get (final Integer id) {
        return categoryRepository.findById(id)
                                 .map(category -> mapToDTO(category, new CategoryDTO()))
                                 .orElseThrow(NotFoundException::new);
    }

    public Integer create (final CategoryDTO categoryDTO) {
        final Category category = new Category();
        mapToEntity(categoryDTO, category);
        return categoryRepository.save(category).getId();
    }

    public void update (final Integer id, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(id)
                                                    .orElseThrow(NotFoundException::new);
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }

    public void delete (final Integer id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO mapToDTO (final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    private Category mapToEntity (final CategoryDTO categoryDTO, final Category category) {
        category.setName(categoryDTO.getName());
        return category;
    }
}
