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
public class ReleaseWaitDTO {
    private Long orderCode;
    private String clientName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseCreatedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    public static ReleaseWaitDTO of(Long orderCode, String clientName, LocalDateTime createdAt, LocalDate deadline) {
        return new ReleaseWaitDTO(
                orderCode,
                clientName,
                createdAt,
                deadline
        );
    }
}
