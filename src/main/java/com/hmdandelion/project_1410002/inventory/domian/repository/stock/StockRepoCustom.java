package com.hmdandelion.project_1410002.inventory.domian.repository.stock;

import com.hmdandelion.project_1410002.inventory.domian.type.AssignmentStatus;
import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.StockProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface StockRepoCustom {
    Page<StockProductDTO> searchStocks(Pageable pageable, Long productCode, StockType type, Long minQuantity, Long maxQuantity, AssignmentStatus assignmentStatus, LocalDate startDate, LocalDate endDate, Boolean sort);
}
