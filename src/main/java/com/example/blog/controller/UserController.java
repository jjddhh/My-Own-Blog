package com.example.blog.controller;

import com.example.blog.config.auth.PrincipalDetail;
import com.example.blog.dto.KakaoProfile;
import com.example.blog.dto.OAuthToken;
import com.example.blog.dto.Req.ReqSignUpDto;
import com.example.blog.entity.RoleType;
import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class UserController {

    @Value("${kakao.password}")
    private String kakaoPassword;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "/user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "/user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        model.addAttribute("user", principalDetail.getUser());
        return "/user/updateForm";
    }

    /**
     * 카카오 로그인
     */
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(@RequestParam("code") String code) {

        /* 옛날 방식
        HttpsURLConnection urlConnection;
         */

        RestTemplate rt = new RestTemplate();

        // Http Header 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Http Body 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "f7d211268c8d63cb28a8d381be2d5bf2");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, httpHeaders);

        // Http Message 전송 후 응답값 받기.
        ResponseEntity<String> responseEntity = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(responseEntity.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RestTemplate rt2 = new RestTemplate();

        // Http Header 생성
        HttpHeaders httpHeaders2 = new HttpHeaders();
        httpHeaders2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        httpHeaders2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(httpHeaders2);

        // Http Message 전송 후 응답값 받기.
        ResponseEntity<String> responseEntity2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(responseEntity2.getBody(),KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ReqSignUpDto kakaoUser = ReqSignUpDto.builder()
                .username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .password(kakaoPassword)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        // 회원가입여부 체크.
        if (userService.회원가입여부(kakaoUser.getUsername()) == false) {
            userService.회원가입(kakaoUser);
        }

        // 로그인 처리.
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kakaoPassword));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

}
