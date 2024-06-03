package com.hmdandelion.project_1410002.inventory.domian.repository.material.spec;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MaterialSpecRepoCustom {

    List<MaterialSpec> searchMaterialSpec(Pageable pageable, String materialName);

    long removeByList(List<Long> specCodes);

    Long getUsingSepcCode(List<Long> specCodes);

    Map<Long, List<MaterialSpecDTO>> getSpecByclientCodes(List<Long> clientCodes);
}
