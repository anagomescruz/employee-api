package com.employee.api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbProperty;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeRead {

    @Schema(description = "The employee id.")
    private String id;

    @Schema(description = "The employee name.")
    private String name;

    @Schema(description = "The employee team.")
    private String team;

    @Schema(description = "The employee title.")
    private String title;

    @Schema(description = "The employee startDate.")
    @JsonbProperty("start_date")
    private Instant startDate;
}
