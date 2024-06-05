package com.hmdandelion.project_1410002.production.dto.material.response;

import com.hmdandelion.project_1410002.production.domain.entity.material.MaterialUsage;
import com.hmdandelion.project_1410002.production.domain.type.MaterialUsageStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialUsageResponse {
    //사용
    //사용코드
    //사용일자
    //사용상태
    private final Long usageCode;
    private final LocalDateTime usageDatetime;
    private MaterialUsageStatus status;

    //사원 - 사용의 employeeCode
    //이름
    //부서명
    //직책명
    //연락처
    private final String employeeName;
    private final String positionName;
    private final String departmentName;
    private final String phone;

    //라인 - 사용의 workOrder.lineCode -> 라인의 name
    //라인코드
    //라인명
    private final String lineName;

    //BOM : List - 사용의 workOrder.product.productCode -> BOM정보
    //스펙코드
    //자재명
    //분류명
    private final Long specCode;
    private final String materialName;
    private final String categoryName;
    private final long quantity;

}
