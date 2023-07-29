package com.clinica.api.concelho;

import com.clinica.api.district.District;
import com.clinica.api.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tconcelho")
public class Concelho {
    @Id
    @SequenceGenerator(
            name = "tconcelho_concelho_id_seq",
            sequenceName = "tconcelho_concelho_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tconcelho_concelho_id_seq"
    )
    @Column(name = "concelho_id", nullable = false)
    private Integer concelhoId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "district_id")
    private District district;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "concelho_name", nullable = false)
    private String concelhoName;

    @Column(name = "dt_added", nullable = false)
    private Timestamp dtAdded;

    @Column(name = "dt_update")
    private Timestamp dtUpdate;
}
