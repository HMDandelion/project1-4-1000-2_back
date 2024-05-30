package com.hmdandelion.project_1410002.purchase.presentation;

import com.hmdandelion.project_1410002.purchase.service.MaterialOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/material")
@RequiredArgsConstructor
public class MaterialOrderController {

    private final MaterialOrderService materialOrderService;

}
