package com.hmdandelion.project_1410002.purchase.service;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Bom;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialGraphModel;
import com.hmdandelion.project_1410002.inventory.service.BomService;
import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlannedList;
import com.hmdandelion.project_1410002.production.service.ProductionPlannedListService;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MaterialOrderAnalyzeService {
    private final ProductionPlannedListService plannedOrderListService;
    private final BomService bomService;
    private final MaterialOrderService materialOrderService;

    public List<MaterialGraphModel> findOrderByMaterialRequirementRatio(Long planCode) {
        List<MaterialGraphModel> result = new ArrayList<>();
        Map<String, Long> materialNeeds = new HashMap<>();
        //생산계획 리스트를 생산계획 코드로 조회한다 (생산하려는 제품과 그 숫자)
        List<ProductionPlannedList> plannedLists = plannedOrderListService.findByPlanCode(planCode);
        //생산하려는 제품의 BOM을 조회한다(생산하려는 제품의 요구수치)
        for (ProductionPlannedList plannedList : plannedLists) {
            List<Bom> bomlist = bomService.getBomByProductCode(plannedList.getProductCode());
            for (Bom bom : bomlist) {
                String materialName = bom.getMaterialSpec().getMaterialName();
                long requiredQuantity = bom.getQuantity();

                materialNeeds.put(materialName,
                                  (materialNeeds.getOrDefault(materialName, 0L) + requiredQuantity)
                                          * Integer.parseInt(plannedList.getPlannedQuantity()));
            }

        }
        //원자재의 이름과 총 필요량을 result에 추가
        for (Map.Entry<String, Long> entry : materialNeeds.entrySet()) {
            result.add(new MaterialGraphModel(entry.getKey(), 0, entry.getValue().intValue()));
        }
        //생산계획 코드로 원자재 주문을 조회한다
        List<MaterialOrder> orders = materialOrderService.findByPlanCode(planCode);
        //원자재 주문과 연결된 order-spec을 조회한다
        for (MaterialOrder order : orders) {
            List<OrderSpec> orderSpecs = materialOrderService.getOrderSpecsByOrderCode(order.getOrderCode());

            for (OrderSpec orderSpec : orderSpecs) {
                String materialName = orderSpec.getMaterialSpec().getMaterialName();
                int orderedQuantity = orderSpec.getOrderQuantity();

                for (MaterialGraphModel model : result) {
                    //order-spec리스트와 A를 비교하여 원자재 이름이 같을경우 주문수량에 orderSpec주문 수량을 누적한다
                    if (model.getNameValue().equals(materialName)) {
                        model.addSubject(orderedQuantity);
                    }
                }
            }
        }
        //결과를 반환한다
        return result;
    }


}
