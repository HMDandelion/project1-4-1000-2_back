package com.hmdandelion.project_1410002.sales.domain.repository.client;

import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import com.hmdandelion.project_1410002.sales.domain.type.ClientStatus;
import com.hmdandelion.project_1410002.sales.domain.type.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Long>, ClientRepoCustom {

    Optional<Client> findByClientCodeAndStatusNotAndClientType(Long clientCode, ClientStatus clientStatus, ClientType clientType);

    Optional<Client> findByClientCodeAndStatusNot(Long clientCode, ClientStatus clientStatus);
}
