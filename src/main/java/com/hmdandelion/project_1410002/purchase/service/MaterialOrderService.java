package com.hmdandelion.project_1410002.purchase.service;

import com.hmdandelion.project_1410002.purchase.domain.repository.material.MaterialOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MaterialOrderService {
    private final MaterialOrderRepo materialOrderRepo;

}
