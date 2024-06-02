package com.hmdandelion.project_1410002.inventory.domian.repository.release;

import com.hmdandelion.project_1410002.inventory.domian.entity.release.Release;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleasePossible;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseRepo extends JpaRepository<Release,Long>,ReleaseRepoCustom {

}
