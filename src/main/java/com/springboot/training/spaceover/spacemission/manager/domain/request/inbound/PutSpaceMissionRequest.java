package com.springboot.training.spaceover.spacemission.manager.domain.request.inbound;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.training.spaceover.spacemission.manager.enums.SpaceMissionStatus;
import com.springboot.training.spaceover.spacemission.manager.utils.annotatations.NullOrNotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PutSpaceMissionRequest {

    @JsonIgnore
    private Long id;

    @PositiveOrZero
    private Long spaceShipId;

    @NotNull
    @NotEmpty
    private String name;

    @NullOrNotBlank
    private String details;

    private SpaceMissionStatus status;

    @PositiveOrZero
    private BigDecimal revenue;

}
