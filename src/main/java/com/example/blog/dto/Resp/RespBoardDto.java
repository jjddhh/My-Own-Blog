package com.example.blog.dto.Resp;

import com.example.blog.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RespBoardDto {

    private String title;

    private String content;

    public static RespBoardDto of(Board savedBoard) {
        return RespBoardDto.builder()
                .title(savedBoard.getTitle())
                .content(savedBoard.getContent())
                .build();
    }
}

