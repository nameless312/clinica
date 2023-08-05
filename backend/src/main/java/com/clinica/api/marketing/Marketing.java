package com.clinica.api.marketing;

import com.clinica.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tmarketing")
public class Marketing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marketing_id")
    private Integer marketingId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
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
