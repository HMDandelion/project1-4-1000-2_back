package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.AssignedMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignedMaterialRepo extends JpaRepository<AssignedMaterial,Long>, AssignedMaterialRepoCustom {

    List<AssignedMaterial> findBySpecCode(Long specCode);
}
