package com.clinica.api.marketing;

import com.clinica.api.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tmarketing")
public class Marketing {
    @Id
    @SequenceGenerator(
            name = "tmarketing_id_seq",
            sequenceName = "tmarketing_id_seq"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tmarketing_id_seq"
    )
    @Column(name = "marketing_id", nullable = false)
    private Integer marketingId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "marketing_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "dt_added", nullable = false)
    private Timestamp dtAdded;

    @Column(name = "dt_update")
    private Timestamp dtUpdate;

    public MarketingDTO toDTO() {
        return new MarketingDTO(
                marketingId,
                channel
        );
    }
}
