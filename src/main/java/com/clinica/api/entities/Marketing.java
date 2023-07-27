package com.clinica.api.entities;

import com.clinica.api.dto.MarketingDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tmarketing", schema = "clinica")
public class Marketing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
