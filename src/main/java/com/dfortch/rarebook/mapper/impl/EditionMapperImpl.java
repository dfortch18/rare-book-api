package com.dfortch.rarebook.mapper.impl;

import org.springframework.stereotype.Component;

import com.dfortch.rarebook.dto.entity.EditionDTO;
import com.dfortch.rarebook.mapper.EditionMapper;
import com.dfortch.rarebook.persistence.entity.Edition;

@Component
public class EditionMapperImpl implements EditionMapper {

    @Override
    public EditionDTO mapToDTO(Edition edition) {
        return new EditionDTO(edition.getId(), edition.getEditionNumber(), edition.getPublicationYear(), edition.getNotes());
    }

    @Override
    public Edition mapToEntity(EditionDTO editionDTO) {
        return Edition.builder()
                .id(editionDTO.getId())
                .editionNumber(editionDTO.getEditionNumber())
                .publicationYear(editionDTO.getPublicationYear())
                .notes(editionDTO.getNotes())
                .build();
    }
}
