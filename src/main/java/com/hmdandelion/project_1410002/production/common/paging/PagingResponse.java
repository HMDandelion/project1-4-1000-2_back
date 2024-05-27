package com.hmdandelion.project_1410002.production.common.paging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PagingResponse {

    private final Object data;
    private final PagingButtonInfo pageInfo;

    public static PagingResponse of(Object data, PagingButtonInfo pagingButtonInfo) {
        return new PagingResponse(data, pagingButtonInfo);
    }
}
