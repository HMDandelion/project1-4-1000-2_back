package com.hmdandelion.project_1410002.purchase.service;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import com.hmdandelion.project_1410002.purchase.domain.repository.material.MaterialOrderRepo;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class MaterialOrderService {

    private static final Logger log = LoggerFactory.getLogger(MaterialOrderService.class);
    private final MaterialOrderRepo materialOrderRepo;

    public Map<String, Double> getMonthTransactionsBySpecCode(long specCode) {
        Map<String, Double> monthTransactions = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        for (int i = 0; i < 6; i++) {
            currentMonth -= 1;
            if (currentMonth < 1) {
                currentYear -= 1;
                currentMonth = 12;
            }
            log.info("year : {} / month : {} / specCode : {}", currentYear, currentMonth, specCode);
            List<MaterialOrderDTO> tempList = materialOrderRepo.findMaterialOrderBySpecCodeAndYearMonth(specCode, currentYear, currentMonth);
            String monthName = getMonthName(currentMonth);
            log.info("찾아온 리스트의 크기...{}", tempList.size());
            double avg = tempList.stream()
                                 .mapToDouble(order -> order.getAvgPriceBySpecCode(specCode))
                                 .average()
                                 .orElse(0d); //예외 상황 발생시에는 값을 0으로 함
            log.info("평균 값...{}", avg);
            monthTransactions.put(monthName, avg);
        }

        return monthTransactions;
    }

    public List<MaterialOrderDTO> getLast10OrderBySpecCode(long specCode) {
        return materialOrderRepo.getLast10OrderBySpecCode(specCode);
    }

    private static String getMonthName(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            throw new IllegalArgumentException("유효하지 않은 월 숫자입니다.");
        }

        return Month.of(monthNumber).name();
    }

    public List<MaterialOrder> findByPlanCode(Long planCode) {
        return materialOrderRepo.findByPlanCode(planCode);
    }

    public List<OrderSpec> getOrderSpecsByOrderCode(Long orderCode) {
        return materialOrderRepo.getOrderSpecsByOrderCode(orderCode);
    }
}
