package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.production.domain.repository.production.ProductionRepository;
import com.hmdandelion.project_1410002.production.dto.response.DailyProductionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductionService {

    private final ProductionRepository productionRepository;

    public Page<DailyProductionResponse> getDailyProductionRecords(final Integer page, final Long productionStatusCode, final LocalDateTime completedAt) {

    }


}
