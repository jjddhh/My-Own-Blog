package com.example.blog.service;

import com.example.blog.dto.Req.ReqSignUpDto;
import com.example.blog.dto.Resp.RespSignUpDto;
import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public RespSignUpDto 회원가입(ReqSignUpDto reqSignUpDto) {
        User user = userRepository.save(reqSignUpDto.toEntity());
        return RespSignUpDto.of(user);
    }
}
