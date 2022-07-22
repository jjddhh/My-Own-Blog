package com.example.blog.controller;

import com.example.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "/board/saveForm";
    }
}
