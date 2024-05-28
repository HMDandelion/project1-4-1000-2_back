package com.hmdandelion.project_1410002.inventory.domian.entity.product;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="tbl_bom")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Bom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bomCode;
    private Long Quantity;
    private Long sequence;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="productCode")
    private Product product;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="specCode")
    private MaterialSpec materialSpec;

    public Bom(Long quantity, Long sequence, Product product, MaterialSpec materialSpec) {
        Quantity = quantity;
        this.sequence = sequence;
        this.product = product;
        this.materialSpec = materialSpec;
    }

    public static Bom of(Long quantity, Long sequence, Product product, MaterialSpec materialSpec) {
        return new Bom(
                quantity,
                sequence,
                product,
                materialSpec
        );
    }
}