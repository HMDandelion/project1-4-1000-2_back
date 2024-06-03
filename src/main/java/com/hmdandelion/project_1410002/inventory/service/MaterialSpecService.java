package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.BedRequestException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.SpecCategory;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.spec.MaterialSpecRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialSpecCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialSpecModifyRequest;
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
    public Long save(MaterialSpecCreateRequest request) {
        SpecCategory foundCategory = materialSpecCategoryService.findById(request.getCategoryCode());
        MaterialSpec spec = MaterialSpec.of(
                request.getMaterialName(),
                request.getRemarks(),
                request.getUnit(),
                foundCategory,
                request.getSafetyStock(),
                request.getSpecification()
        );
        return materialSpecRepo.save(spec).getSpecCode();
    }

    @Transactional
    public String removeByList(List<Long> specCodes) {
        if (specCodes.isEmpty()) {
            throw new BedRequestException(ExceptionCode.BAD_REQUEST_NO_OPTIONS);
        }
        Long useThisSpec = materialSpecRepo.getUsingSepcCode(specCodes);
        if (useThisSpec > 0) {
            throw new IllegalStateException("스펙을 사용중인 재고가 있어 삭제할 수 없음.");
        }
        long affectRows = materialSpecRepo.removeByList(specCodes);
        return affectRows + "의 콘텐츠가 삭제되었습니다.";
    }

    @Transactional
    public Long modifySpec(MaterialSpecModifyRequest request) {
        MaterialSpec targetSpec = materialSpecRepo.findBySpecCode(request.getSpecCode());
        SpecCategory category = materialSpecCategoryService.findById(request.getCategoryCode());
        if (category == null) {
            throw new NotFoundException(ExceptionCode.NOT_FOUND_CATEGORY_CODE);
        }
        targetSpec.modifyFrom(request, category);

        return targetSpec.getSpecCode();
    }

}
