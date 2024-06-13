package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.BadRequestException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.SpecCategory;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.spec.MaterialSpecCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialSpecCategoryService {

    private final MaterialSpecCategoryRepo materialSpecCategoryRepo;

    public void save(String newCategoryName) {
        SpecCategory newCategory = SpecCategory.of(newCategoryName);
        materialSpecCategoryRepo.save(newCategory);
    }

    public SpecCategory findById(final long categoryCode) {
        return materialSpecCategoryRepo.findById(categoryCode)
                                       .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_CATEGORY_CODE));
    }

    @Transactional
    public void deleteById(Long categoryCode) {
        if (materialSpecCategoryRepo.isCanDelete(categoryCode)) {
            materialSpecCategoryRepo.deleteById(categoryCode);
        } else {
            throw new BadRequestException(ExceptionCode.BAD_REQUEST_CATEGORY_EXIST);
        }
    }

    public List<SpecCategory> findAll() {
        return materialSpecCategoryRepo.findAll();
    }
}
