package com.dfortch.rarebook.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RareBookDTO {

    private Long id;

    private String title;

    private String author;

    private CategoryDTO category;

    private Year publicationYear;

    @Builder.Default
    private List<EditionDTO> editions = new ArrayList<>();

    private String isbn;

    private String condition;

    private String rarity;

    private String description;
    
    private Double price;
}
