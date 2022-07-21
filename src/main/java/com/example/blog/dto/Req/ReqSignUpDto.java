package com.example.blog.dto.Req;

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
public class ReqSignUpDto {

    private String username;

    private String password;

    private String email;

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .role(RoleType.USER)
                .build();
    }
}
