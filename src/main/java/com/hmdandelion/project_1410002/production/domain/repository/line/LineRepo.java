package com.hmdandelion.project_1410002.production.domain.repository.line;

import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import com.hmdandelion.project_1410002.production.domain.entity.line.Line;
import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LineRepo extends JpaRepository<Line ,Long> {
//    List<Line>findByLineStatusNot(LineStatusType lineStatusType);
    List<Line> findByLineStatusNot(LineStatusType lineStatusType);

}
