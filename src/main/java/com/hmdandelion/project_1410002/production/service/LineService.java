package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.production.domain.entity.line.Line;
import com.hmdandelion.project_1410002.production.domain.repository.line.LineRepo;
import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
import com.hmdandelion.project_1410002.production.dto.request.LineCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.LineUpdateRequest;
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

    @Transactional(readOnly = true)
    public Page<LineResponse> getLineInfo(final Long lineCode, final LineStatusType lineStatusType) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Line> lines = null;

        if (lineCode != null && lineCode > 0) {
            lines = lineRepo.findByLineStatusNot(LineStatusType.INACTIVE, pageable);
        } else {
            lines = lineRepo.findAll(pageable);
        }
        return lines.map(LineResponse::form);
    }

    /*라인 등록*/
    @Transactional
    public Long save(final LineCreateRequest lineCreateRequest) {

        final Line
                newLine = Line.of(
                lineCreateRequest.getLineName(),
                lineCreateRequest.getLineProduction(),
                lineCreateRequest.getLineStatusType()
        );
        final Line line = lineRepo.save(newLine);

        return line.getLineCode();
    }

    /* 수정 */
    @Transactional
    public void modify(Long lineCode, LineUpdateRequest lineUpdateRequest) {

        Line line = lineRepo.findLineByLineCode(lineCode)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_LINE_CODE));

        line.modify(
                lineUpdateRequest.getLineName(),
                lineUpdateRequest.getLineProduction(),
                lineUpdateRequest.getLineStatusType()
        );
        lineRepo.save(line);
    }

    /* 삭제 */
    public void remove(Long lineCode) {

        lineRepo.deleteById(lineCode);
    }

    public String findNameByCode(Long lineCode) {
        return lineRepo.findLineByLineCode(lineCode)
                            .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_LINE_CODE)).getLineName();
    }
}

