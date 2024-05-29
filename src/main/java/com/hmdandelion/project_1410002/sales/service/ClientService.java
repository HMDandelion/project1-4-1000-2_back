package com.hmdandelion.project_1410002.sales.service;

import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import com.hmdandelion.project_1410002.sales.domain.repository.client.ClientRepo;
import com.hmdandelion.project_1410002.sales.domain.type.ClientStatus;
import com.hmdandelion.project_1410002.sales.domain.type.ClientType;
import com.hmdandelion.project_1410002.sales.dto.request.ClientCreateRequest;
import com.hmdandelion.project_1410002.sales.dto.request.ClientUpdateRequest;
import com.hmdandelion.project_1410002.sales.dto.response.ClientOrderDTO;
import com.hmdandelion.project_1410002.sales.dto.response.SalesClientResponse;
import com.hmdandelion.project_1410002.sales.dto.response.SalesClientsResponse;
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
    public Page<SalesClientsResponse> getSalesClients(Integer page, String sort, String clientName, Boolean isOrdered) {
        Page<Client> clients = clientRepo.search(getPageable(page), sort, clientName, isOrdered);
        return clients.map(SalesClientsResponse::from);
    }

    @Transactional(readOnly = true)
    public SalesClientResponse getSalesClient(Long clientCode) {
        Client client = clientRepo.findByClientCodeAndStatusNot(clientCode, ClientStatus.DELETED)
                                  .orElseThrow(() -> new RuntimeException());

        List<ClientOrderDTO> orders = clientRepo.getOrderList(clientCode);

        return SalesClientResponse.from(client, orders);
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

    public void modify(Long clientCode, ClientUpdateRequest clientRequest) {
        Client client = clientRepo.findByClientCodeAndStatusNot(clientCode, ClientStatus.DELETED)
                                  .orElseThrow(() -> new RuntimeException());

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
        clientRepo.deleteById(clientCode);
    }
}
