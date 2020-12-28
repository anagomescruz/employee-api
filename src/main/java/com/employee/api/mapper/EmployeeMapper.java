package com.employee.api.mapper;

import com.employee.api.entity.Employee;
import com.employee.api.model.EmployeeCreate;
import com.employee.api.model.EmployeeRead;
import com.employee.api.model.EmployeeUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeRead toEmployeeRead(Employee employee);

    Employee toEmployee(EmployeeCreate employeeCreate);

    EmployeeCreate toEmployeeDTO(Employee employee);

    Employee employeeUpdated(EmployeeUpdate employeeUpdate);

    void toEmployeeUpdated(Employee employee, @MappingTarget Employee target);

    EmployeeUpdate toEmployeeUpdate(EmployeeRead employeeRead);

}
