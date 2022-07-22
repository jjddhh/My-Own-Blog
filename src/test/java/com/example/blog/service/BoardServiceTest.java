package com.example.blog.service;

import com.example.blog.dto.Req.ReqBoardDto;
import com.example.blog.dto.Resp.RespBoardDto;
import com.example.blog.entity.Board;
import com.example.blog.entity.RoleType;
import com.example.blog.entity.User;
import com.example.blog.repository.BoardRepository;
import com.example.blog.repository.UserRepository;
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
class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @DisplayName("service - 글쓰기")
    @Test
    public void 글쓰기() throws Exception {
        //given
        User user = User.builder()
                .username("test")
                .password("1234")
                .email("test@test.com")
                .role(RoleType.USER)
                .build();
        ReqBoardDto request = reqBoardDto();

        doReturn(request.toEntity(user)).when(boardRepository)
                .save(any(Board.class));

        //when
        RespBoardDto  respBoardDto= boardService.글쓰기(request, user);

        //then
        assertThat(respBoardDto.getTitle()).isEqualTo(request.getTitle());
        assertThat(respBoardDto.getContent()).isEqualTo(request.getContent());

    }

    private ReqBoardDto reqBoardDto() {
        return ReqBoardDto.builder()
                .title("제목")
                .content("내용")
                .build();
    }
}