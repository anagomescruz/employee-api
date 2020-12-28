package com.employee.api.repository;


import com.employee.api.entity.Employee;
import com.employee.api.mapper.EmployeeMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.employee.api.entity.Employee.*;
import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
@Transactional(REQUIRED)
@Slf4j
public class EmployeeRepository implements PanacheRepositoryBase<Employee, String> {

    @Inject
    EntityManager em;

    /**
     *
     * @param employee the entity to persist
     * @return the attached entity, with the id set
     */
    public Optional<Employee> create(final Employee employee) {
        log.info(String.valueOf(employee));

        persist(employee);
        return Optional.of(employee);
    }

    public List<Employee> findByName(final String name){
        return em.createNamedQuery(FIND_BY_NAME, Employee.class)
                    .setParameter("name", name)
                    .getResultList();
    }

    public List<Employee> findByTeam(final String team){
        //Panache uses fund method to create the query
        //return find("team", team).list();
        return em.createNamedQuery(FIND_BY_TEAM, Employee.class)
                .setParameter("team", team)
                .getResultList();
    }

    public List<Employee> findByTitle(final String title){

        return em.createNamedQuery(FIND_BY_TITLE, Employee.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Employee> findByTeamAndTitle(final String team, final String title){

        return em.createNamedQuery(FIND_BY_TEAM_AND_TITLE, Employee.class)
                .setParameter("team", team)
                .setParameter("title", title)
                .getResultList();
    }

    public Optional<Employee> updateEmployee(final Employee employee){
        return findByIdOptional(employee.getId()).map(employeeToBeUpdated -> {
            EmployeeMapper.INSTANCE.toEmployeeUpdated(employee, employeeToBeUpdated);
            System.out.println("Repo: " + employeeToBeUpdated);
            return employeeToBeUpdated;
        });
    }

    public Optional<Employee> delete(final String id){
        return findByIdOptional(id).map(employee -> {
            delete(employee);
            return employee;
        });
    }

}
