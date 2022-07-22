package com.example.blog.dto.Req;

import com.example.blog.entity.Board;
import com.example.blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReqBoardDto {

    private String title;

    private String content;

    public Board toEntity(User user) {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .user(user)
                .build();
    }
}
