package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.production.domain.entity.line.Line;
import com.hmdandelion.project_1410002.production.domain.repository.line.LineRepo;
import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
import com.hmdandelion.project_1410002.production.dto.response.line.LineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LineService {
    private final LineRepo lineRepo;

    @Transactional(readOnly = true)
    public List<LineResponse> getLineInfo(final Long lineCode, final String lineName, final Integer lineProduction, final LineStatusType lineStatusType) {


        List<Line> lines = null;
        if (lineCode != null && lineCode > 0) {
            lines = lineRepo.findByLineStatusNot(LineStatusType.PRODUCTION_START);
        }else {
            lines = lineRepo.findAll();
        }

        return lines.stream()
                .map(LineResponse::form)
                .collect(Collectors.toList());
    }//ㅈㅈㄴㄴㄴㄴ
}
