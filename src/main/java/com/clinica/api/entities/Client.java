package com.clinica.api.entities;

import com.clinica.api.dto.ClientDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

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
        return new ClientDTO(
                clientId,
                address.toDTO(),
                marketing.toDTO(),
                partnership.toDTO(),
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
