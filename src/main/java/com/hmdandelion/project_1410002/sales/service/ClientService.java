package com.hmdandelion.project_1410002.sales.service;

import com.hmdandelion.project_1410002.common.exception.BadRequestException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialClientDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.response.MaterialClientDetailResponse;
import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import com.hmdandelion.project_1410002.sales.domain.repository.client.ClientRepo;
import com.hmdandelion.project_1410002.sales.domain.type.ClientStatus;
import com.hmdandelion.project_1410002.sales.domain.type.ClientType;
import com.hmdandelion.project_1410002.sales.dto.request.ClientCreateRequest;
import com.hmdandelion.project_1410002.sales.dto.request.ClientUpdateRequest;
import com.hmdandelion.project_1410002.sales.dto.response.ClientOrderDTO;
import com.hmdandelion.project_1410002.sales.dto.response.SalesClientResponse;
import com.hmdandelion.project_1410002.sales.dto.response.SalesClientsResponse;
import com.hmdandelion.project_1410002.sales.dto.response.SimpleClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepo clientRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10);
    }

    @Transactional(readOnly = true)
    public List<SimpleClientResponse> getSimpleClients() {
        List<Client> clients = clientRepo.findByClientTypeAndStatusNot(ClientType.PRODUCTS, ClientStatus.DELETED);
        return clients.stream().map(SimpleClientResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Page<SalesClientsResponse> getSalesClients(Integer page, String sort, String clientName, Boolean isOrdered) {
        Page<Client> clients = clientRepo.search(getPageable(page), sort, clientName, isOrdered);
        return clients.map(SalesClientsResponse::from);
    }

    @Transactional(readOnly = true)
    public SalesClientResponse getSalesClient(Long clientCode) {
        Client client = findByClientCodeAndType(clientCode, ClientType.PRODUCTS);

                List < ClientOrderDTO > orders = clientRepo.getOrderList(clientCode);

        return SalesClientResponse.from(client, orders);
    }

    private Client findByClientCodeAndType(Long clientCode, ClientType clientType) {
        return clientRepo.findByClientCodeAndStatusNotAndClientType(clientCode, ClientStatus.DELETED, clientType)
                         .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_CLIENT_CODE));
    }

    public Long save(ClientCreateRequest clientRequest, ClientType clientType) {
        final Client newClient = Client.of(
                clientRequest.getClientName(),
                clientRequest.getAddress(),
                clientRequest.getAddressDetail(),
                clientRequest.getPostcode(),
                clientRequest.getRepresentativeName(),
                clientRequest.getPhone(),
                clientType
        );

        final Client client = clientRepo.save(newClient);

        return client.getClientCode();
    }

    public void modify(Long clientCode, ClientUpdateRequest clientRequest, ClientType clientType) {
        Client client = findByClientCodeAndType(clientCode, clientType);

        client.modify(
                clientRequest.getClientName(),
                clientRequest.getAddress(),
                clientRequest.getAddressDetail(),
                clientRequest.getPostcode(),
                clientRequest.getRepresentativeName(),
                clientRequest.getPhone()
        );
    }

    public void remove(Long clientCode) {
        if (!clientRepo.getOrderList(clientCode).isEmpty()) {
            throw new BadRequestException(ExceptionCode.BAD_REQUEST_ORDER_EXIST_CLIENT);
        }
        clientRepo.deleteById(clientCode);
    }

    public List<MaterialClientDTO> getMaterialClientByCodes(List<Long> clientCodes) {
        return clientRepo.getMaterialClientByCodes(clientCodes);
    }

    //region MaterialClient...

    public Client findById(Long clientCode) {
        return clientRepo.findById(clientCode).orElseThrow(
                () -> new NotFoundException(ExceptionCode.NOT_FOUND_CLIENT_CODE)
        );
    }

    public Page<MaterialClientDTO> searchMateClients(Pageable pageable, String clientName) {
        Page<MaterialClientDTO> clients = clientRepo.searchMaterialClient(pageable, clientName);
        if (clients.isEmpty()) {
            throw new NotFoundException(ExceptionCode.No_CONTENTS_CLIENT_CODE);
        }
        return clients;
    }

    public MaterialClientDetailResponse getMaterialClientDetail(Long clientCode) {
        Client client = findByClientCodeAndType(clientCode, ClientType.RAW_MATERIALS);
        return MaterialClientDetailResponse.from(client);
    }

    public List<Client> findByIds(List<Long> clientCodes) {
        return clientRepo.findByClientCodeIn(clientCodes);
    }


    //endregion
}
