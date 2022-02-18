package com.springboot.training.spaceover.spacemission.manager.domain.model;

import com.springboot.training.spaceover.spacemission.manager.enums.SpaceMissionStatus;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
//LT3.1-Include domain model auditing
public class SpaceMission extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @PositiveOrZero
    private Long spaceShipId;

    @NotNull
    @NotEmpty
    private String name;

    @NotEmpty
    private String details;

    @NotNull
    private SpaceMissionStatus status;

    @NotNull
    @PositiveOrZero
    private BigDecimal revenue;

    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedAt;

}
