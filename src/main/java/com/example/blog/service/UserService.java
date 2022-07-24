package com.example.blog.service;

import com.example.blog.dto.Req.ReqSignInDto;
import com.example.blog.dto.Req.ReqSignUpDto;
import com.example.blog.dto.Req.ReqUpdateUserDto;
import com.example.blog.dto.Resp.RespSignInDto;
import com.example.blog.dto.Resp.RespSignUpDto;
import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Transactional
    public void 회원수정(ReqUpdateUserDto reqUpdateUserDto) {
        User updateUser = userRepository.findById(reqUpdateUserDto.getId())
                .orElseThrow(()-> new IllegalArgumentException("회원 찾기 실패"));

        // Kakao Oauth 유저들은 정보수정 불가.
        if(updateUser.getOauth() == null || updateUser.getOauth().equals("")){
            // password 암호화
            String encPassword = encoder.encode(reqUpdateUserDto.getPassword());
            reqUpdateUserDto.setPassword(encPassword);
            // dirty check 이용한 update.
            updateUser.updateUser(reqUpdateUserDto);
        }
    }

    @Transactional(readOnly = true)
    public Boolean 회원가입여부(String username) {
        User user = userRepository.findByUsername(username).orElseGet(()-> null);
        if(user == null) return false;
        else return true;
    }

    /*
    @Transactional(readOnly = true)
    public RespSignInDto 로그인(ReqSignInDto reqSignInDto) {
        User loginUser = userRepository.findByUsernameAndPassword(reqSignInDto.getUsername(), reqSignInDto.getPassword());
        return RespSignInDto.of(loginUser);
    }
    */
}
