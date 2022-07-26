package com.example.blog.controller.api;

import com.example.blog.config.auth.PrincipalDetail;
import com.example.blog.dto.CommonResponseDto;
import com.example.blog.dto.Req.ReqBoardDto;
import com.example.blog.dto.Req.ReqReplyDto;
import com.example.blog.dto.Req.ReqSignUpDto;
import com.example.blog.dto.Req.ReqUpdateBoardDto;
import com.example.blog.dto.Resp.RespBoardDto;
import com.example.blog.dto.Resp.RespSignUpDto;
import com.example.blog.service.BoardService;
import com.example.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public CommonResponseDto<?> 글쓰기(@RequestBody ReqBoardDto reqBoardDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        RespBoardDto respBoardDto = boardService.글쓰기(reqBoardDto,principalDetail.getUser());
        return new CommonResponseDto<>(HttpStatus.OK.value(), respBoardDto);
    }

    @DeleteMapping("/api/board/{id}")
    public CommonResponseDto<?> 글삭제하기(@PathVariable Long id) {
        boardService.글삭제하기(id);
        return new CommonResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public CommonResponseDto<?> 글수정하기(@PathVariable Long id, @RequestBody ReqUpdateBoardDto reqUpdateBoardDto) {
        boardService.글수정하기(id, reqUpdateBoardDto);
        return new CommonResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{boardId}/reply")
    public CommonResponseDto<?> 댓글등록(@RequestBody ReqReplyDto reqReplyDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        boardService.댓글등록(principalDetail.getUser(), reqReplyDto);
        return new CommonResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public CommonResponseDto<?> 댓글삭제(@PathVariable Long replyId) {
        boardService.댓글삭제(replyId);
        return new CommonResponseDto<>(HttpStatus.OK.value(), 1);
    }

}
