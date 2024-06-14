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
public class ReleaseShippingDTO {
    private Long orderCode;
    private String clientName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shippingStartAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    private String dday;

    public static ReleaseShippingDTO of(Long orderCode, String clientName, LocalDateTime changeAt, LocalDate deadline,String dday) {
        return new ReleaseShippingDTO(
                orderCode,
                clientName,
                changeAt,
                deadline,
                dday
        );
    }
}
