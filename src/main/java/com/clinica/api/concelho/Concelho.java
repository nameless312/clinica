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
            name = "tconcelho_id_seq",
            sequenceName = "tconcelho_id_seq"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tconcelho_id_seq"
    )
    @Column(name = "concelho_id", nullable = false)
    private Integer concelhoId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "concelho_id", referencedColumnName = "district_id")
    private District district;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "concelho_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "concelho_name", nullable = false)
    private String concelhoName;

    @Column(name = "dt_added", nullable = false)
    private Timestamp dtAdded;

    @Column(name = "dt_update")
    private Timestamp dtUpdate;
}
