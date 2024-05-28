package com.hmdandelion.project_1410002.purchase.service;

import com.hmdandelion.project_1410002.purchase.domain.repository.material.MaterialOrderRepo;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
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

    public Map<String, Double> getMonthTransactionsBySpecCode(long specCode) {
        Map<String, Double> monthTransactions = new HashMap<>();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        for (int i = 0; i < 6; i++) {
            currentMonth -= i;
            if (currentMonth < 1) {
                currentYear -= 1;
                currentMonth = 12;
            }
            List<MaterialOrderDTO> tempList = materialOrderRepo.getSearch(specCode, currentYear, currentMonth);
            String monthName = getMonthName(currentMonth);
            double avg = tempList.stream()
                                 .mapToDouble(order -> order.getAvgPriceBySpecCode(specCode))
                                 .average()
                                 .orElse(0d); //예외 상황 발생시에는 값을 0으로 함
            monthTransactions.put(monthName, avg);
        }

        return monthTransactions;
    }

    private static String getMonthName(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            throw new IllegalArgumentException("유효하지 않은 월 숫자입니다.");
        }

        return Month.of(monthNumber).name();
    }
}
