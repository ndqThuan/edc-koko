package com.duro.edc_koko.entity.category.service;

import com.duro.edc_koko.entity.category.domain.Category;
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

    public List<Category> findAll () {
        final List<Category> categories = categoryRepository.findAll(Sort.by("id"));
        return categories.stream().toList();
    }

    public List<Category> find5Categories () {
        final List<Category> categories = categoryRepository.find5Categories();
        return categories.stream().toList();
    }

    public Category get (final Integer id) {
        return categoryRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Integer create (final Category category) {
        return categoryRepository.save(category).getId();
    }

    public void delete (final Integer id) {
        categoryRepository.deleteById(id);
    }

}
