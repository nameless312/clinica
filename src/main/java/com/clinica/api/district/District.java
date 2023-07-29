package com.clinica.api.district;

import com.clinica.api.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tdistrict")
public class District {
    @Id
    @SequenceGenerator(
            name = "tdistrict_district_id_seq",
            sequenceName = "tdistrict_district_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tdistrict_district_id_seq"
    )
    @Column(name = "district_id", nullable = false)
    private Integer districtId;

    @Column(name = "district_name", nullable = false)
    private String districtName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "dt_added", nullable = false)
    private Timestamp dtAdded;

    @Column(name = "dt_update")
    private Timestamp dtUpdate;
}
