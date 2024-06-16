package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.BadRequestException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.SpecCategory;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.spec.MaterialSpecRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialSpecCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialSpecModifyRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.response.SpecDetailResponse;
import com.hmdandelion.project_1410002.purchase.service.MaterialClientService;
import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MaterialSpecService {

    private final MaterialSpecRepo materialSpecRepo;
    private final MaterialSpecCategoryService materialSpecCategoryService;
    @Transactional
    public Page<MaterialSpecDTO> searchMaterialSpec(Pageable pageable, String materialName) {
        return materialSpecRepo.searchMaterialSpec(pageable, materialName).map(MaterialSpecDTO::from);
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
    public String removeByList(Long specCode) {
        if (specCode == null) {
            throw new BadRequestException(ExceptionCode.BAD_REQUEST_NO_OPTIONS);
        }
        List<Long> list = new ArrayList<>();
        list.add(specCode);
        Long useThisSpec = materialSpecRepo.getUsingSepcCode(list);
        if (useThisSpec > 0) {
            throw new BadRequestException(ExceptionCode.BAD_REQUEST_SPEC_EXIST);
        }
        long affectRows = materialSpecRepo.removeByList(list);
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

    public Map<Long, List<MaterialSpecDTO>> getSpecByClientCodes(List<Long> clientCodes) {
        return materialSpecRepo.getSpecByClientCodes(clientCodes);
    }


    public MaterialSpec specDetail(Long specCode) {
        final MaterialSpec spec = materialSpecRepo.findBySpecCode(specCode);

        return spec;
    }
    public MaterialSpec findSpec(Long specCode) {
        return materialSpecRepo.findBySpecCode(specCode);
    }

    public List<MaterialSpec> findAllSpecList() {
        return materialSpecRepo.findAll();
    }
}
