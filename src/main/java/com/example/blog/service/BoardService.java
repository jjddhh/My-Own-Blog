package com.example.blog.service;

import com.example.blog.dto.Req.ReqBoardDto;
import com.example.blog.dto.Req.ReqSignUpDto;
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

    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
}
