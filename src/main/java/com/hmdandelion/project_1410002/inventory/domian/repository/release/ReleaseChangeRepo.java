package com.hmdandelion.project_1410002.inventory.domian.repository.release;

import com.hmdandelion.project_1410002.inventory.domian.entity.release.ReleaseChange;
import com.hmdandelion.project_1410002.inventory.domian.type.ReleaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseChangeRepo extends JpaRepository<ReleaseChange,Long> {

    ReleaseChange findByReleaseReleaseCodeAndStatus(Long releaseCode, ReleaseStatus status);
}
