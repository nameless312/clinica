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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
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
