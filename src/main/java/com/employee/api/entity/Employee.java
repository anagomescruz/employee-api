package com.employee.api.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
@NamedQuery(name = Employee.FIND_BY_NAME,
        query = "SELECT u " +
                "FROM Employee u " +
                "WHERE u.name = :name")
@NamedQuery(name = Employee.FIND_BY_TEAM,
        query = "SELECT u " +
                "FROM Employee u " +
                "WHERE u.team = :team")
@NamedQuery(name = Employee.FIND_BY_TITLE,
        query = "SELECT u " +
                "FROM Employee u " +
                "WHERE u.title = :title")
@NamedQuery(name = Employee.FIND_BY_TEAM_AND_TITLE,
        query = "SELECT u " +
                "FROM Employee u " +
                "WHERE u.team = :team AND u.title = :title")
public class Employee {
    public static final String FIND_BY_NAME = "Employee.FIND_BY_NAME";
    public static final String FIND_BY_TEAM = "Employee.FIND_BY_TEAM";
    public static final String FIND_BY_TITLE = "Employee.FIND_BY_TITLE";
    public static final String FIND_BY_TEAM_AND_TITLE = "Employee.FIND_BY_TEAM_AND_TITLE";


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    private String name;

    @NotNull
    @JsonbDateFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_date")
    private Instant startDate;

    @NotNull
    private String team;

    @NotNull
    private String title;

    @Builder
    public Employee(@NotNull String name, @NotNull Instant startDate, @NotNull String team, @NotNull String title) {
        this.name = name;
        this.startDate = startDate;
        this.team = team;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Employee)){
            return false;
        }
        Employee employee = (Employee) o;
        return id != null && id.equals(employee.getId());
    }

    @Override
    public int hashCode() {
        return 42;
    }

    /*public Employee toEmployee(EmployeeCreate emp){
        return EmployeeMapper.INSTANCE.toEmployee(emp);
    }*/
}
