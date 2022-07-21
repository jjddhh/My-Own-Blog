package com.example.blog.controller.api;

import com.example.blog.dto.Req.ReqSignUpDto;
import com.example.blog.dto.Resp.RespSignUpDto;
import com.example.blog.entity.RoleType;
import com.example.blog.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class UserApiControllerTest {

    @InjectMocks
    private UserApiController userApiController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userApiController).build();
    }

    @DisplayName("controller - 회원가입")
    @Test
    public void 회원가입() throws Exception {
        //given
        ReqSignUpDto request = reqSignUpDto();
        RespSignUpDto response = respSignUpDto();

        doReturn(response).when(userService)
                .회원가입(any(ReqSignUpDto.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        );

        //then
        MvcResult mvcResult = resultActions
                .andExpect(jsonPath("$.status", HttpStatus.OK.value()).exists())
                .andExpect(jsonPath("$.data.username", is(response.getUsername())))
                .andExpect(jsonPath("$.data.password", is(response.getPassword())))
                .andExpect(jsonPath("$.data.email", is(response.getEmail())))
                .andExpect(jsonPath("$.data.role", is("USER")))
                .andReturn();
    }

    private RespSignUpDto respSignUpDto() {
        return RespSignUpDto.builder()
                .username("test")
                .password("1234")
                .email("test@test.com")
                .role(RoleType.USER)
                .build();
    }

    private ReqSignUpDto reqSignUpDto() {
        return ReqSignUpDto.builder()
                .username("test")
                .password("1234")
                .email("test@test.com")
                .build();
    }
}