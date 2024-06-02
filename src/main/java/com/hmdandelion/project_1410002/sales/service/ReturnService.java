package com.hmdandelion.project_1410002.sales.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.sales.domain.entity.order.Order;
import com.hmdandelion.project_1410002.sales.domain.entity.order.OrderProduct;
import com.hmdandelion.project_1410002.sales.domain.entity.returns.Return;
import com.hmdandelion.project_1410002.sales.domain.entity.returns.ReturnProduct;
import com.hmdandelion.project_1410002.sales.domain.repository.returns.ReturnRepo;
import com.hmdandelion.project_1410002.sales.domain.repository.order.OrderRepo;
import com.hmdandelion.project_1410002.sales.domain.type.ManageType;
import com.hmdandelion.project_1410002.sales.domain.type.OrderStatus;
import com.hmdandelion.project_1410002.sales.dto.request.ReturnCreateRequest;
import com.hmdandelion.project_1410002.sales.dto.response.ReturnsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReturnService {

    private final OrderRepo orderRepo;
    private final ReturnRepo returnRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10);
    }

    public Long save(ReturnCreateRequest returnRequest) {
        Order order = orderRepo.findByOrderCodeAndStatus(returnRequest.getOrderCode(), OrderStatus.COMPLETED)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE));

        final Return newReturn = Return.of(
                order.getClientCode(),
                returnRequest.getOrderCode(),
                returnRequest.getManageType()
        );

        List<ReturnProduct> returnProducts = returnRequest.getProducts().stream()
                .map(productRequest -> {
                    return ReturnProduct.of(
                            productRequest.getQuantity(),
                            productRequest.getRefundPrice(),
                            newReturn,
                            productRequest.getProductCode()
                    );
                }).toList();

        newReturn.modifyProducts(returnProducts);

        // 교환이면 신규 주문 생성
        if(ManageType.EXCHANGE.equals(returnRequest.getManageType())) {
            final Order exchangeOrder = Order.of(
                    returnRequest.getDeadline(),
                    newReturn.getClientCode(),
                    null
            );
            List<OrderProduct> exchangeProducts = returnRequest.getProducts().stream()
                    .map(productRequest -> {
                        return OrderProduct.of(
                                productRequest.getQuantity(),
                                productRequest.getRefundPrice(),
                                productRequest.getProductCode(),
                                exchangeOrder
                        );
                    }).toList();

            exchangeOrder.modifyProducts(exchangeProducts);
            final Long exchangeOrderCode = orderRepo.save(exchangeOrder).getOrderCode();

            newReturn.modifyExchangeCode(exchangeOrderCode);
        }
        System.out.println("manageType : " + newReturn.getManageType());
        order.modifyStatus(OrderStatus.RETURNED);
        return returnRepo.save(newReturn).getReturnCode();
    }

    @Transactional(readOnly = true)
    public Page<ReturnsResponse> getReturns(
            Integer page, Long orderCode, String manageType,
            String clientName, String productName, String sort
    ) {
        Page<ReturnsResponse> returns = returnRepo.search(getPageable(page), orderCode, manageType, clientName, productName, sort);
        return returns;
    }
}
