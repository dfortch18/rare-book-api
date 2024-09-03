package com.dfortch.rarebook.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EditionDTO {

    private Long id;

    private Integer editionNumber;

    private Year publicationYear;
    
    private String notes;
}
