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
public class ReqUpdateUserDto {

    private Long id;

    private String username;

    private String password;

    private String email;

}
