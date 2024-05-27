package com.hmdandelion.project_1410002.sales.service;

import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import com.hmdandelion.project_1410002.sales.domain.repository.client.ClientRepo;
import com.hmdandelion.project_1410002.sales.dto.request.ClientCreateRequest;
import com.hmdandelion.project_1410002.sales.dto.response.ClientOrderDTO;
import com.hmdandelion.project_1410002.sales.dto.response.SalesClientResponse;
import com.hmdandelion.project_1410002.sales.dto.response.SalesClientsResponse;
import com.hmdandelion.project_1410002.sales.model.ClientStatus;
import com.hmdandelion.project_1410002.sales.model.ClientType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepo clientRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("productCode").descending());
    }

    public Page<SalesClientsResponse> getSalesClients(Integer page, String sort, String clientName, Boolean isOrdered) {
        Page<Client> clients = null;

        clients = clientRepo.search(getPageable(page));

        return clients.map(SalesClientsResponse::from);
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

    public SalesClientResponse getSalesClient(Long clientCode) {
        Client client = clientRepo.findByClientCodeAndStatusNot(clientCode, ClientStatus.DELETED)
                .orElseThrow(() -> new RuntimeException());

        List<ClientOrderDTO> orders = clientRepo.getOrderList(clientCode);

        return SalesClientResponse.from(client, orders);
    }
}
