package com.springboot.training.spaceover.spacemission.manager.domain.response.outbound;

import com.springboot.training.spaceover.spacemission.manager.enums.SpaceMissionStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSpaceMissionResponse extends RepresentationModel<GetSpaceMissionResponse> {

    private Long id;

    private Long spaceShipId;

    private String name;

    private String details;

    private SpaceMissionStatus status;

    private BigDecimal revenue;

}
