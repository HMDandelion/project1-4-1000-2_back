package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.AssignedMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignedMaterialRepo extends JpaRepository<AssignedMaterial,Long>, AssignedMaterialRepoCustom {
}
