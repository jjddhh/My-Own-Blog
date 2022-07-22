package com.example.blog.controller.api;

import com.example.blog.dto.Req.ReqBoardDto;
import com.example.blog.dto.Resp.RespBoardDto;
import com.example.blog.entity.RoleType;
import com.example.blog.entity.User;
import com.example.blog.service.BoardService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class BoardApiControllerTest {

    @InjectMocks
    private BoardApiController boardApiController;

    @Mock
    private BoardService boardService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(boardApiController).build();
    }

    @DisplayName("controller - 글쓰기")
    @Test
    public void 글쓰기() throws Exception {
        //given
        ReqBoardDto request = reqBoardDto();
        RespBoardDto response = respBoardDto();

        doReturn(response).when(boardService)
                .글쓰기(any(ReqBoardDto.class), any());

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        );

        //then
        MvcResult mvcResult = resultActions
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.title", is(response.getTitle())))
                .andExpect(jsonPath("$.data.content", is(response.getContent())))
                .andReturn();

    }

    private RespBoardDto respBoardDto() {
        return RespBoardDto.builder()
                .title("제목")
                .content("내용")
                .build();
    }

    private ReqBoardDto reqBoardDto() {
        return ReqBoardDto.builder()
                .title("제목")
                .content("내용")
                .build();
    }
}