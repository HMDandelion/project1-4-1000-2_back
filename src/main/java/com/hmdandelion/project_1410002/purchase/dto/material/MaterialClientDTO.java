package com.hmdandelion.project_1410002.purchase.dto.material;

import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
@RequiredArgsConstructor
public class MaterialClientDTO {
    private final Long clientCode;
    private final String clientName;
    private final String representativeName;
    private final List<MaterialSpecDTO> materials;

    protected MaterialClientDTO(Client client) {
        this.clientCode = client.getClientCode();
        this.clientName = client.getClientName();
        this.representativeName = client.getRepresentativeName();
        this.materials = new ArrayList<>();
    }

    public static MaterialClientDTO from(Client client) {
        return new MaterialClientDTO(
                client.getClientCode(),
                client.getClientName(),
                client.getRepresentativeName(),
                new ArrayList<>()
        );
    }

    public void addMaterials(List<MaterialSpecDTO> materials) {
        this.materials.addAll(materials);
    }
}
