package com.hmdandelion.project_1410002.inventory.dto.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class MaterialSpecDTO {
    private Long specCode;
    private String materialName;
    private String remarks;
    private String unit;
    private String categoryName;
    private int safetyStock;
    private String specification;

    public static MaterialSpecDTO from(MaterialSpec materialSpec) {
        MaterialSpecDTO materialSpecDTO = new MaterialSpecDTO();
        materialSpecDTO.setSpecCode(materialSpec.getSpecCode());
        materialSpecDTO.setMaterialName(materialSpec.getMaterialName());
        materialSpecDTO.setRemarks(materialSpec.getRemarks());
        materialSpecDTO.setUnit(materialSpec.getUnit());
        materialSpecDTO.setCategoryName(materialSpec.getCategory().getCategoryName());
        materialSpecDTO.setSafetyStock(materialSpec.getSafetyStock());
        materialSpecDTO.setSpecification(materialSpec.getSpecification());
        return materialSpecDTO;
    }
}
