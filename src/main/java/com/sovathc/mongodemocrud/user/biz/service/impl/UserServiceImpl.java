package com.sovathc.mongodemocrud.user.biz.service.impl;

import com.sovathc.mongodemocrud.common.exception.BusinessException;
import com.sovathc.mongodemocrud.common.utils.MongoEntityUtils;
import com.sovathc.mongodemocrud.user.biz.dto.UserDTO;
import com.sovathc.mongodemocrud.user.biz.entity.UserEntity;
import com.sovathc.mongodemocrud.user.biz.mapper.UserMapper;
import com.sovathc.mongodemocrud.user.biz.service.UserService;
import com.sovathc.mongodemocrud.user.web.vo.request.UserPagableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
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
          UserEntity entity = MongoEntityUtils.findEntityById(template, UserEntity.class, id);
          UserMapper.INSTANCE.dtoToEntity(userDTO, entity);
          template.save(entity);
          UserMapper.INSTANCE.entityToDto(entity, userDTO);
          return userDTO;
     }

     @Override
     public void delete(String id) throws BusinessException {
          UserEntity entity = MongoEntityUtils.findEntityById(template, UserEntity.class, id);
          template.remove(entity);
     }

     @Override
     public Page<UserDTO> findAll(UserDTO dto, UserPagableRequest request) {

          return null;
     }

     @Override
     public UserDTO findOne(String id) throws BusinessException {
          UserDTO userDTO = new UserDTO();
          UserEntity entity = MongoEntityUtils.findEntityById(template, UserEntity.class, id);
          UserMapper.INSTANCE.entityToDto(entity, userDTO);
          return userDTO;
     }
}
