package com.example.blog.repository;

import com.example.blog.entity.Board;
import com.example.blog.entity.RoleType;
import com.example.blog.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Disabled
    @DisplayName("글 추가")
    @Test
    public void saveBoard() throws Exception {
        //given
        Board board = board();

        //when
        Board savedBoard = boardRepository.save(board);

        //then
        assertThat(savedBoard.getTitle()).isEqualTo(board.getTitle());
        assertThat(savedBoard.getContent()).isEqualTo(board.getContent());
        assertThat(savedBoard.getUser()).isEqualTo(board.getUser());

    }

    private Board board() {
        return Board.builder()
                .title("제목")
                .content("내용")
                .user(user())
                .build();
    }

    private User user() {
        return User.builder()
                .username("test")
                .password("1234")
                .email("test@test.com")
                .role(RoleType.USER)
                .build();
    }
}