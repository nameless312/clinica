package com.clinica.api.client;

import com.clinica.api.address.Address;
import com.clinica.api.address.AddressDTO;
import com.clinica.api.marketing.Marketing;
import com.clinica.api.marketing.MarketingDTO;
import com.clinica.api.partnership.Partnership;
import com.clinica.api.partnership.PartnershipDTO;
import com.clinica.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tclient")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Integer clientId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "partnership_id", referencedColumnName = "partnership_id")
    private Partnership partnership;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "marketing_id", referencedColumnName = "marketing_id")
    private Marketing marketing;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "name_abbr", nullable = false)
    private String nameAbbr;

    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "lanline")
    private String lanline;
    @Column(name = "notes")
    private String notes;

    @Column(name = "gender")
    private Gender gender;
    @Column(name = "ssn")
    private String ssn;

    @Column(name = "dt_added", nullable = false)
    private Timestamp dtAdded = new Timestamp(System.currentTimeMillis());;

    @Column(name = "dt_update")
    private Timestamp dtUpdate;

    @Column(name = "dt_registered", nullable = false)
    private Timestamp dtRegistered;

    public ClientDTO toDto() {
        AddressDTO addressDTO = address != null ? address.toDTO() : null;
        PartnershipDTO partnershipDTO = partnership != null ? partnership.toDTO() : null;
        MarketingDTO marketingDTO = marketing != null ? marketing.toDTO() : null;
        Date bdate = birthdate != null ? new Date(birthdate.getTime()) : null;
        String genderString = gender != null ? gender.toString() : null;
        return new ClientDTO(
                clientId,
                addressDTO,
                marketingDTO,
                partnershipDTO,
                dtRegistered,
                fullName,
                nameAbbr,
                email,
                bdate,
                mobile,
                lanline,
                notes,
                genderString,
                ssn
        );
    }
}
