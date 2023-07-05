package com.sovathc.mongodemocrud.common.mapper;

import com.sovathc.mongodemocrud.common.request.PageableRequest;
import com.sovathc.mongodemocrud.user.web.vo.request.UserCreateOrUpdateRequest;
import com.sovathc.mongodemocrud.user.web.vo.request.UserCreatedRequest;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface AbstractMapper<ENTITY, DTO, ITEM_RESPONSE, RESPONSE, CREATED, UPDATED, PAGEABLE extends PageableRequest> {

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
    void listRequestTOEntity(List<UserCreatedRequest> dtos, @MappingTarget List<ENTITY> entityList);
}
