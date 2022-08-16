package com.example.blog.repository;

import com.example.blog.entity.RoleType;
import com.example.blog.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Disabled
    @DisplayName("사용자 추가")
    //@Test
    public void saveUser() throws Exception {
        //given
        User user = user();

        //when
        User savedUser = userRepository.save(user);

        //then
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getRole()).isEqualTo(user.getRole());
    }

    private User user() {
        return User.builder()
                .username("test")
                .password("1234")
                .email("test@test.com")
                .role(RoleType.USER)
                .build();
    }
}