package com.clinica.api.procedure;

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
@Table(name = "tprocedure")
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procedure_id")
    private Integer procedureId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "dt_added", nullable = false)
    private Timestamp dtAdded = new Timestamp(System.currentTimeMillis());;

    @Column(name = "dt_update")
    private Timestamp dtUpdate;

    @Column(name = "procedure_desc")
    private String procedureDesc;

    public ProcedureDTO toDTO() {
        return new ProcedureDTO( procedureId, procedureDesc );
    }
}
