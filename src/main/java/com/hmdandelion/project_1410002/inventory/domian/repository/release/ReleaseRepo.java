package com.hmdandelion.project_1410002.inventory.domian.repository.release;

import com.hmdandelion.project_1410002.inventory.domian.entity.release.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseRepo extends JpaRepository<Release,Long> {
}
