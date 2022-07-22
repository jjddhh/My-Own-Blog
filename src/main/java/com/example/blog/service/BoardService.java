package com.example.blog.service;

import com.example.blog.dto.Req.ReqBoardDto;
import com.example.blog.dto.Req.ReqSignUpDto;
import com.example.blog.dto.Req.ReqUpdateBoardDto;
import com.example.blog.dto.Resp.RespBoardDto;
import com.example.blog.dto.Resp.RespSignUpDto;
import com.example.blog.entity.Board;
import com.example.blog.entity.User;
import com.example.blog.repository.BoardRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

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
}
