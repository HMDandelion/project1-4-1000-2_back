package com.hmdandelion.project_1410002.sales.domain.repository.returns;

import com.hmdandelion.project_1410002.sales.domain.type.ManageType;
import com.hmdandelion.project_1410002.sales.dto.response.ReturnResponse;
import com.hmdandelion.project_1410002.sales.dto.response.ReturnsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReturnRepoCustom {
    Page<ReturnsResponse> search(Pageable pageable, Long orderCode, String manageType, String clientName, String productName, String sort);

    Optional<ReturnResponse> getReturn(Long returnCode);
}
