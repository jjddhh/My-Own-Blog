package com.example.blog.service;

import com.example.blog.dto.Req.ReqSignInDto;
import com.example.blog.dto.Req.ReqSignUpDto;
import com.example.blog.dto.Resp.RespSignInDto;
import com.example.blog.dto.Resp.RespSignUpDto;
import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public RespSignUpDto 회원가입(ReqSignUpDto reqSignUpDto) {
        // password 암호화
        String encPassword = encoder.encode(reqSignUpDto.getPassword());
        reqSignUpDto.setPassword(encPassword);

        // 유저 추가
        User user = userRepository.save(reqSignUpDto.toEntity());
        return RespSignUpDto.of(user);
    }

    /*
    @Transactional(readOnly = true)
    public RespSignInDto 로그인(ReqSignInDto reqSignInDto) {
        User loginUser = userRepository.findByUsernameAndPassword(reqSignInDto.getUsername(), reqSignInDto.getPassword());
        return RespSignInDto.of(loginUser);
    }
    */
}
