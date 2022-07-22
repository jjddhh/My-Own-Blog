package com.example.blog.controller.api;

import com.example.blog.config.auth.PrincipalDetail;
import com.example.blog.dto.CommonResponseDto;
import com.example.blog.dto.Req.ReqSignInDto;
import com.example.blog.dto.Req.ReqSignUpDto;
import com.example.blog.dto.Req.ReqUpdateUserDto;
import com.example.blog.dto.Resp.RespSignInDto;
import com.example.blog.dto.Resp.RespSignUpDto;
import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/auth/joinProc")
    public CommonResponseDto<?> 회원가입(@RequestBody ReqSignUpDto reqSignUpDto) {
        RespSignUpDto respSignUpDto = userService.회원가입(reqSignUpDto);
        return new CommonResponseDto<>(HttpStatus.OK.value(), respSignUpDto);
    }

    @PutMapping("/user")
    public CommonResponseDto<?> 회원수정(@RequestBody ReqUpdateUserDto reqUpdateUserDto) {
        String username = reqUpdateUserDto.getUsername();
        String password = reqUpdateUserDto.getPassword();
        userService.회원수정(reqUpdateUserDto);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new CommonResponseDto<>(HttpStatus.OK.value(), 1);
    }

    /*
    @PostMapping("/api/user/login")
    public CommonResponseDto<?> 로그인(@RequestBody ReqSignInDto reqSignInDto) {

        RespSignInDto respSignInDto = userService.로그인(reqSignInDto);
        return new CommonResponseDto<>(HttpStatus.OK.value(), respSignInDto);
    }
    */
}
