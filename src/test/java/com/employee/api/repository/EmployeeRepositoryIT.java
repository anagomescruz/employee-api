package com.employee.api.repository;

import com.employee.api.entity.Employee;
import com.employee.api.repository.EmployeeRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class EmployeeRepositoryIT {
    @Inject
    EmployeeRepository employeeRepository;

    @Test
    void create(){
        Employee employeeToPersist = employeeBuilder();
        //employeeRepository.create(employee);
        //assertNotNull(employee.getId());

        final Optional<Employee> employeePersisted = employeeRepository.create(employeeToPersist);

        assertTrue(employeePersisted.isPresent());

        final Employee employeeReturned = employeePersisted.get();

        assertEquals(employeeToPersist.getId(), employeeReturned.getId());
    }

    @Test
    void update(){
        Optional<Employee> employeePersisted = employeeRepository.create(employeeBuilder());
        assertTrue(employeePersisted.isPresent());

        final Optional<Employee> employeeReturned = employeeRepository.findByIdOptional(employeePersisted.get().getId());

        final Employee employeeToUpdate = employeeReturned.get();
        employeeToUpdate.setTitle("QA");

        final Optional<Employee> employeeUpdate = employeeRepository.updateEmployee(employeeToUpdate);

        assertThat(employeePersisted, is(employeeReturned));
        assertEquals(employeeReturned.get().getId(), employeeToUpdate.getId());
        assertEquals(employeeToUpdate.getId(), employeeUpdate.get().getId());
        assertEquals(employeeToUpdate.getTitle(), employeeUpdate.get().getTitle());
    }

    @Test
    void findAll() {
        //final List<Employee> employees = employeeRepository.listAll();
        //System.out.println("size: " + employees.size());
        //assertThat(employees.size(), is(0));
        List.of(Employee.builder()
                        .name("Ana")
                        .title("SW")
                        .team("Avalon")
                        .startDate(Instant.parse("2020-11-23T09:30:32Z"))
                        .build(),
                Employee.builder()
                        .name("Anabela")
                        .title("QA")
                        .team("Chronos")
                        .startDate(Instant.parse("2020-12-23T09:30:32Z"))
                        .build(),
                Employee.builder()
                        .name("Joao")
                        .title("SW")
                        .team("Kraken")
                        .startDate(Instant.parse("2020-11-23T09:30:32Z"))
                        .build()
        ).forEach(employeeRepository::create);

        assertThat(employeeRepository.listAll().size(), is(3));
    }

    @Test
    void findByName(){
        //create an employee
        List.of(Employee.builder()
                        .name("Ana")
                        .title("SW")
                        .team("Avalon")
                        .startDate(Instant.parse("2020-11-23T09:30:32Z"))
                        .build(),
                Employee.builder()
                        .name("Anabela")
                        .title("QA")
                        .team("Chronos")
                        .startDate(Instant.parse("2020-12-23T09:30:32Z"))
                        .build(),
                Employee.builder()
                        .name("Joao")
                        .title("SW")
                        .team("Kraken")
                        .startDate(Instant.parse("2020-11-23T09:30:32Z"))
                        .build()
        ).forEach(employeeRepository::create);

        List<Employee> employeeReturned = employeeRepository.listAll();


        //List<Employee> emp1 = employeeReturned.listIterator(employeeReturned.get().getName());


        List<Employee> emp = employeeRepository.findByName("Ana");



        //assertThat();
    }

    @Test
    void delete() {
        Optional<Employee> employeePersisted = employeeRepository.create(employeeBuilder());
        assertTrue(employeePersisted.isPresent());

        final Optional<Employee> employee = employeeRepository.findByIdOptional(employeePersisted.get().getId());
        assertTrue(employee.isPresent());
        employeeRepository.delete(employee.get().getId());
        assertFalse(employeeRepository.findByIdOptional(employee.get().getId()).isPresent());
    }

    private Employee employeeBuilder(){
        return Employee.builder()
                .name("Ana")
                .title("SW")
                .team("Avalon")
                .startDate(Instant.parse("2020-11-23T09:30:32Z"))
                .build();
    }




}
