package com.dfortch.rarebook.mapper.impl;

import org.springframework.stereotype.Component;

import com.dfortch.rarebook.dto.entity.EditionDTO;
import com.dfortch.rarebook.dto.entity.RareBookDTO;
import com.dfortch.rarebook.mapper.CategoryMapper;
import com.dfortch.rarebook.mapper.EditionMapper;
import com.dfortch.rarebook.mapper.RareBookMapper;
import com.dfortch.rarebook.persistence.entity.Category;
import com.dfortch.rarebook.persistence.entity.Edition;
import com.dfortch.rarebook.persistence.entity.RareBook;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RareBookMapperImpl implements RareBookMapper {

    private final CategoryMapper categoryMapper;

    private final EditionMapper editionMapper;

    @Override
    public RareBookDTO mapToDTO(RareBook rareBook) {
        return new RareBookDTO(
                rareBook.getId(),
                rareBook.getTitle(),
                rareBook.getAuthor(),
                categoryMapper.mapToDTO(rareBook.getCategory()),
                rareBook.getPublicationYear(),
                mapEditionsToDTO(rareBook.getEditions()),
                rareBook.getIsbn(),
                rareBook.getCondition().name(),
                rareBook.getRarity().name(),
                rareBook.getDescription(),
                rareBook.getPrice()
        );
    }

    @Override
    public RareBook mapToEntity(RareBookDTO rareBookDTO) {
        Category category = categoryMapper.mapToEntity(rareBookDTO.getCategory());

        return RareBook.builder()
                .id(rareBookDTO.getId())
                .title(rareBookDTO.getTitle())
                .author(rareBookDTO.getAuthor())
                .category(category)
                .publicationYear(rareBookDTO.getPublicationYear())
                .editions(mapDTOToEditions(rareBookDTO, rareBookDTO.getEditions()))
                .isbn(rareBookDTO.getIsbn())
                .condition(RareBook.Condition.valueOf(rareBookDTO.getCondition()))
                .rarity(RareBook.Rarity.valueOf(rareBookDTO.getRarity()))
                .description(rareBookDTO.getDescription())
                .price(rareBookDTO.getPrice())
                .build();
    }

    private List<EditionDTO> mapEditionsToDTO(List<Edition> editions) {
        return editions.stream().map(editionMapper::mapToDTO).collect(Collectors.toList());
    }

    private List<Edition> mapDTOToEditions(RareBookDTO rareBookDTO, List<EditionDTO> editionDTOs) {
        return editionDTOs.stream().map(editionDTO -> {
            editionDTO.setId(rareBookDTO.getId());
            return editionMapper.mapToEntity(editionDTO);
        }).collect(Collectors.toList());
    }
}
