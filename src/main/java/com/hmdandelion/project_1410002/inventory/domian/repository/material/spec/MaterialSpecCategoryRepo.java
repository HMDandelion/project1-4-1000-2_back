package com.hmdandelion.project_1410002.inventory.domian.repository.material.spec;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.SpecCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialSpecCategoryRepo extends JpaRepository<SpecCategory, Long>,MaterialSpecCategoryRepoCustom {

    void deleteByCategoryName(String categoryName);
}
