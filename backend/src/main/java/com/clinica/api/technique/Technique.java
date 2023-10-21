package com.clinica.api.technique;

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
@Table(name = "ttechnique")
public class Technique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technique_id")
    private Integer techniqueId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "dt_added", nullable = false)
    private Timestamp dtAdded = new Timestamp(System.currentTimeMillis());;

    @Column(name = "dt_update")
    private Timestamp dtUpdate;

    @Column(name = "technique")
    private String technique;

    public TechniqueDTO toDTO() {
        return new TechniqueDTO( techniqueId, technique );
    }
}
