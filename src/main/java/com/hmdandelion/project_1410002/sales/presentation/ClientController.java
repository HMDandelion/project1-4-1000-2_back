package com.hmdandelion.project_1410002.sales.presentation;

import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.sales.dto.request.ClientCreateRequest;
import com.hmdandelion.project_1410002.sales.dto.response.SalesClientsResponse;
import com.hmdandelion.project_1410002.sales.model.ClientType;
import com.hmdandelion.project_1410002.sales.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<PagingResponse> getSalesClients(
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(required = false) final String sort,
            @RequestParam(required = false) final String clientName,
            @RequestParam(required = false) final Boolean isOrdered
    ) {
        final Page<SalesClientsResponse> clients = clientService.getSalesClients(page, sort, clientName, isOrdered);

        return null;
    }

    @PostMapping("/clients")
    public ResponseEntity<Void> save(@RequestBody final ClientCreateRequest clientCreateRequest) {
        final Long clientCode = clientService.save(clientCreateRequest, ClientType.PRODUCTS);

        return ResponseEntity.created(URI.create("/api/v1/clients/" + clientCode)).build();
    }
}
