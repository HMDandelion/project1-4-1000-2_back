package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.SpecCategory;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.MaterialSpecCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MaterialSpecCategoryService {
    private final MaterialSpecCategoryRepo materialSpecCategoryRepo;

    public void save(String newCategoryName) {
        SpecCategory newCategory = SpecCategory.of(newCategoryName);
        materialSpecCategoryRepo.save(newCategory);
    }

    public SpecCategory findById(final long categoryCode) {
        return materialSpecCategoryRepo.findById(categoryCode).orElseThrow(()-> new NotFoundException(ExceptionCode.NOT_FOUND_CATEGORY_CODE));
    }

    @Transactional
    public void deleteByName(String categoryName) {
        materialSpecCategoryRepo.deleteByCategoryName(categoryName);
    }
}
