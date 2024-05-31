package com.hmdandelion.project_1410002.sales.domain.repository.returns;

import com.hmdandelion.project_1410002.sales.domain.entity.returns.Return;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRepo extends JpaRepository<Return, Long>, ReturnRepoCustom {
}
