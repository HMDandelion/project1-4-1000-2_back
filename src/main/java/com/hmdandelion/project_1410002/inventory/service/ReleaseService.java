package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.inventory.domian.repository.release.ReleaseRepo;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleasePossible;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReleaseService {

    private final ReleaseRepo releaseRepo;


    public Page<ReleasePossible> getReleasePossibles(Pageable pageable) {
        return null;
    }
}
