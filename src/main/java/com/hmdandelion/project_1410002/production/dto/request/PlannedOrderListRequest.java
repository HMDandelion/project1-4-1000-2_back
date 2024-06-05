package com.hmdandelion.project_1410002.production.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlannedOrderListRequest {

    private final Long planCode;

    private final Long orderCode;

}
