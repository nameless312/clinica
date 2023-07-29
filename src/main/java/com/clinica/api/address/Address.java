package com.clinica.api.address;

import com.clinica.api.concelho.Concelho;
import com.clinica.api.district.District;
import com.clinica.api.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "taddress")
public class Address {

    @Id
    @SequenceGenerator(
            name = "taddress_id_seq",
            sequenceName = "taddress_id_seq"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "taddress_id_seq"
    )
    @Column(name = "address_id", nullable = false)
    private Integer addressId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "district_id")
    private District district;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "concelho_id")
    private Concelho concelho;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "street_name", nullable = false)
    private String streetName;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "locality", nullable = false)
    private String locality;

    @Column(name = "dt_added", nullable = false)
    private Timestamp dtAdded;

    @Column(name = "dt_update")
    private Timestamp dtUpdate;

    public AddressDTO toDTO() {
        return new AddressDTO(
                addressId,
                district.getDistrictId(),
                district.getDistrictName(),
                concelho.getConcelhoId(),
                concelho.getConcelhoName(),
                streetName,
                city,
                zipCode,
                locality
        );
    }
}
