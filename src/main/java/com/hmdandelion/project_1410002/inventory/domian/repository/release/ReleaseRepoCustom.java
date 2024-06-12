package com.hmdandelion.project_1410002.inventory.domian.repository.release;

import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleasePossible;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReleaseRepoCustom {
    Page<ReleasePossible> getReleasePossibles(Pageable pageable, Boolean isReleasePossible, Boolean createdSort);
}
