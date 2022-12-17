package com.sovathc.mongodemocrud.user.biz.service;

import com.sovathc.mongodemocrud.common.exception.BusinessException;
import com.sovathc.mongodemocrud.user.biz.dto.UserDTO;
import com.sovathc.mongodemocrud.user.web.vo.request.UserPagableRequest;
import org.springframework.data.domain.Page;

public interface UserService {
    UserDTO create(UserDTO dto);
    UserDTO update(String id, UserDTO userDTO) throws BusinessException;
    void delete(String id) throws BusinessException;
    Page<UserDTO> findAll(UserDTO dto, UserPagableRequest request) throws BusinessException;
    UserDTO findOne(String id) throws BusinessException;

}
