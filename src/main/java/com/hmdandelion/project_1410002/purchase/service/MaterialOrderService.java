package com.hmdandelion.project_1410002.purchase.service;

import com.hmdandelion.project_1410002.common.exception.NoContentsException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.employee.domain.entity.Employee;
import com.hmdandelion.project_1410002.employee.service.EmployeeService;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.spec.MaterialSpecRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import com.hmdandelion.project_1410002.production.service.PlanService;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import com.hmdandelion.project_1410002.purchase.domain.repository.material.MaterialOrderRepo;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialClientDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.request.MaterialOrderCreateRequest;
import com.hmdandelion.project_1410002.purchase.dto.material.request.MaterialOrderModifyRequest;
import com.hmdandelion.project_1410002.purchase.dto.material.response.MaterialOrderResponse;
import com.hmdandelion.project_1410002.purchase.dto.material.response.MaterialOrderWeeklyResponse;
import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;

import com.hmdandelion.project_1410002.sales.service.ClientService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class MaterialOrderService {

    private static final Logger log = LoggerFactory.getLogger(MaterialOrderService.class);
    private final ClientService clientService;
    private final MaterialOrderRepo materialOrderRepo;
    private final MaterialSpecRepo materialSpecRepo;
    private final PlanService planService;
    private final EmployeeService employeeService;

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



    @Transactional(readOnly = true)
    public MaterialOrderResponse findDetail(Long orderCode) {
        //TODO 예외처리 안되어있음
        MaterialOrder order = materialOrderRepo.findById(orderCode).orElseThrow(
                ()-> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE)
        );
        ProductionPlan plan = planService.findById(order.getPlanCode());
        Employee employee = employeeService.findById(order.getEmployeeCode());
        String departmentName = employeeService.findDepartmentNameById(employee.getDepartmentCode());
        String positionName = employeeService.findPositionNameById(employee.getPositionCode());
        List<OrderSpec> orderSpecs = materialOrderRepo.getOrderSpecsByOrderCode(orderCode);
        return MaterialOrderResponse.of(plan, order, employee, positionName, departmentName, orderSpecs);
    }

    @Transactional
    public void deleteOrder(Long orderCode, String deletionReason) {
        MaterialOrder order = materialOrderRepo.findById(orderCode).orElseThrow(
                ()-> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE)
        );
        order.delete(deletionReason);
        materialOrderRepo.flush();
    }

    @Transactional
    public Long createOrder(MaterialOrderCreateRequest request) {
        //TODO 예외처리 안됨
        final Client client = clientService.findById(request.getClientCode());
        final MaterialOrder order = MaterialOrder.from(request, client);
        final Long orderCode = materialOrderRepo.save(order).getOrderCode();
        materialOrderRepo.setOrderSpec(orderCode, request.getOrderSpecList());
        return orderCode;
    }

    @Transactional
    public Long modifyOrder(MaterialOrderModifyRequest request) {
        //TODO 예외처리 안됨
        final Client client = clientService.findById(request.getClientCode());
        final MaterialOrder order = materialOrderRepo.findById(request.getOrderCode()).orElseThrow(
                () -> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE)
        );
        order.modify(request, client);
        //수정 소요가 너무 심해서 삭제로 진행합니다
        materialOrderRepo.deleteAllOrderSpecByOrderCode(order.getOrderCode());
        materialOrderRepo.setOrderSpec(order.getOrderCode(), request.getOrderSpecList());

        return order.getOrderCode();
    }

    public List<Long> findClientCodeBySpecCodes(List<Long> specCodes) {
        return materialOrderRepo.findClientCodeBySpecCodes(specCodes);
    }


    public List<MaterialOrderDTO> getOrderToday(Pageable pageable) {
        final LocalDate today = LocalDate.now();
        List<MaterialOrderDTO> orders = materialOrderRepo.getOrderToday(pageable, today);
        if (orders.isEmpty()) {
            throw new NoContentsException(ExceptionCode.No_CONTENTS_M_ORDER_TODAY);
        }
        return orders;
    }

    public MaterialOrderWeeklyResponse orderWeekly() {
        LocalDate today = LocalDate.now();
        int dayOfWeekNumber = today.getDayOfWeek().getValue();
        LocalDate targetDate = today.minusDays(--dayOfWeekNumber);

        Map<String, Long> orderThisWeek = materialOrderRepo.orderWeekly(targetDate);
        return MaterialOrderWeeklyResponse.from(orderThisWeek);
    }

    @Transactional
    public void orderArrival(Long orderCode) {
        MaterialOrder order = materialOrderRepo.findById(orderCode).orElseThrow(
                ()-> new NotFoundException(ExceptionCode.NO_CONTENTS_M_ORDERS)
        );
        order.setArrival(LocalDateTime.now());
    }
}
