package com.sovathc.mongodemocrud.user.biz.service;

import com.sovathc.mongodemocrud.common.exception.BusinessException;
import com.sovathc.mongodemocrud.user.biz.dto.UserDTO;
import com.sovathc.mongodemocrud.user.web.vo.request.UserCreateOrUpdateRequest;
import com.sovathc.mongodemocrud.user.web.vo.request.UserPageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface UserService {
    UserDTO create(UserDTO dto);
    UserDTO update(String id, UserDTO userDTO) throws BusinessException;
    void delete(String id) throws BusinessException;
    Page<UserDTO> findAll(UserDTO dto, UserPageableRequest request) throws BusinessException;
    UserDTO findOne(String id) throws BusinessException;

    void generateUserPdf(String id) throws BusinessException;
    HttpEntity<byte[]> downloadPDF(String templateName, String id)throws BusinessException;
    void generatePDF(String templateName, String id)throws BusinessException;
    HttpEntity<byte[]> downloadJapserPDF()throws BusinessException;
    AggregationResults<Map> testAggregationMongo();

    void createOrUpdate(UserCreateOrUpdateRequest dto);
}
