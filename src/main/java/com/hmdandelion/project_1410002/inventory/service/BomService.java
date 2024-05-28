package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.Category;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Bom;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.BomRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.ProductRepo;
import com.hmdandelion.project_1410002.inventory.dto.product.request.BomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BomService {

    private final BomRepo bomRepository;
    private final ProductRepo productRepository;


    @Transactional(readOnly = true)
    public List<Bom> getBoms() {
        List<Bom> boms = bomRepository.findAll();
        return boms;
    }
    @Transactional(readOnly = true)
    public Bom getBomByBomCode(Long BomCode){
        Bom bom = bomRepository.findBomByBomCode(BomCode).orElseThrow(() -> new CustomException(ExceptionCode.NO_BOM));
        return bom;
    }
    @Transactional(readOnly = true)
    public Bom getBomByProductCode(Long productCode) {
        Product product = productRepository.findById(productCode).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        Bom bom = bomRepository.findBomByProduct(product).orElseThrow(() -> new CustomException(ExceptionCode.NO_BOM));
        return bom;
    }

    public Long saveBomByProductCode(Long productCode, BomRequest bomRequest) {
        Product product = productRepository.findById(productCode).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        MaterialSpec materialSpec = new MaterialSpec(1L,"G","3","G",null,3,"G");
        System.out.println("양:"+bomRequest.getQuantity());
        Bom newBom = Bom.of(
          bomRequest.getQuantity(),
          bomRequest.getSequence(),
          product,
                materialSpec
        );
        System.out.println("newBom = " + newBom);
        Bom bom =  bomRepository.save(newBom);

        return bom.getBomCode();
    }
}