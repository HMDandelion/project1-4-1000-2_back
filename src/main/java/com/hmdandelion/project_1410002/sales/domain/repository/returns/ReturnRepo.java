package com.hmdandelion.project_1410002.sales.domain.repository.returns;

import com.hmdandelion.project_1410002.sales.domain.entity.returns.Return;
import com.hmdandelion.project_1410002.sales.domain.type.ManageStatus;
import com.hmdandelion.project_1410002.sales.domain.type.ReturnStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReturnRepo extends JpaRepository<Return, Long>, ReturnRepoCustom {
    Optional<Return> findByReturnCodeAndManageStatusAndReturnStatus(Long returnCode, ManageStatus manageStatus, ReturnStatus returnStatus);
}
