package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseComplete {
    private Long orderCode;
    private String clientName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedAt;
    private Boolean isDeadLine;

    public static ReleaseComplete of(Long orderCode, String clientName, LocalDateTime changeAt, Boolean isDeadLine) {
        return new ReleaseComplete(
                orderCode,
                clientName,
                changeAt,
                isDeadLine
        );
    }
}
