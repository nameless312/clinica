package com.clinica.api.address;

import com.clinica.api.address.concelho.Concelho;
import com.clinica.api.address.district.District;
import com.clinica.api.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "taddress")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "district_id")
    private District district;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "concelho_id", referencedColumnName = "concelho_id")
    private Concelho concelho;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
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
    private Timestamp dtAdded = new Timestamp(System.currentTimeMillis());;

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
