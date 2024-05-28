package com.hmdandelion.project_1410002.inventory.domian.repository.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialSpecRepoCustom {
    List<MaterialSpec> searchMaterialSpec(Pageable pageable, String materialName);

}
