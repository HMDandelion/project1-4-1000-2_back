package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.inventory.domian.repository.material.MaterialSpecRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.MaterialSpecDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialSpecService {
    private final MaterialSpecRepo materialSpecRepo;

    public List<MaterialSpecDTO> findAll() {
        return null;
    }
}
