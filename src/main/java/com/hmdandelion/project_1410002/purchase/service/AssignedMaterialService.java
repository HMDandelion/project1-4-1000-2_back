package com.hmdandelion.project_1410002.purchase.service;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.AssignedMaterial;
import com.hmdandelion.project_1410002.purchase.domain.repository.material.AssignedMaterialRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignedMaterialService {

    private final AssignedMaterialRepo assignedMaterialRepo;

    @Transactional
    public void insertAssignedByClientCodeAndSpecList(Long clientCode, List<Long> specCodes) {
        for (Long specCode : specCodes) {
            AssignedMaterial assignedMaterial = AssignedMaterial.of(clientCode, specCode);
            assignedMaterialRepo.save(assignedMaterial);
        }
    }

    public void deleteAssignedByClientCode(Long clientCode) {
        assignedMaterialRepo.deleteAssignedByClientCode(clientCode);
    }

    public List<Long> findBySpecCode(Long specCode) {
        List<AssignedMaterial> assignedMaterials = assignedMaterialRepo.findBySpecCode(specCode);
        return assignedMaterials.stream().map(AssignedMaterial::getClientCode).toList();
    }
}
