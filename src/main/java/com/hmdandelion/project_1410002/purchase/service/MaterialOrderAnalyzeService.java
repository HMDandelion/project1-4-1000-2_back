package com.hmdandelion.project_1410002.purchase.service;

import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialGraphModel;
import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlannedList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialOrderAnalyzeService {

    public List<MaterialGraphModel> findOrderByMaterialRequirementRatio(Long planCode) {
        //생산계획 리스트를 생산계획 코드로 조회한다 (생산하려는 제품과 그 숫자)
//        List<ProductionPlannedList> plannedLists =
        //생산하려는 제품의 BOM을 조회한다(생산하려는 제품의 요구수치)
        //필요 원자재 정보를 조회한다(원자재의 정보)
        //원자재의 이름과 총 필요량을 산출하여 데이터를 저장한다 called A
        //생산계획 코드로 원자재 주문을 조회한다
        //원자재 주문과 연결된 order-spec을 조회한다
        //order-spec리스트와 A를 비교하여 원자재 이름이 같을경우 주문수량에 orderSpec주문 수량을 누적한다
        //결과를 반환한다
        return null;
    }

}
