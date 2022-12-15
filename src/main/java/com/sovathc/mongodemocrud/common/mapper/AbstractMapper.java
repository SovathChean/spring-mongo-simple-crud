package com.sovathc.mongodemocrud.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface AbstractMapper<ENTITY, DTO, ITEM_RESPONSE, RESPONSE, CREATED, UPDATED, PAGEABLE> {

    ENTITY dtoToEntity(DTO dto);
    DTO entityToDto(ENTITY entity);
    List<ENTITY> listDtoTOEntity(List<DTO> dtos);
    List<DTO> listEntityToDto(List<ENTITY> entities);
    ITEM_RESPONSE dtoToItemResponse(DTO dto);
    List<ITEM_RESPONSE> listDtoToListItemResponse(List<DTO> dtos);
    RESPONSE dtoToResponse(DTO dto);
    List<RESPONSE> listDtoToResponse(List<DTO> dtos);
    DTO createdToDto(CREATED created);
    DTO updatedToDto(UPDATED updated);
    PAGEABLE pagableToDto(PAGEABLE pageable);
}
