package com.hmdandelion.project_1410002.purchase.service;

import com.hmdandelion.project_1410002.common.exception.NoContentsException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.spec.MaterialSpecRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import com.hmdandelion.project_1410002.purchase.domain.repository.material.MaterialOrderRepo;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialClientDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import com.hmdandelion.project_1410002.sales.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MaterialOrderService {

    private static final Logger log = LoggerFactory.getLogger(MaterialOrderService.class);
    private final ClientService clientService;
    private final MaterialOrderRepo materialOrderRepo;
    private final MaterialSpecRepo materialSpecRepo;


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

    public List<MaterialOrderDTO> getOrders(Long planCode, String clientName, Pageable pageable) {
        List<MaterialOrderDTO> orders = materialOrderRepo.gerOrders(planCode, clientName, pageable);
        log.info("조회된 주문정보 {}건.",orders.size());
        if (orders == null || orders.isEmpty()) {
            throw new NoContentsException(ExceptionCode.NO_CONTENTS_M_ORDERS);
        }
        return orders;
    }

    @Transactional
    public List<MaterialClientDTO> getClientBySpecList(List<Long> specCodes) {
        //해당 스펙을 담당자제로 가진 거래처의 목록을 불러옴
        List<Long> clientCodes =  materialOrderRepo.findClientCodeBySpecCodes(specCodes);
        if (clientCodes.isEmpty()) {
            //비어있다면 예외
            throw new NoContentsException(ExceptionCode.No_CONTENTS_CLIENT_CODE);
        }
        // 거래처의 정보를 받아옴
        List<MaterialClientDTO> clients = clientService.getMaterialClientByCodes(clientCodes);
        // 필요한 스펙들의 정보를 불러옴
        Map<Long, List<MaterialSpecDTO>> clientCode_SpecList = materialSpecRepo.getSpecByclientCodes(clientCodes);
        // 거래처에 맞게 스펙들의 정보를 넣어줌
        for (Map.Entry<Long, List<MaterialSpecDTO>> entry : clientCode_SpecList.entrySet()) {
            for (MaterialClientDTO dto : clients) {
                if (dto.getClientCode() == entry.getKey()) {
                    // 스펙 코드를 기준으로 정렬
                    List<MaterialSpecDTO> sortedSpecList = entry.getValue().stream()
                                                                   .sorted(Comparator.comparing(MaterialSpecDTO::getSpecCode))
                                                                   .toList();
                    dto.addMaterials(sortedSpecList);
                }
            }
        }
        return clients;
    }
}
