package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.production.domain.entity.line.Line;
import com.hmdandelion.project_1410002.production.domain.repository.line.LineRepo;
import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
import com.hmdandelion.project_1410002.production.dto.request.LineCreateRequest;
import com.hmdandelion.project_1410002.production.dto.response.line.LineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LineService {
    private final LineRepo lineRepo;

    /* 라인 목록 조회 */
    @Transactional(readOnly = true)
    public Page<LineResponse> getLineInfo(final Long lineCode, final String lineName, final Integer lineProduction, final LineStatusType lineStatusType) {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Line> lines = null;
        if (lineCode != null && lineCode > 0) {
            lines = lineRepo.findByLineStatusNot(LineStatusType.PRODUCTION_START, pageable);
        } else {
            lines = lineRepo.findAll(pageable);
        }

        return lines.map(LineResponse::form);
    }

    /*라인 등록*/
    public Long save(final LineCreateRequest lineCreateRequest) {

        final Line newLine = Line.of(
                lineCreateRequest.get
        );
        final Line line =  lineRepo.save(newLine);


     return line.getLineCode();
    }
}

