package com.hmdandelion.project_1410002.purchase.service;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import com.hmdandelion.project_1410002.purchase.domain.repository.OrderSpecRepo;
import com.hmdandelion.project_1410002.purchase.domain.repository.material.MaterialOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MaterialOrderService {
    private final MaterialOrderRepo materialOrderRepo;

    public Map<String, Double> findMonthlyAverageBySpecCode(long specCode) {
        Map<String, Double> result = new HashMap<>();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        //-5월까지 반복해서 조회함
        for (int i = 0; i < 6; i++) {
            List<MaterialOrder> orderList = materialOrderRepo.findOrdersByYearAndMonth(currentYear, currentMonth-i, specCode);
            double avg = 0d;
            for (MaterialOrder order : orderList) {
                avg += order.getPriceAvgBySpecCode(specCode);
            }
            avg = avg / orderList.size();
            result.put(convertMonthNumberToName(currentMonth-i),avg);
        }
        return result;
    }

    private static String convertMonthNumberToName(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            throw new IllegalArgumentException("Invalid month number. Month number must be between 1 and 12.");
        }

        return Month.of(monthNumber).toString();
    }
}
