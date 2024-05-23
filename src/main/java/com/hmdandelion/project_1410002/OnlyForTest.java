package com.hmdandelion.project_1410002;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnlyForTest {

    @GetMapping("/")
    public String forTest() {
        return "민들레 테스트 입니다";
    }
}
