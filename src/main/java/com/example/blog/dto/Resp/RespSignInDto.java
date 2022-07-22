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
public class RespSignInDto {

    private String username;

    private String password;

    private RoleType role;

    public static RespSignInDto of(User user) {
        return RespSignInDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .role(RoleType.USER)
                .build();
    }
}
