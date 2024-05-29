package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.production.domain.repository.WorkOrderRepo;
import com.hmdandelion.project_1410002.production.dto.request.WorkOrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkOrderService {

    private final WorkOrderRepo workOrderRepo;
    public Long workOrderSave(WorkOrderCreateRequest workOrderCreateRequest) {
        return null;
    }

    public void workOrderRemove(Long workOrderCode) {

        workOrderRepo.deleteById(workOrderCode);
    }
}
