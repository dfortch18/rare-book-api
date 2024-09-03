package com.dfortch.rarebook.service.impl;

import com.dfortch.rarebook.common.exception.RecordConflictException;
import com.dfortch.rarebook.common.exception.RecordNotFoundException;
import com.dfortch.rarebook.dto.entity.CategoryDTO;
import com.dfortch.rarebook.dto.request.CreateCategoryRequest;
import com.dfortch.rarebook.dto.request.GetCategoriesFilterOptions;
import com.dfortch.rarebook.dto.request.UpdateCategoryRequest;
import com.dfortch.rarebook.mapper.CategoryMapper;
import com.dfortch.rarebook.persistence.entity.Category;
import com.dfortch.rarebook.persistence.repository.CategoryRepository;
import com.dfortch.rarebook.service.CategoryService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;

    @Override
    public Page<CategoryDTO> getCategories(Pageable pageable, GetCategoriesFilterOptions filterOptions) {
        Page<Category> categoryPage;
        if (filterOptions != null) {
            categoryPage = categoryRepository.findAll((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.hasText(filterOptions.keyword())) {
                    String keywordPattern = "%" + filterOptions.keyword() + "%";
                    Predicate namePredicate = criteriaBuilder.like(root.get("name"), keywordPattern);
                    Predicate slugPredicate = criteriaBuilder.like(root.get("slug"), keywordPattern);
                    Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), keywordPattern);

                    predicates.add(criteriaBuilder.or(namePredicate, slugPredicate, descriptionPredicate));
                }

                if (predicates.isEmpty()) {
                    return criteriaBuilder.conjunction();
                }

                return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
            }, pageable);
        } else {
            categoryPage = categoryRepository.findAll(pageable);
        }
        return categoryPage.map(categoryMapper::mapToDTO);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Category.class));
        return categoryMapper.mapToDTO(category);
    }

    @Override
    public CategoryDTO getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new RecordNotFoundException(Category.class));
        return categoryMapper.mapToDTO(category);
    }

    @Override
    @Transactional
    public Long createCategory(CreateCategoryRequest requestDto) {
        Category.CategoryBuilder categoryBuilder = Category.builder()
                .name(requestDto.name())
                .slug(requestDto.slug());

        if (requestDto.description() != null && !requestDto.description().trim().isEmpty()) {
            categoryBuilder.description(requestDto.description());
        }

        Category category = categoryBuilder.build();

        Category savedCategory = categoryRepository.save(category);

        return savedCategory.getId();
    }

    @Override
    public void updateCategory(Long id, UpdateCategoryRequest requestDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Category.class));

        existingCategory.setName(requestDto.name());

        if (categoryRepository.existsBySlugAndIdNot(requestDto.slug(), id)) {
            Map<String, String> fieldErrors = new TreeMap<>();
            fieldErrors.put("slug", "field already used by other category");
            throw new RecordConflictException(Category.class, fieldErrors);
        }

        existingCategory.setSlug(requestDto.slug());

        if (requestDto.description() != null && !requestDto.description().trim().isEmpty()) {
            existingCategory.setDescription(requestDto.description());
        }

        categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        boolean categoryExists = categoryRepository.existsById(id);

        if (!categoryExists) {
            throw new RecordNotFoundException(Category.class);
        }

        categoryRepository.deleteById(id);
    }
}
