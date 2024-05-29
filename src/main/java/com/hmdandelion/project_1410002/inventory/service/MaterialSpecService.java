package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.BedRequestException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.SpecCategory;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.MaterialSpecRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.request.SaveMaterialSpecRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialSpecService {

    private final MaterialSpecRepo materialSpecRepo;
    private final MaterialSpecCategoryService materialSpecCategoryService;

    public List<MaterialSpecDTO> findAll() {
        return null;
    }

    @Transactional
    public List<MaterialSpecDTO> searchMaterialSpec(Pageable pageable, String materialName) {
        List<MaterialSpec> specs = materialSpecRepo.searchMaterialSpec(pageable, materialName);
        return specs.stream().map(MaterialSpecDTO::from).toList();
    }

    @Transactional
    public Long save(SaveMaterialSpecRequest request) {
        SpecCategory foundCategory = materialSpecCategoryService.findById(request.getCategoryCode());
        MaterialSpec spec = MaterialSpec.of(
                request.getSpecCode(),
                request.getMaterialName(),
                request.getRemarks(),
                request.getUnit(),
                foundCategory,
                request.getSafetyQuantity(),
                request.getSpecification()
        );
        return materialSpecRepo.save(spec).getSpecCode();
    }

    @Transactional
    public String removeByList(List<Long> specCodes) {
        if (specCodes.isEmpty()) {
            throw new BedRequestException(ExceptionCode.BAD_REQUEST_NO_OPTIONS);
        }
        long affectRows = materialSpecRepo.removeByList(specCodes);
        return affectRows + "의 콘텐츠가 삭제되었습니다.";
    }
}
