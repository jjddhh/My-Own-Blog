package com.example.blog.dto.Resp;

import com.example.blog.entity.RoleType;
import com.example.blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RespSignUpDto {

    private String username;

    private String password;

    private String email;

    private RoleType role;

    public static RespSignUpDto of(User user) {
        return RespSignUpDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .role(RoleType.USER)
                .build();
    }
}
