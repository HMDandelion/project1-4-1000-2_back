package com.hmdandelion.project_1410002.purchase.service;

import com.hmdandelion.project_1410002.common.exception.NoContentsException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.inventory.service.MaterialSpecService;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialClientDTO;
import com.hmdandelion.project_1410002.sales.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialClientService {
    private final ClientService clientService;
    private final MaterialOrderService materialOrderService;
    private final MaterialSpecService materialSpecService;


    @Transactional
    public List<MaterialClientDTO> getClientBySpecList(List<Long> specCodes) {
        //해당 스펙을 담당자제로 가진 거래처의 목록을 불러옴
        List<Long> clientCodes =  materialOrderService.findClientCodeBySpecCodes(specCodes);
        if (clientCodes.isEmpty()) {
            //비어있다면 예외
            throw new NoContentsException(ExceptionCode.No_CONTENTS_CLIENT_CODE);
        }
        // 거래처의 정보를 받아옴
        List<MaterialClientDTO> clients = clientService.getMaterialClientByCodes(clientCodes);
        // 필요한 스펙들의 정보를 불러옴
        Map<Long, List<MaterialSpecDTO>> clientCode_SpecList = materialSpecService.getSpecByClientCodes(clientCodes);
        // 거래처에 맞게 스펙들의 정보를 넣어줌
        for (Map.Entry<Long, List<MaterialSpecDTO>> entry : clientCode_SpecList.entrySet()) {
            for (MaterialClientDTO dto : clients) {
                if (dto.getClientCode() == entry.getKey()) {
                    // 스펙 코드를 기준으로 정렬
                    List<MaterialSpecDTO> sortedSpecList = entry.getValue().stream()
                                                                .sorted(Comparator.comparing(MaterialSpecDTO::getSpecCode))
                                                                .toList();
                    dto.addMaterials(sortedSpecList);
                }
            }
        }
        return clients;
    }


    public List<MaterialClientDTO> searchClients(Pageable pageable, String clientName) {

    }
}
