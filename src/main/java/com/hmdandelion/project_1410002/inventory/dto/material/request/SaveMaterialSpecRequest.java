package com.hmdandelion.project_1410002.inventory.dto.material.request;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SaveMaterialSpecRequest {

    private Long specCode;
    private String materialName;
    private long categoryCode;
    private String unit;
    private String specification;
    private String remarks;
    private Integer safetyQuantity;

    public SaveMaterialSpecRequest(String materialName, long categoryCode, String unit, String specification, String remarks, Integer safetyQuantity) {
        this.specCode = null;
        this.materialName = materialName;
        this.categoryCode = categoryCode;
        this.unit = unit;
        this.specification = specification;
        this.remarks = remarks;
        this.safetyQuantity = safetyQuantity;
    }
}
