package com.clinica.api.entities;

import com.clinica.api.dto.AddressDTO;
import com.clinica.api.dto.ClientDTO;
import com.clinica.api.dto.MarketingDTO;
import com.clinica.api.dto.PartnershipDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Data
@Entity
@Table(name = "tclient", schema = "clinica")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private Integer clientId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "address_id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "partnership_id")
    private Partnership partnership;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "marketing_id")
    private Marketing marketing;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "name_abbr", nullable = false)
    private String nameAbbr;

    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private Timestamp birthdate;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "lanline")
    private String lanline;
    @Column(name = "notes")
    private String notes;

    @Column(name = "gender")
    private String gender;
    @Column(name = "ssn")
    private String ssn;

    @Column(name = "dt_added", nullable = false)
    private Timestamp dtAdded;

    @Column(name = "dt_update")
    private Timestamp dtUpdate;

    @Column(name = "dt_registered")
    private Timestamp dtRegistered;

    public ClientDTO toDto() {
        AddressDTO addressDTO = address.toDTO();
        PartnershipDTO partnershipDTO = partnership != null ? partnership.toDTO() : null;
        MarketingDTO marketingDTO = marketing != null ? marketing.toDTO() : null;
        return new ClientDTO(
                clientId,
                addressDTO,
                marketingDTO,
                partnershipDTO,
                dtRegistered,
                fullName,
                nameAbbr,
                email,
                new Date(birthdate.getTime()),
                mobile,
                lanline,
                notes,
                gender,
                ssn
        );
    }
}
