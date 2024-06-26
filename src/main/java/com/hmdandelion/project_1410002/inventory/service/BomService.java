package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Bom;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.spec.MaterialSpecRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.BomRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.BomRepository;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.ProductRepo;
import com.hmdandelion.project_1410002.inventory.dto.product.request.BomCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.product.request.BomUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BomService {

    private final BomRepo bomRepository;
    private final ProductRepo productRepository;
    private final MaterialSpecRepo materialSpecRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("sequence"));
    }
    @Transactional(readOnly = true)
    public List<Bom> getBoms() {
        List<Bom> boms = bomRepository.findAll();
        return boms;
    }
    @Transactional(readOnly = true)
    public Bom getBomByBomCode(Long BomCode){
        Bom bom = bomRepository.findBomByBomCode(BomCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_BOM_CODE));
        return bom;
    }
    @Transactional(readOnly = true)
    public List<Bom> getBomByProductCode(Long productCode) {
        Product product = productRepository.findById(productCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        List<Bom> boms = bomRepository.findBomByProduct(product);
        return boms;
    }

    public Long saveBomByProductCode(Long productCode, BomCreateRequest bomRequest) {
        Product product = productRepository.findById(productCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        MaterialSpec materialSpec = materialSpecRepo.findById(bomRequest.getSpecCode()).orElseThrow(() -> new NotFoundException(ExceptionCode.NO_CONTENTS_MATERIAL_STOCK));

        Bom newBom = Bom.of(
                bomRequest.getQuantity(),
                bomRequest.getSequence(),
                product,
                materialSpec
        );

        Bom bom =  bomRepository.save(newBom);

        return bom.getBomCode();
    }


    public void modifyBomByBomCode(Long bomCode, BomUpdateRequest bomRequest) {
        Bom bom = getBomByBomCode(bomCode);
        System.out.println("bom = " + bom);
        MaterialSpec materialSpec = materialSpecRepo.findById(bomRequest.getSpecCode()).orElseThrow(() -> new NotFoundException(ExceptionCode.NO_CONTENTS_MATERIAL_STOCK));
        bom.modify(
                materialSpec,
                bomRequest.getQuantity(),
                bomRequest.getSequence()
        );
    }

    public void deleteByBomCode(Long bomCode) {
        bomRepository.deleteById(bomCode);
    }

    public Page<Bom> getBomByPageProductCode(Long productCode, Integer page) {
        Product product = productRepository.findById(productCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        System.out.println("product = " + product);
        Page<Bom> pages = bomRepository.findByProductProductCode(getPageable(page),product.getProductCode());
        System.out.println("pages = " + pages);
        return pages;
    }
}
