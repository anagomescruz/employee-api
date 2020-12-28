package com.employee.api.resource;

import com.employee.api.common.JsonbObjectMapper;
import com.employee.api.entity.Employee;
import com.employee.api.model.EmployeeCreate;
import com.employee.api.model.EmployeeRead;
import com.employee.api.model.EmployeeUpdate;
import com.employee.api.repository.EmployeeRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class EmployeeResourceIT {

    static {
        RestAssured.config = RestAssured.config().objectMapperConfig(new ObjectMapperConfig(new JsonbObjectMapper()));
    }
    @Inject
    EmployeeRepository employeeRepository;

    LocalDateTime today = LocalDateTime.now();

    @Test
    public void create(){
        EmployeeCreate emp = EmployeeCreate.builder()
                .name("Ana")
                .title("SW")
                .team("Avalon")
                .startDate(today.atZone(ZoneId.systemDefault()).toInstant())
                .build();

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(emp)
                .post("/employee/create")
                .then()
                .statusCode(CREATED.getStatusCode());
    }

    @Test
    void update(){
        final EmployeeCreate employeeCreate = EmployeeCreate.builder()
                .name("Ana")
                .title("SW")
                .team("Avalon")
                .startDate(today.atZone(ZoneId.systemDefault()).toInstant())
                .build();

      final EmployeeRead responseRead = given()
                .when()
                .contentType(APPLICATION_JSON)
                .body(employeeCreate)
                .post("/employee/create")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract().as(EmployeeRead.class);

        final EmployeeUpdate employeeUpdated = EmployeeUpdate.builder()
                .name("Ana")
                .title("QA")
                .team("Apollo")
                .startDate(today.atZone(ZoneId.systemDefault()).toInstant())
                .build();

        given()
                .when()
                .contentType(APPLICATION_JSON)
                .body(employeeUpdated)
                .put("/employee/update/" + responseRead.getId())
                .then()
                .statusCode(OK.getStatusCode());

        given()
                .when()
                .get("/employee/id/" +  responseRead.getId())
                .then()
                .statusCode(OK.getStatusCode())
                .body("name", equalTo("Ana"))
                .body("team", equalTo("Apollo"))
                .body("title", equalTo("QA"));
    }

    @Test
    void getEmployeeById() {

        final EmployeeCreate employeeCreate = EmployeeCreate.builder()
                .name("Ana")
                .title("SW")
                .team("Avalon")
                .startDate(today.atZone(ZoneId.systemDefault()).toInstant())
                .build();

        final EmployeeRead responseRead = given()
                .when()
                .contentType(APPLICATION_JSON)
                .body(employeeCreate)
                .post("/employee/create")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract().as(EmployeeRead.class);

        final EmployeeRead responseGetID = given()
                .when()
                .get("/employee/id/{employee_id}", responseRead.getId())
                .then()
                .statusCode(OK.getStatusCode())
                .extract().as(EmployeeRead.class);

        assertThat(responseRead, is(responseGetID));
        assertEquals(responseRead.getId(), responseGetID.getId());
    }

    @Test
    void findAll(){
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

        final List<EmployeeRead> employeeReads =
                given()
                        .when()
                        .contentType(APPLICATION_JSON)
                        .get("/employee/allEmployees")
                        .then()
                        .statusCode(OK.getStatusCode())
                        .extract().body().as(getEmployeesTypeRef());



        assertThat(employeeReads.size(), is(3));


    }

    @Test
    void delete(){
        final EmployeeCreate employeeCreate = EmployeeCreate.builder()
                .name("Ana")
                .title("SW")
                .team("Avalon")
                .startDate(today.atZone(ZoneId.systemDefault()).toInstant())
                .build();

        final EmployeeRead responseRead = given()
                .when()
                .contentType(APPLICATION_JSON)
                .body(employeeCreate)
                .post("/employee/create")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract().as(EmployeeRead.class);

        given()
                .when()
                .delete("/employee/delete/{employee_id}", responseRead.getId())
                .then()
                .statusCode(NO_CONTENT.getStatusCode());

        given()
                .when()
                .get("/employee/id/{employee_id}", responseRead.getId())
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }


    private TypeRef<List<EmployeeRead>> getEmployeesTypeRef() {
        return new TypeRef<>() {
            // Kept empty on purpose
        };
    }
}
