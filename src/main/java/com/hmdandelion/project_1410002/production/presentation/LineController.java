package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
import com.hmdandelion.project_1410002.production.dto.response.line.LineResponse;
import com.hmdandelion.project_1410002.production.service.LineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LineController {

    private final LineService lineService;

    @GetMapping("/lines")
    public ResponseEntity<Page<LineResponse>> getLines(
            @RequestParam(required = false) Long lineCode,
            @RequestParam(required = false) String lineName,
            @RequestParam(required = false) Integer lineProduction,
            @RequestParam(required = false) LineStatusType lineStatusType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {


        final Page<LineResponse> lineResponses = lineService.getLineInfo(lineCode, lineName, lineProduction, lineStatusType);

        return ResponseEntity.ok(lineResponses);
    }
}