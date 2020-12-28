package com.employee.api.model;

import com.employee.api.entity.Employee;
import com.employee.api.mapper.EmployeeMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeCreate {

    private String name;

    private String title;

    private String team;

    @JsonbProperty("start_date")
    private Instant startDate;


    public EmployeeCreate toEmployeeDTO(Employee emp){
        return EmployeeMapper.INSTANCE.toEmployeeDTO(emp);
    }

}
