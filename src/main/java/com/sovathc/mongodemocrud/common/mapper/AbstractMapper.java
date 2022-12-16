package com.sovathc.mongodemocrud.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface AbstractMapper<ENTITY, DTO, ITEM_RESPONSE, RESPONSE, CREATED, UPDATED, PAGEABLE> {

    void dtoToEntity(DTO dto, @MappingTarget ENTITY entity);
    void entityToDto(ENTITY entity, @MappingTarget DTO dto);
    void listDtoTOEntity(List<DTO> dtos, @MappingTarget List<ENTITY> entityList);
    void listEntityToDto(List<ENTITY> entities, @MappingTarget List<DTO> dtos);
    void dtoToItemResponse(DTO dto, @MappingTarget ITEM_RESPONSE itemResponse);
    void listDtoToListItemResponse(List<DTO> dtos, @MappingTarget List<ITEM_RESPONSE> responseList);
    void dtoToResponse(DTO dto, @MappingTarget RESPONSE response);
    void listDtoToResponse(List<DTO> dtos, @MappingTarget List<RESPONSE> responseList);
    void createdToDto(CREATED created, @MappingTarget DTO dto);
    void updatedToDto(UPDATED updated, @MappingTarget DTO dto);
    void pagableToDto(PAGEABLE pageable, @MappingTarget DTO dto);
}
