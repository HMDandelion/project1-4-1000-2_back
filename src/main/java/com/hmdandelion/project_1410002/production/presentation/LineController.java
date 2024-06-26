package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
import com.hmdandelion.project_1410002.production.dto.request.LineCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.LineUpdateRequest;
import com.hmdandelion.project_1410002.production.dto.response.line.LineResponse;
import com.hmdandelion.project_1410002.production.service.LineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LineController {

    private final LineService lineService;

    @GetMapping("/lines")
    public ResponseEntity<Page<LineResponse>> getLines(
            @RequestParam(required = false) Long lineCode,
            @RequestParam(required = false) LineStatusType lineStatusType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        final Page<LineResponse> lineResponses = lineService.getLineInfo(lineCode, lineStatusType);

        return ResponseEntity.ok(lineResponses);
    }

    /* 등록 */
    @PostMapping("/lines")
    public ResponseEntity<Void> save(
            @RequestBody @Valid final LineCreateRequest lineCreateRequest
    ) {
        final Long lineCode = lineService.save(lineCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/lines/" + lineCode)).build();
    }

    /* 수정 */
    @PutMapping("/lines/{lineCode}")
    public ResponseEntity<Void> modify(
            @PathVariable final Long lineCode,
            @RequestBody @Valid final LineUpdateRequest lineUpdateRequest
    ) {
        lineService.modify(lineCode, lineUpdateRequest);

        return ResponseEntity.created(URI.create("/api/v1/lines/" + lineCode)).build();

    }

    /* 삭제 */
    @DeleteMapping("/lines/{lineCode}")
    public ResponseEntity<Void> remove(@PathVariable final Long lineCode){
        lineService.remove(lineCode);

        return ResponseEntity.noContent().build();

    }
}