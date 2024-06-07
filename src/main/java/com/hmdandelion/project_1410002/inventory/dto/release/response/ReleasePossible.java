package com.hmdandelion.project_1410002.inventory.dto.release.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReleasePossible {
    private Long orderCode;
    private String clientName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    private Long dDay;
    private Boolean isReleasePossible;

    public static ReleasePossible of(Long orderCode, String clientName, LocalDate deadline,Long dDay, Boolean result) {

        return new ReleasePossible(
                orderCode,
                clientName,
                deadline,
                dDay,
                result);
    }
}
