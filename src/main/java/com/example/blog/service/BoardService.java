package com.example.blog.service;

import com.example.blog.dto.Req.ReqBoardDto;
import com.example.blog.dto.Req.ReqReplyDto;
import com.example.blog.dto.Req.ReqUpdateBoardDto;
import com.example.blog.dto.Resp.RespBoardDto;
import com.example.blog.entity.Board;
import com.example.blog.entity.Reply;
import com.example.blog.entity.User;
import com.example.blog.repository.BoardRepository;
import com.example.blog.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public RespBoardDto 글쓰기(ReqBoardDto reqBoardDto, User user) {
        Board board = reqBoardDto.toEntity(user);
        Board savedBoard = boardRepository.save(board);
        return RespBoardDto.of(savedBoard);
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다."));
    }

    @Transactional
    public void 글삭제하기(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(Long id, ReqUpdateBoardDto reqUpdateBoardDto) {
        // 해당 글 영속화.
        Board foundBoard = boardRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("글 수정하기 실패 : 아이디를 찾을 수 없습니다."));
        // dirty check 이용한 수정.
        foundBoard.updateBoard(reqUpdateBoardDto);
    }

    @Transactional
    public void 댓글등록(User user, ReqReplyDto reqReplyDto) {
        /*
        Board board = boardRepository.findById(reqReplyDto.getBoardId())
                .orElseThrow(()-> new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다."));

        //이펙티브 자바 참고해서 문제점 파악해야함.
        Reply reply = Reply.of(user, board, reqReplyDto.getContent());
        */

        reqReplyDto.setUserId(user.getId());

        replyRepository.mSave(reqReplyDto.getUserId(), reqReplyDto.getBoardId(), reqReplyDto.getContent());
    }

    @Transactional
    public void 댓글삭제(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
