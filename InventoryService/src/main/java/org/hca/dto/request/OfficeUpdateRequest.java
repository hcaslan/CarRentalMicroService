package org.hca.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hca.entity.enums.OfficeType;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OfficeUpdateRequest {
    private String id;
    private String name;
    private String city;
    @Schema(hidden = true)
    private OfficeType officeType;
}