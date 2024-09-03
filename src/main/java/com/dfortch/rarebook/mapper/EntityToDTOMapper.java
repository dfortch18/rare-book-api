package com.dfortch.rarebook.mapper;

public interface EntityToDTOMapper<Entity, DTO> {

    DTO mapToDTO(Entity entity);

    Entity mapToEntity(DTO dto);
}
