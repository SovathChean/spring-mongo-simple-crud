package com.sovathc.mongodemocrud.user.web.controller;

import com.sovathc.mongodemocrud.common.controller.AbstractController;
import com.sovathc.mongodemocrud.user.web.vo.request.UserCreatedRequest;
import com.sovathc.mongodemocrud.user.web.vo.request.UserPagableRequest;
import com.sovathc.mongodemocrud.user.web.vo.request.UserUpdatedRequest;
import com.sovathc.mongodemocrud.user.web.vo.response.UserItemResponse;
import com.sovathc.mongodemocrud.user.web.vo.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController implements AbstractController<UserItemResponse, UserResponse, UserCreatedRequest, UserUpdatedRequest, UserPagableRequest> {

}
