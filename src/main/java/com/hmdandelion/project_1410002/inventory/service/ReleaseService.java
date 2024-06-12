package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.release.Release;
import com.hmdandelion.project_1410002.inventory.domian.entity.release.ReleaseChange;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Storage;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.ProductRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.release.ReleaseChangeRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.release.ReleaseRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.stock.StockRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.stock.StorageRepo;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleaseOrderLackDTO;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleaseOrderProduct;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleasePossible;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleaseStorage;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.ReleaseCompleteDTO;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.ReleaseShippingDTO;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.ReleaseWaitDTO;
import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import com.hmdandelion.project_1410002.sales.domain.entity.order.Order;
import com.hmdandelion.project_1410002.sales.domain.entity.order.OrderProduct;
import com.hmdandelion.project_1410002.sales.domain.repository.client.ClientRepo;
import com.hmdandelion.project_1410002.sales.domain.repository.order.OrderProductRepo;
import com.hmdandelion.project_1410002.sales.domain.repository.order.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hmdandelion.project_1410002.inventory.domian.type.ReleaseStatus.*;
import static com.hmdandelion.project_1410002.sales.domain.type.ClientStatus.DELETED;
import static com.hmdandelion.project_1410002.sales.domain.type.OrderStatus.COMPLETED;
import static com.hmdandelion.project_1410002.sales.domain.type.OrderStatus.ORDER_RECEIVED;

@Service
@RequiredArgsConstructor
@Transactional
public class ReleaseService {

    private final ReleaseRepo releaseRepo;
    private final OrderRepo orderRepo;
    private final OrderProductRepo orderProductRepo;
    private final StorageRepo storageRepo;
    private final StockRepo stockRepo;
    private final ClientRepo clientRepo;
    private final ProductRepo productRepo;
    private final ReleaseChangeRepo releaseChangeRepo;

    private Pageable getPageable(final Integer page, final Boolean createdSort) {
        Sort sort = createdSort ? Sort.by("dDay").ascending() : Sort.by("dDay").descending();
        return PageRequest.of(page - 1, 10, sort);
    }
    private Pageable getPageableWait(final Integer page) {
        return PageRequest.of(page - 1, 10);
    }
    @Transactional(readOnly = true)
    public Page<ReleasePossible> getReleasePossibles(Integer page, Boolean isReleasePossible, Boolean createdSort) {
        List<Order> orders = orderRepo.findAllByStatus(ORDER_RECEIVED);
        List<Order> filterOrders = new ArrayList<>();
        List<Release> releases = releaseRepo.findAll();

        System.out.println("orders = " + orders.size());

        for(Order order : orders){
            for(Release release : releases){
                if(Objects.equals(release.getOrder().getOrderCode(), order.getOrderCode())){
                   filterOrders.add(order);
                }
            }
        }

        orders.removeAll(filterOrders);
        System.out.println("filterOrders = " + filterOrders);

        System.out.println("orders = " + orders.size());

        List<ReleasePossible> releasePossibleList = new ArrayList<>();

        for (Order order : orders) {
            List<OrderProduct> orderProducts = orderProductRepo.findByOrderCode(order.getOrderCode());
            Boolean result = true;

            for (OrderProduct orderProduct : orderProducts) {
                Long sum = 0L;
                List<Stock> stocks = stockRepo.findByProductProductCodeAndIsDelete(orderProduct.getProductCode(), false);

                for (Stock stock : stocks) {
                    List<Storage> storages = storageRepo.findStoragesByStockStockCodeAndIsDelete(stock.getStockCode(), false);

                    for (Storage storage : storages) {
                        sum += storage.getActualQuantity();
                    }
                }

                if (sum < orderProduct.getQuantity()) {
                    System.out.println("order.getOrderCode() = " + order.getOrderCode());
                    System.out.println("sum = " + sum);
                    System.out.println("orderProduct.getQuantity() = " + orderProduct.getQuantity());
                    result = false;
                }
            }

            Client client = clientRepo.findByClientCodeAndStatusNot(order.getClientCode(), DELETED)
                    .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_CLIENT_CODE));

            LocalDate now = LocalDate.now();
            Period period = Period.between(now, order.getDeadline());
            long daysDiff = period.getDays();
            String dday;
            if(daysDiff<0){
                dday="마감 기간 종료";
            }else if(daysDiff==0){
                dday="D-DAY";
            }else{
                dday= "D-"+String.valueOf(daysDiff);
            }

            ReleasePossible releasePossible = ReleasePossible.of(
                    order.getOrderCode(),
                    client.getClientName(),
                    order.getDeadline(),
                    dday,
                    result
            );
            releasePossibleList.add(releasePossible);
        }

        // isReleasePossible 값에 따라 필터링
        if (isReleasePossible != null) {
            releasePossibleList = releasePossibleList.stream()
                    .filter(rp -> rp.getIsReleasePossible().equals(isReleasePossible))
                    .collect(Collectors.toList());
        }

        // createdSort 값을 반영하여 정렬
        if (createdSort != null) {
            Comparator<ReleasePossible> comparator = Comparator.comparing(ReleasePossible::getDDay);
            if (!createdSort) {
                comparator = comparator.reversed();
            }
            releasePossibleList.sort(comparator);
        }

        // pageable을 생성할 때 createdSort 값을 반영
        Pageable pageable = getPageable(page, createdSort);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), releasePossibleList.size());
        List<ReleasePossible> sublist = releasePossibleList.subList(start, end);

        return new PageImpl<>(sublist, pageable, releasePossibleList.size());
    }
    @Transactional(readOnly = true)
    public List<ReleaseOrderProduct> getReleaseOrderProduct(Long orderCode) {
        List<OrderProduct> orderProducts = orderProductRepo.findByOrderCode(orderCode);
        List<ReleaseOrderProduct> resultList = new ArrayList<>();
        for(OrderProduct orderProduct:orderProducts){
            Product product = productRepo.findById(orderProduct.getProductCode()).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
            ReleaseOrderProduct releaseOrderProduct = ReleaseOrderProduct.of(
                    product.getProductName(),
                    orderProduct.getQuantity()
            );
            resultList.add(releaseOrderProduct);
        }
        return resultList;
    }
    @Transactional(readOnly = true)
    public List<ReleaseOrderLackDTO> getReleaseOrderLack(Long orderCode) {

        List<ReleaseOrderLackDTO> releaseOrderLacks = new ArrayList<>();

        System.out.println("orderCode = " + orderCode);
        Order order = orderRepo.findByOrderCodeAndStatus(orderCode,ORDER_RECEIVED).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE));
        List<OrderProduct> orderProducts = orderProductRepo.findByOrderCode(order.getOrderCode());

        for(OrderProduct orderProduct : orderProducts){
            Long lackQuantity = 0L;
            Boolean isLack = false;
            Long sum = 0L;
            Product product = productRepo.findById(orderProduct.getProductCode()).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
            List<Stock> stocks = stockRepo.findByProductProductCodeAndIsDelete(orderProduct.getProductCode(),false);
            for(Stock stock : stocks){
                List<Storage> storages = storageRepo.findStoragesByStockStockCodeAndIsDelete(stock.getStockCode(),false);
                for(Storage storage : storages){
                    sum += storage.getActualQuantity();
                }
            }
            if(sum<orderProduct.getQuantity()){
                lackQuantity = orderProduct.getQuantity()-sum;
                isLack = true;
            }else{
                lackQuantity = 0L;
            }
            ReleaseOrderLackDTO releaseOrderLack = ReleaseOrderLackDTO.of(
                    product.getProductName(),
                    lackQuantity,
                    isLack
            );

            releaseOrderLacks.add(releaseOrderLack);

        }
        return releaseOrderLacks;
    }

    public Long saveRelease(Long orderCode) {
        Order order = orderRepo.findByOrderCodeAndStatus(orderCode,ORDER_RECEIVED).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE));
        Release newRelease = Release.of(
                order
        );

        List<Storage> resultList = new ArrayList<>();
        List<OrderProduct> orderProducts = orderProductRepo.findByOrderCode(orderCode);
        for(OrderProduct orderProduct : orderProducts){
            List<Stock> stocks = stockRepo.findByProductProductCodeAndIsDelete(orderProduct.getProductCode(),false);
            for(Stock stock : stocks) {
                List<Storage> storages = storageRepo.findStoragesByStockStockCodeAndIsDelete(stock.getStockCode(),false);

                resultList.addAll(storages);
                resultList.sort(Comparator.comparing(Storage::getCreatedAt));
            }

            Long standard = 0L;

            for(Storage storage : resultList){
                standard+=storage.getActualQuantity();
                if(standard>orderProduct.getQuantity()){
                    Storage modifyStorage = storageRepo.findStorageByStorageCodeAndIsDelete(storage.getStorageCode(),false);
                    modifyStorage.minusActualQuantity(storage.getActualQuantity() - (standard-orderProduct.getQuantity()));
                    break;
                }else{
                    Storage modifyStorage = storageRepo.findStorageByStorageCodeAndIsDelete(storage.getStorageCode(),false);
                    modifyStorage.modifyActualQuantity();
                }
            }

            Long standardSum = 0L;
            for(Storage entityStorage : resultList){
                standardSum+=entityStorage.getActualQuantity();
                if(standardSum == 0){
                    entityStorage.modify();
                }
            }

            if(standardSum == 0){
                stocks.get(0).modifyIsDelete();
            }

        }


        Long releaseCode = releaseRepo.save(newRelease).getReleaseCode();
        return releaseCode;
    }
    @Transactional(readOnly = true)
    public List<ReleaseStorage> getStorageByOrderCode(Long orderCode) {
        List<Storage> resultList = new ArrayList<>();
        List<ReleaseStorage> returnList = new ArrayList<>();

        List<OrderProduct> orderProducts = orderProductRepo.findByOrderCode(orderCode);
        System.out.println("orderProducts = " + orderProducts);
        System.out.println("orderProducts.get(0).getProductCode() = " + orderProducts.get(0).getProductCode());
        for(OrderProduct orderProduct : orderProducts){
            List<Stock> stocks = stockRepo.findByProductProductCodeAndIsDelete(orderProduct.getProductCode(),false);
            Product product = productRepo.findById(orderProduct.getProductCode()).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
            System.out.println("stocks = " + stocks);
            for(Stock stock : stocks) {
                List<Storage> storages = storageRepo.findStoragesByStockStockCodeAndIsDelete(stock.getStockCode(),false);
                resultList.addAll(storages);
                resultList.sort(Comparator.comparing(Storage::getCreatedAt));
            }

            System.out.println("resultList = " + resultList);

            Long standard = 0L;
            List<String> warehouseNames = new ArrayList<>();
            List<Long> releaseQuantities = new ArrayList<>();


            for(Storage storage : resultList){
                standard+=storage.getActualQuantity();
                if(standard>orderProduct.getQuantity()){
                    Storage modifyStorage = storageRepo.findStorageByStorageCodeAndIsDelete(storage.getStorageCode(),false);

                    warehouseNames.add(modifyStorage.getWarehouse().getName());
                    releaseQuantities.add(storage.getActualQuantity() - (standard-orderProduct.getQuantity()));
                    break;
                }else{
                    Storage modifyStorage = storageRepo.findStorageByStorageCodeAndIsDelete(storage.getStorageCode(),false);
                    warehouseNames.add(modifyStorage.getWarehouse().getName());
                    releaseQuantities.add(modifyStorage.getActualQuantity());
                }
            }

                ReleaseStorage releaseStorage = ReleaseStorage.of(
                        product.getProductName(),
                        orderProduct.getQuantity(),
                        warehouseNames,
                        releaseQuantities
                        );
            returnList.add(releaseStorage);
        }

        return returnList;
    }

    @Transactional(readOnly = true)
    public Page<ReleaseWaitDTO> getReleaseWait(Integer page, Boolean deadlineSort) {
        List<ReleaseWaitDTO> resultList = new ArrayList<>();

        List<Release> releases = releaseRepo.findByStatus(WAIT);
        for (Release release : releases) {
            Order order = orderRepo.findByOrderCodeAndStatus(release.getOrder().getOrderCode(), ORDER_RECEIVED)
                    .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE));
            Client client = clientRepo.findByClientCodeAndStatusNot(order.getClientCode(), DELETED)
                    .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_CLIENT_CODE));
            ReleaseWaitDTO releaseWait = ReleaseWaitDTO.of(
                    order.getOrderCode(),
                    client.getClientName(),
                    release.getCreatedAt(),
                    order.getDeadline()
            );
            resultList.add(releaseWait);
        }

        // deadlineSort 값에 따라 resultList를 정렬
        if (deadlineSort) {
            // deadlineSort가 참이면 내림차순 정렬
            resultList.sort(Comparator.comparing(ReleaseWaitDTO::getDeadline).reversed());
        } else {
            // deadlineSort가 거짓이면 오름차순 정렬
            resultList.sort(Comparator.comparing(ReleaseWaitDTO::getDeadline));
        }

        // releaseCreatedAt을 기준으로 내림차순 정렬
        resultList.sort(Comparator.comparing(ReleaseWaitDTO::getReleaseCreatedAt).reversed());

        // 페이지 요청 생성 및 결과 리스트 페이징 처리
        Pageable pageable = getPageableWait(page);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), resultList.size());
        List<ReleaseWaitDTO> subList = resultList.subList(start, end);

        return new PageImpl<>(subList, pageable, resultList.size());
    }

    public void shippingOrder(Long orderCode) {

        Release release = releaseRepo.findByOrderOrderCode(orderCode);

        release.shipping();


        ReleaseChange releaseChange = ReleaseChange.of(
                SHIPPING,
                LocalDateTime.now(),
                release
        );
        releaseChangeRepo.save(releaseChange);
    }
    @Transactional(readOnly = true)
    public List<ReleaseShippingDTO> getReleaseShipping(Boolean deadlineSort) {
        List<ReleaseShippingDTO> resultList = new ArrayList<>();

        List<Release> releases = releaseRepo.findByStatus(SHIPPING);
        for(Release release : releases){
            Order order = orderRepo.findByOrderCodeAndStatus(release.getOrder().getOrderCode(), ORDER_RECEIVED)
                    .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE));
            System.out.println("release.getReleaseCode() = " + release.getReleaseCode());
            ReleaseChange releaseChange = releaseChangeRepo.findByReleaseReleaseCodeAndStatus(release.getReleaseCode(),SHIPPING);
            System.out.println("releaseChange = " + releaseChange);
            Client client = clientRepo.findByClientCodeAndStatusNot(order.getClientCode(), DELETED)
                    .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_CLIENT_CODE));
            ReleaseShippingDTO releaseShipping = ReleaseShippingDTO.of(
                    order.getOrderCode(),
                    client.getClientName(),
                    releaseChange.getChangeAt(),
                    order.getDeadline()
            );
            resultList.add(releaseShipping);
        }

        // deadLineSort 값에 따라 resultList를 정렬
        if (deadlineSort) {
            // deadLineSort가 참이면 내림차순 정렬
            resultList.sort(Comparator.comparing(ReleaseShippingDTO::getDeadline).reversed());
        } else {
            // deadLineSort가 거짓이면 오름차순 정렬
            resultList.sort(Comparator.comparing(ReleaseShippingDTO::getDeadline));
        }

        return resultList;
    }

    public void completeOrder(Long orderCode) {
        Release release = releaseRepo.findByOrderOrderCode(orderCode);

        release.complete();

        ReleaseChange releaseChange = ReleaseChange.of(
                DELIVERY_COMPLETED,
                LocalDateTime.now(),
                release
        );

        releaseChangeRepo.save(releaseChange);

        Order order = orderRepo.findByOrderCodeAndStatus(orderCode,ORDER_RECEIVED).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE));
        order.orderCompleteWork(COMPLETED,LocalDateTime.now());

    }
    @Transactional(readOnly = true)
    public List<ReleaseCompleteDTO> getReleaseComplete(
            Boolean isCompleted
    ) {


        List<ReleaseCompleteDTO> resultList = new ArrayList<>();

        List<Release> releases = releaseRepo.findByStatus(DELIVERY_COMPLETED);
        for(Release release : releases){
            Order order = orderRepo.findByOrderCodeAndStatus(release.getOrder().getOrderCode(), COMPLETED)
                    .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE));

            ReleaseChange releaseChange = releaseChangeRepo.findByReleaseReleaseCodeAndStatus(release.getReleaseCode(),DELIVERY_COMPLETED);
            Client client = clientRepo.findByClientCodeAndStatusNot(order.getClientCode(), DELETED)
                    .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_CLIENT_CODE));
            Boolean isDeadline = true;
            if(order.getDeadline().atStartOfDay().isBefore(releaseChange.getChangeAt())){
                isDeadline=false;
            }
            ReleaseCompleteDTO releaseComplete = ReleaseCompleteDTO.of(
                    order.getOrderCode(),
                    client.getClientName(),
                    releaseChange.getChangeAt(),
                    isDeadline
            );
            resultList.add(releaseComplete);
        }

        // deadLineSort 값에 따라 resultList를 정렬
        if (isCompleted) {
            // deadLineSort가 참이면 내림차순 정렬
            resultList.sort(Comparator.comparing(ReleaseCompleteDTO::getCompletedAt).reversed());
        } else {
            // deadLineSort가 거짓이면 오름차순 정렬
            resultList.sort(Comparator.comparing(ReleaseCompleteDTO::getCompletedAt));
        }

        return resultList;
    }
}