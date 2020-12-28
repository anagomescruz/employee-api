package com.employee.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmployeeUpdate {

    private String name;

    private String title;

    private String team;

    @JsonbProperty("start_date")
    private Instant startDate;

}
