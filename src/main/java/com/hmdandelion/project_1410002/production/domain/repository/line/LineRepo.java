package com.hmdandelion.project_1410002.production.domain.repository.line;

import com.hmdandelion.project_1410002.production.domain.entity.line.Line;
import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepo extends JpaRepository<Line, Long> {
    Page<Line> findByLineStatusNot(LineStatusType lineStatusType, Pageable pageable);
}