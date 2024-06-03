package com.hmdandelion.project_1410002.inventory.domian.entity.release;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.inventory.domian.type.ReleaseStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_release_change")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString
public class ReleaseChange {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long releaseChangeCode;
    @Enumerated(EnumType.STRING)
    private ReleaseStatus status;
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime changeAt;
    @ManyToOne
    @JoinColumn(name="release_code")
    private Release release;

    public ReleaseChange(ReleaseStatus status, LocalDateTime changeAt, Release release) {
        this.status = status;
        this.changeAt = changeAt;
        this.release = release;
    }

    public static ReleaseChange of(ReleaseStatus releaseStatus, LocalDateTime now, Release release) {
        return new ReleaseChange(
                releaseStatus,
                now,
                release
        );
    }
}
