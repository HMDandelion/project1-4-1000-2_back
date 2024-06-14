package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodayStockDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDate today;
    private Integer todayCase;
    private Long todayQuantity;

    public static TodayStockDTO of(LocalDate now, int size, Long todayQuantity) {
        return new TodayStockDTO(
                now,
                size,
                todayQuantity
        );
    }
}
