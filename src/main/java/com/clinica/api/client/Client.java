package com.clinica.api.client;

import com.clinica.api.address.AddressDTO;
import com.clinica.api.marketing.MarketingDTO;
import com.clinica.api.partnership.PartnershipDTO;
import com.clinica.api.address.Address;
import com.clinica.api.marketing.Marketing;
import com.clinica.api.partnership.Partnership;
import com.clinica.api.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "tclient")
public class Client {
    @Id
    @SequenceGenerator(
            name = "tclient_id_seq",
            sequenceName = "tclient_id_seq"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tclient_id_seq"
    )
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
