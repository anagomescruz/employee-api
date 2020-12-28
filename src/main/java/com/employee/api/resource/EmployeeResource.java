package com.employee.api.resource;

import com.employee.api.entity.Employee;
import com.employee.api.mapper.EmployeeMapper;
import com.employee.api.model.EmployeeCreate;
import com.employee.api.model.EmployeeRead;
import com.employee.api.model.EmployeeUpdate;
import com.employee.api.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.Response.Status.*;


@ApplicationScoped
@Slf4j
public class EmployeeResource implements EmployeeAPI{

    @Inject
    EmployeeRepository employeeRepository;


    @Override
    public Response creteNewEmployee(@NotNull EmployeeCreate employeeCreate){
        log.info("Create New Employee");
        Employee employeeMapper = EmployeeMapper.INSTANCE.toEmployee(employeeCreate);

        return employeeRepository.create(employeeMapper)
                .map(EmployeeMapper.INSTANCE::toEmployeeRead)
                .map( employeeRead ->
                        Response.created(URI.create("employee/id/" + employeeRead.getId()))
                                .entity(employeeRead))
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    @Override
    public Response getEmployee(final String id){
        log.info("Get employee id");
        return employeeRepository.findByIdOptional(id)
                .map(EmployeeMapper.INSTANCE::toEmployeeRead)
                .map(Response::ok)
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    @Override
    public Response getAllEmployees(){
        log.info("Get all employees");
        List<EmployeeRead> allEmployees =  employeeRepository.listAll().stream()
                        .map(EmployeeMapper.INSTANCE::toEmployeeRead)
                        .collect(toList());
        return Response.ok(allEmployees).build();
    }

    @Override
    public Response listEmployeeName(final String name){
        log.info("Get employee by name");
        List<EmployeeRead> employeeName = employeeRepository.findByName(name).stream()
                .map(EmployeeMapper.INSTANCE::toEmployeeRead)
                .collect(toList());

        return Response.ok(employeeName).build();
    }

    @Override
    public Response listEmployeeTeam(final String team){
        log.info("Get employee by team");
        List<EmployeeRead> employeeTeam = employeeRepository.findByTeam(team).stream()
                .map(EmployeeMapper.INSTANCE::toEmployeeRead)
                .collect(toList());

        return Response.ok(employeeTeam).build();
    }

    @Override
    public Response listEmployeeTitle(final String title){
        log.info("Get employee by title");
        List<EmployeeRead> employeeTitle = employeeRepository.findByTitle(title).stream()
                .map(EmployeeMapper.INSTANCE::toEmployeeRead)
                .collect(toList());

        return Response.ok(employeeTitle).build();
    }

    @Override
    public Response listEmployeeByTeamAndTitle(final String team, final String title){
        log.info("Get employee by team and title");
        List<EmployeeRead> employeeTeamAndTitle = employeeRepository.findByTeamAndTitle(team, title).stream()
                .map(EmployeeMapper.INSTANCE::toEmployeeRead)
                .collect(toList());

        return Response.ok(employeeTeamAndTitle).build();
    }

    @Override
    public Response updateEmployee(@NotNull String id, @NotNull EmployeeUpdate employeeUpdate){
        log.info("Update employee");
        //Employee employee = EmployeeMapper.INSTANCE.employeeUpdated(employeeUpdate).setId(id);
        //System.out.println("employee: " + employee);
        //employee.setId(id);
        //System.out.println("employee after id " + employee);
        Employee emp = EmployeeMapper.INSTANCE.employeeUpdated(employeeUpdate);
        emp.setId(id);
        return employeeRepository.updateEmployee(emp)
                .map(Response::ok)
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    @Override
    public Response delete(final String id) {
        return employeeRepository.delete(id)
                .map(employee -> Response.noContent())
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

}

