package com.sovathc.mongodemocrud.user.biz.service.impl;

import com.sovathc.mongodemocrud.common.exception.BusinessException;
import com.sovathc.mongodemocrud.common.utils.FilterUtils;
import com.sovathc.mongodemocrud.common.utils.MongoEntityUtils;
import com.sovathc.mongodemocrud.common.utils.PageableUtils;
import com.sovathc.mongodemocrud.common.utils.SearchUtils;
import com.sovathc.mongodemocrud.user.biz.dto.UserDTO;
import com.sovathc.mongodemocrud.user.biz.dto.UserSearchDTO;
import com.sovathc.mongodemocrud.user.biz.entity.UserEntity;
import com.sovathc.mongodemocrud.user.biz.mapper.UserMapper;
import com.sovathc.mongodemocrud.user.biz.service.UserService;
import com.sovathc.mongodemocrud.user.web.vo.request.UserPagableRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

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
          List<UserDTO> userList = new ArrayList<>();
          Criteria criteria = new Criteria();

          //search keyword
          if(ObjectUtils.isNotEmpty(request.getKeywords()))
               SearchUtils.generate(criteria, UserSearchDTO.class, request.getKeywords());
          if(ObjectUtils.isNotEmpty(request.getFilter()))
               FilterUtils.generate(criteria, UserSearchDTO.class, request.getFilter());

          Page<UserEntity> page =  PageableUtils.generate(template, UserEntity.class, criteria, request);
          List<UserEntity> items = page.getContent();
          long record = page.getTotalElements();
          if(ObjectUtils.isNotEmpty(items) && record > 0)
          {
               UserMapper.INSTANCE.listEntityToDto(items, userList);
          }

          return new PageImpl<>(userList, page.getPageable(), record);
     }

     @Override
     public UserDTO findOne(String id) throws BusinessException {
          UserDTO userDTO = new UserDTO();
          UserEntity entity = MongoEntityUtils.findEntityById(template, UserEntity.class, id);
          UserMapper.INSTANCE.entityToDto(entity, userDTO);
          return userDTO;
     }
}
