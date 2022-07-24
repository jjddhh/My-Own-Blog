package com.example.blog.entity;

import com.example.blog.dto.Req.ReqUpdateUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String oauth;

    @CreationTimestamp
    private Timestamp createDate;

    public void updateUser(ReqUpdateUserDto reqUpdateUserDto) {
        this.password = reqUpdateUserDto.getPassword();
        this.email = reqUpdateUserDto.getEmail();
    }
}
