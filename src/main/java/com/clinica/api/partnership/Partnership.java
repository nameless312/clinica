package com.clinica.api.partnership;

import com.clinica.api.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tpartnerships")
public class Partnership {
    @Id
    @SequenceGenerator(
            name = "tpartnerships_id_seq",
            sequenceName = "tpartnerships_id_seq"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tpartnerships_id_seq"
    )
    @Column(name = "partnership_id", nullable = false)
    private Integer partnershipId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "partnership_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "partner", nullable = false)
    private String partner;

    @Column(name = "locality", nullable = false)
    private String locality;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "commision", nullable = false)
    private Integer commision;

    @Column(name = "job", nullable = false)
    private String job;

    @Column(name = "dt_added", nullable = false)
    private Timestamp dtAdded;

    @Column(name = "dt_update")
    private Timestamp dtUpdate;

    public PartnershipDTO toDTO() {
        return new PartnershipDTO(
                partnershipId,
                partner,
                locality,
                mobile,
                commision,
                job
        );
    }
}
