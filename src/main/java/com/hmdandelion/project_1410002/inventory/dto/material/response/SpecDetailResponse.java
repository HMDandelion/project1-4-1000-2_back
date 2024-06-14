package com.hmdandelion.project_1410002.inventory.dto.material.response;


import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialClientDTO;
import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SpecDetailResponse {
    private final MaterialSpecDTO spec;
    private final List<MaterialClientDTO> clients;

    public static SpecDetailResponse from(MaterialSpec spec, List<Client> clients) {
        return new SpecDetailResponse(
                MaterialSpecDTO.from(spec),
                clients.stream().map(MaterialClientDTO::from).toList()
        );
    }
}
