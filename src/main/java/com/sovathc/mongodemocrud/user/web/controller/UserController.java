package com.sovathc.mongodemocrud.user.web.controller;

import com.sovathc.mongodemocrud.common.controller.AbstractController;
import com.sovathc.mongodemocrud.common.controller.ResponseBuilderMessage;
import com.sovathc.mongodemocrud.common.controller.ResponseMessage;
import com.sovathc.mongodemocrud.common.response.PageableResponse;
import com.sovathc.mongodemocrud.user.biz.dto.UserDTO;
import com.sovathc.mongodemocrud.user.biz.mapper.UserMapper;
import com.sovathc.mongodemocrud.user.biz.service.UserService;
import com.sovathc.mongodemocrud.user.web.vo.request.UserCreatedRequest;
import com.sovathc.mongodemocrud.user.web.vo.request.UserPageableRequest;
import com.sovathc.mongodemocrud.user.web.vo.request.UserUpdatedRequest;
import com.sovathc.mongodemocrud.user.web.vo.response.UserItemResponse;
import com.sovathc.mongodemocrud.user.web.vo.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "User")
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController implements AbstractController<UserItemResponse, UserResponse, UserCreatedRequest, UserUpdatedRequest, UserPageableRequest> {
    private final UserService service;
    @SneakyThrows
    @Override
    public ResponseMessage<UserResponse> findOne(String id)
    {
        UserDTO userResponseDTO = this.service.findOne(id);
        UserResponse response = new UserResponse();
        UserMapper.INSTANCE.dtoToResponse(userResponseDTO, response);

        return new ResponseBuilderMessage<UserResponse>()
                .success().addData(response).build();
    }
    @SneakyThrows
    @Override
    public ResponseMessage<PageableResponse<UserItemResponse>> findWithPage(UserPageableRequest request)
    {
        UserDTO userDTO = new UserDTO();
        UserMapper.INSTANCE.pagableToDto(request, userDTO);
        Page<UserDTO> page = this.service.findAll(userDTO, request);
        List<UserItemResponse> responseList = new ArrayList<>();
        UserMapper.INSTANCE.listDtoToListItemResponse(page.getContent(), responseList);
        PageableResponse<UserItemResponse> response = new PageableResponse<>(page.getTotalElements(), responseList, request);

        return new ResponseBuilderMessage<PageableResponse<UserItemResponse>>()
                .success().addData(response).build();
    }
    @SneakyThrows
    @Override
    public ResponseMessage<UserResponse> create(UserCreatedRequest request)
    {
        UserDTO userDTO = new UserDTO();
        UserMapper.INSTANCE.createdToDto(request, userDTO);
        UserDTO userResponseDTO = this.service.create(userDTO);
        UserResponse response = new UserResponse();
        UserMapper.INSTANCE.dtoToResponse(userResponseDTO, response);

        return new ResponseBuilderMessage<UserResponse>()
                .success().addData(response).build();
    }
    @SneakyThrows
    @Override
    public ResponseMessage<UserResponse> update(String id, UserUpdatedRequest request)
    {
        UserDTO userDTO = new UserDTO();
        UserMapper.INSTANCE.updatedToDto(request, userDTO);
        UserDTO userResponseDTO = this.service.update(id, userDTO);
        UserResponse response = new UserResponse();
        UserMapper.INSTANCE.dtoToResponse(userResponseDTO, response);
        return new ResponseBuilderMessage<UserResponse>()
                .success().addData(response).build();
    }

    @SneakyThrows
    @Override
    public ResponseMessage<Void> delete(String id)
    {
        this.service.delete(id);
        return new ResponseBuilderMessage<Void>()
                .success().build();
    }
    @SneakyThrows
    @GetMapping("/pdf/{id}")
    public ResponseMessage<Void> generatePdf(@PathVariable String id)
    {
        this.service.generateUserPdf(id);

        return new ResponseBuilderMessage<Void>()
                .success().build();
    }
    @SneakyThrows
    @GetMapping(value = "/download/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public HttpEntity<byte[]> downloadPDF(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
    {

        return this.service.downloadPDF(id);
    }
}
