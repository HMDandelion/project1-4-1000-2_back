package com.hmdandelion.project_1410002.inventory.domian.repository.release;

import com.hmdandelion.project_1410002.inventory.domian.entity.release.Release;
import com.hmdandelion.project_1410002.inventory.domian.type.ReleaseStatus;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleasePossible;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepo extends JpaRepository<Release,Long>,ReleaseRepoCustom {
    List<Release> findByStatus(ReleaseStatus releaseStatus);
    Release findByOrderOrderCode(Long orderCode);
}
