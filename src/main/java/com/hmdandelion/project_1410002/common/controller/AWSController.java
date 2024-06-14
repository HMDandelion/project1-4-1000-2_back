package com.hmdandelion.project_1410002.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AWSController {

    @GetMapping("/api/v1/aws")
    public ResponseEntity<String> isOk() {
        return ResponseEntity.ok("정상운영중");
    }
}
