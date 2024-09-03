package com.dfortch.rarebook.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryDTO {

    private Long id;

    private String name;

    private String slug;
    
    private String description;
}
