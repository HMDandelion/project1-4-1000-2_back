package com.hmdandelion.project_1410002.inventory.domian.repository.stock;

import com.hmdandelion.project_1410002.inventory.dto.stock.response.StorageFilterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface StorageRepoCustom {
    Page<StorageFilterResponse> searchStorages(Pageable pageable, Long productCode, Long minQuantity, Long maxQuantity, Long startDate, Long endDate,Boolean quantitySort, Boolean dateSort);
}
