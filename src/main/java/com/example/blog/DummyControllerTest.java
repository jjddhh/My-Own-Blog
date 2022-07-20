package com.example.blog;

import com.example.blog.entity.RoleType;
import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DummyControllerTest {

    private final UserRepository userRepository;

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);
        /**
         * page 관련 처리.(isFirst, isLast...)
         */
        List<User> users = pagingUser.getContent();
        return users;
    }

    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저는 없습니다. id : " + id));
        return user;
    }

    @PostMapping("/dummy/join")
    public User join(User user) {
        user.setRole(RoleType.USER);
        return userRepository.save(user);
    }

    @Transactional
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User requestUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("수정에 실패하였습니다."));

        // dirty check
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        return user;
    }

    @Transactional
    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable Long id) {

        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다.";
        }

        return "삭제되었습니다. id : " + id;
    }
}
