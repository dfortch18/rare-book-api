package com.dfortch.rarebook.mapper.impl;

import org.springframework.stereotype.Component;

import com.dfortch.rarebook.dto.entity.CategoryDTO;
import com.dfortch.rarebook.mapper.CategoryMapper;
import com.dfortch.rarebook.persistence.entity.Category;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDTO mapToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getSlug(), category.getDescription());
    }

    @Override
    public Category mapToEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .slug(categoryDTO.getSlug())
                .description(categoryDTO.getDescription())
                .build();
    }
}
