package com.hmdandelion.project_1410002.inventory.domian.repository.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.dto.material.MaterialSpecDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialSpecCustom {
    List<MaterialSpec> searchMaterialSpec(Pageable pageable, String materialName);

}
