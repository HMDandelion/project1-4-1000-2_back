package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.OrderSpecCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MaterialOrderRepoCustom {

    List<MaterialOrderDTO> findMaterialOrderBySpecCodeAndYearMonth(Long specCode, int year, int month);

    List<MaterialOrderDTO> getLast10OrderBySpecCode(long specCode);

    List<OrderSpec> getOrderSpecsByOrderCode(Long orderCode);

    Page<MaterialOrderDTO> gerOrders(Long planCode, String clientName, Pageable pageable);

    List<Long> findClientCodeBySpecCodes(List<Long> materialCodes);

    void setOrderSpec(Long orderCode, List<OrderSpecCreateDTO> orderSpecList);

    void deleteAllOrderSpecByOrderCode(Long orderCode);

    Page<MaterialOrderDTO> getOrderToday(Pageable pageable, LocalDate today);

    Map<String, Long> orderWeekly(LocalDate targetDate);
}
