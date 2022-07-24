package com.example.blog.dto.Req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReqReplyDto {

    private Long userId;
    private Long boardId;
    private String content;
}
