package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.MaterialSpecRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.MaterialSpecDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialSpecService {
    private final MaterialSpecRepo materialSpecRepo;

    public List<MaterialSpecDTO> findAll() {
        return null;
    }

    @Transactional
    public List<MaterialSpecDTO> searchMaterialSpec(Pageable pageable, String materialName) {
        List<MaterialSpec> specs = materialSpecRepo.searchMaterialSpec(pageable, materialName);
        return specs.stream().map(MaterialSpecDTO::from).toList();
    }
}
