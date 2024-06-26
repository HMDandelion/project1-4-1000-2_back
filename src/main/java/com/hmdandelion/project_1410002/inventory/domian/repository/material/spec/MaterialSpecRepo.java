package com.hmdandelion.project_1410002.inventory.domian.repository.material.spec;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialSpecRepo extends JpaRepository<MaterialSpec, Long>, MaterialSpecRepoCustom {

    MaterialSpec findBySpecCode(Long specCode);
}
