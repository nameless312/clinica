package com.clinica.api.service.servicedesc;

import com.clinica.api.procedure.Procedure;
import com.clinica.api.service.servicetype.ServiceType;
import com.clinica.api.technique.Technique;
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
@Table(name = "tservice_desc")
public class ServiceDesc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_desc_id")
    private Integer serviceDescId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_type_id", referencedColumnName = "service_type_id")
    private ServiceType serviceType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "procedure_id", referencedColumnName = "procedure_id")
    private Procedure procedure;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "technique_id", referencedColumnName = "technique_id")
    private Technique technique;

    @Column(name = "dt_added", nullable = false)
    private Timestamp dtAdded = new Timestamp(System.currentTimeMillis());;

    @Column(name = "dt_update")
    private Timestamp dtUpdate;

    @Column(name = "dt_service")
    private Timestamp dtService;

    @Column(name = "service_cost")
    private Double serviceCost;

    @Column(name = "security_deposit")
    private Double securityDeposit;

    @Column(name = "penalization")
    private Double penalization;
    @Column(name = "paid_value")
    private Double paidValue;
    @Column(name = "commision")
    private Double commision;
    @Column(name = "discount_percentage")
    private Integer discountPercentage;
    @Column(name = "tip")
    private Double tip;
    @Column(name = "minutes_used")
    private Integer minutesUsed;
    @Column(name = "used_cream")
    private Boolean usedCream;
    @Column(name = "num_pills")
    private Integer numPills;
    @Column(name = "obs")
    private String obs;


    public ServiceDescDTO toDTO() {
        return new ServiceDescDTO(
                serviceDescId,
                serviceType.toDTO(),
                procedure.toDTO(),
                technique.toDTO(),
                dtService,
                serviceCost,
                securityDeposit,
                penalization,
                paidValue,
                commision,
                discountPercentage,
                tip,
                minutesUsed,
                usedCream,
                numPills,
                obs
        );
    }
}
