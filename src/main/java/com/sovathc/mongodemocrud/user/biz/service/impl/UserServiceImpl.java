package com.sovathc.mongodemocrud.user.biz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sovathc.mongodemocrud.common.constants.SysHttpResultCode;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import com.sovathc.mongodemocrud.common.utils.*;
import com.sovathc.mongodemocrud.user.biz.dto.UserDTO;
import com.sovathc.mongodemocrud.user.biz.dto.UserSearchDTO;
import com.sovathc.mongodemocrud.user.biz.entity.UserEntity;
import com.sovathc.mongodemocrud.user.biz.mapper.UserMapper;
import com.sovathc.mongodemocrud.user.biz.service.PdfGeneratorService;
import com.sovathc.mongodemocrud.user.biz.service.UserService;
import com.sovathc.mongodemocrud.user.web.vo.request.UserPageableRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
     private final MongoTemplate template;
     private final PdfGeneratorService pdfGeneratorService;
     private final PdfGeneratorUtils utils;

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
     public Page<UserDTO> findAll(UserDTO dto, UserPageableRequest request) throws BusinessException {
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
     @Override
     public void generateUserPdf(String id) throws BusinessException
     {
          UserEntity userEntity = MongoEntityUtils.findEntityById(template, UserEntity.class, id);
          Map<String, Object> data = new HashMap<>();
          data.put("user", userEntity);

          pdfGeneratorService.generatePdfFile("user", data, "user.pdf");
     }
     @Override
     public HttpEntity<byte[]> downloadPDF(String id)throws BusinessException
     {
          UserEntity userEntity = MongoEntityUtils.findEntityById(template, UserEntity.class, id);
          Map<String, Object> data = new HashMap<>();
          data.put("user", userEntity);
          try
          {
               byte[] bytes = utils.downloadPDF("user", data);
               HttpHeaders headers = new HttpHeaders();
               headers.setContentDisposition(ContentDisposition.builder("inline").filename("user.pdf").build());
               headers.setContentType(MediaType.APPLICATION_PDF);
               headers.setContentLength(bytes.length);

               return new HttpEntity<>(bytes, headers);
          }
          catch(Exception e) {
               throw new BusinessException(SysHttpResultCode.ERROR_500.getCode(), "Download pdf failed");
          }

     }
}
