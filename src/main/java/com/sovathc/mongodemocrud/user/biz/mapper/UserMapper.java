package com.sovathc.mongodemocrud.user.biz.mapper;

import com.sovathc.mongodemocrud.common.mapper.AbstractMapper;
import com.sovathc.mongodemocrud.user.biz.dto.UserDTO;
import com.sovathc.mongodemocrud.user.biz.entity.UserEntity;
import com.sovathc.mongodemocrud.user.web.vo.request.UserCreatedRequest;
import com.sovathc.mongodemocrud.user.web.vo.request.UserPageableRequest;
import com.sovathc.mongodemocrud.user.web.vo.request.UserUpdatedRequest;
import com.sovathc.mongodemocrud.user.web.vo.response.UserItemResponse;
import com.sovathc.mongodemocrud.user.web.vo.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface UserMapper extends AbstractMapper<UserEntity, UserDTO, UserItemResponse, UserResponse, UserCreatedRequest, UserUpdatedRequest, UserPageableRequest> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
