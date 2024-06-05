package com.hmdandelion.project_1410002.production.dto.response.line;

import com.hmdandelion.project_1410002.production.domain.entity.line.Line;
import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LineResponse {
    private final Long lineCode;
    private final String lineName;
    private final Integer lineProduction;
    private final LineStatusType lineStatusType;

    public static LineResponse form(final Line line){

        return new LineResponse(
                line.getLineCode(),
                line.getLineName(),
                line.getLineProduction(),
                line.getLineStatus()
        );

    }
}
