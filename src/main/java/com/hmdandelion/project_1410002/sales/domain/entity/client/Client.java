package com.hmdandelion.project_1410002.sales.domain.entity.client;

import com.hmdandelion.project_1410002.sales.model.ClientStatus;
import com.hmdandelion.project_1410002.sales.model.ClientType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "tbl_client")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientCode;
    private String clientName;
    private String address;
    private String addressDetail;
    private String postcode;
    private String representativeName;
    private String phone;
    @Enumerated(value = EnumType.STRING)
    private ClientType clientType;
    @Enumerated(value = EnumType.STRING)
    private ClientStatus status;
}
