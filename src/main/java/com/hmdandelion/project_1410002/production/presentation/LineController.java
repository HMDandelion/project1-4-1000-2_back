package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
import com.hmdandelion.project_1410002.production.dto.response.line.LineResponse;
import com.hmdandelion.project_1410002.production.service.LineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LineController {

    private final LineService lineService;

    @GetMapping("/lines")
    public ResponseEntity<List<LineResponse>> getLines(
            @RequestParam(required = false) Long lineCode,
            @RequestParam(required = false) String lineName,
            @RequestParam(required = false) Integer lineProduction,
            @RequestParam(required = false) LineStatusType lineStatusType) {

        final List<LineResponse> lineResponses = lineService.getLineInfo(lineCode, lineName, lineProduction, lineStatusType);
       log.info("찾은이름값은{} ",lineResponses.get(0).getLineName() );

        return ResponseEntity.ok(lineResponses);
    }

    @PostMapping("/lines")
    public ResponseEntity<Void> save() {

        return null;
    }
}