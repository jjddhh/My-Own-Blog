package com.example.blog.service;

import com.example.blog.controller.api.UserApiController;
import com.example.blog.dto.Req.ReqSignUpDto;
import com.example.blog.dto.Resp.RespSignUpDto;
import com.example.blog.entity.RoleType;
import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @DisplayName("Service - 회원가입")
    @Test
    public void 회원가입() throws Exception {
        //given
        ReqSignUpDto request = reqSignUpDto();

        doReturn(request.toEntity()).when(userRepository)
                .save(any());

        //when
        RespSignUpDto user = userService.회원가입(request);

        //then
        assertThat(user.getUsername()).isEqualTo(request.getUsername());
        assertThat(user.getPassword()).isEqualTo(request.getPassword());
        assertThat(user.getEmail()).isEqualTo(request.getEmail());
        assertThat(user.getRole()).isEqualTo(RoleType.USER);
    }

    private ReqSignUpDto reqSignUpDto() {
        return ReqSignUpDto.builder()
                .username("test")
                .password("1234")
                .email("test@test.com")
                .build();
    }
}