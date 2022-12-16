package com.sovathc.mongodemocrud.user.biz.service.impl;

import com.sovathc.mongodemocrud.common.constants.SysHttpResultCode;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import com.sovathc.mongodemocrud.user.biz.dto.UserDTO;
import com.sovathc.mongodemocrud.user.biz.entity.UserEntity;
import com.sovathc.mongodemocrud.user.biz.mapper.UserMapper;
import com.sovathc.mongodemocrud.user.biz.repository.UserRepository;
import com.sovathc.mongodemocrud.user.biz.service.UserService;
import com.sovathc.mongodemocrud.user.web.vo.request.UserPagableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
     private final UserRepository repository;
     private final MongoTemplate template;

     @Override
     public UserDTO create(UserDTO dto) {
          UserEntity entity = new UserEntity();
          UserMapper.INSTANCE.dtoToEntity(dto, entity);
          template.save(entity);
          UserMapper.INSTANCE.entityToDto(entity, dto);
          return dto;
     }

     @Override
     public UserDTO update(String id, UserDTO userDTO) throws BusinessException {
          UserEntity entity = template.findById(id, UserEntity.class);
          if(ObjectUtils.isEmpty(entity))
               throw new BusinessException(SysHttpResultCode.ERROR_400.getCode(), "User Not found");
          UserMapper.INSTANCE.dtoToEntity(userDTO, entity);
          template.save(entity);
          UserMapper.INSTANCE.entityToDto(entity, userDTO);
          return userDTO;
     }

     @Override
     public void delete(String id) throws BusinessException {
          UserEntity entity = template.findById(id, UserEntity.class);
          if(ObjectUtils.isEmpty(entity))
               throw new BusinessException(SysHttpResultCode.ERROR_400.getCode(), "User Not found");
          template.remove(entity);
     }

     @Override
     public Page<UserDTO> findAll(UserDTO dto, UserPagableRequest request) {

          return null;
     }

     @Override
     public UserDTO findOne(String id) {
          UserDTO userDTO = new UserDTO();
          UserEntity entity = template.findById(id, UserEntity.class);
          UserMapper.INSTANCE.entityToDto(entity, userDTO);
          return userDTO;
     }
}
