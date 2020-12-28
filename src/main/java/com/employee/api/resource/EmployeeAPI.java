package com.employee.api.resource;


import com.employee.api.model.EmployeeCreate;
import com.employee.api.model.EmployeeRead;
import com.employee.api.model.EmployeeUpdate;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.STRING;

@Path("/employee")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public interface EmployeeAPI {

    @POST
    @Path("/create")
    @Operation(
            operationId = "CreateNewEmployee",
            summary = "New Employee",
            description = "Create new Employee"
    )
    @RequestBody(content = @Content(
            mediaType = APPLICATION_JSON,
            schema = @Schema(implementation = EmployeeCreate.class, ref = "newEmployee")),
            description = "Create new Employee",
            required = true)
    @APIResponse(
            responseCode = "201",
            description = "The payload was accepted by the server and stored in the DB successfully",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = EmployeeRead.class, ref = "employee")),
            headers = @Header(
                    name = "Location",
                    description = "Information about the location of a newly created resource",
                    schema = @Schema(implementation = String.class))
    )
    @APIResponse(
            name = "badRequest",
            responseCode = "400",
            description = "Validation errors were found in the submitted data.",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "unauthorized",
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "forbidden",
            responseCode = "403",
            description = "Forbidden",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    Response creteNewEmployee(@NotNull EmployeeCreate employeeCreate);



    @GET
    @Path("/id/{employee_id}")
    @Operation(
            operationId = "GetEmployeeID",
            summary = "Get employee Id",
            description = "Returns the fields related to employeeId"
    )
    @APIResponse(
            responseCode = "200",
            description = "The employee information",
            content = @Content(mediaType = APPLICATION_JSON,
                                schema = @Schema(implementation = EmployeeRead.class, ref="employee_id"))
    )
    @APIResponse(
            name = "unauthorized",
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "forbidden",
            responseCode = "403",
            description = "Forbidden",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    Response getEmployee(
            @Parameter(name = "employee_id",
                    description = "Id of the Employee that needs to be fetched",
                    required = true,
                    example = "81471222-5798-11e9-ae24-57fa13b361e1",
                    schema = @Schema(description = "uuid", required = true))
            @PathParam("employee_id") String employee_id);

    @GET
    @Path("/allEmployees")
    @Operation(
            operationId = "GetAllEmployees",
            summary = "Get all employees",
            description = "Returns all employees"
    )
    @APIResponse(
            responseCode = "200",
            description = "All employees information",
            content = @Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = EmployeeRead.class, ref="allEmployees"))
    )
    @APIResponse(
            name = "unauthorized",
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "forbidden",
            responseCode = "403",
            description = "Forbidden",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    Response getAllEmployees();

    @GET
    @Path("/name/{employee_name}")
    @Operation(
            operationId = "GetEmployeeName",
            summary = "Get employee name",
            description = "Returns information related to employee(s) name(s)"
    )
    @APIResponse(
            responseCode = "200",
            description = "The employee information related to name",
            content = @Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = EmployeeRead.class, ref="employee_name"))
    )
    @APIResponse(
            name = "unauthorized",
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "forbidden",
            responseCode = "403",
            description = "Forbidden",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    Response listEmployeeName(
            @PathParam("employee_name") String employee_name);

    @GET
    @Path("/team/{employee_team}")
    @Operation(
            operationId = "GetEmployeesTeam",
            summary = "Get employees team",
            description = "Returns all employees associated to team"
    )
    @APIResponse(
            responseCode = "200",
            description = "The employee information related to team",
            content = @Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = EmployeeRead.class, ref="employee_team"))
    )
    @APIResponse(
            name = "unauthorized",
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "forbidden",
            responseCode = "403",
            description = "Forbidden",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    Response listEmployeeTeam(
            @PathParam("employee_team") String employee_team);

    @GET
    @Path("/title/{employee_title}")
    @Operation(
            operationId = "GetEmployeesTitle",
            summary = "Get employees with this title",
            description = "Returns all employees with this title"
    )
    @APIResponse(
            responseCode = "200",
            description = "All employees with a specific title",
            content = @Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = EmployeeRead.class, ref="employee_title"))
    )
    @APIResponse(
            name = "unauthorized",
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "forbidden",
            responseCode = "403",
            description = "Forbidden",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    Response listEmployeeTitle(
            @PathParam("employee_title") String employee_title);

    @GET
    @Path("/")
    @Operation(
            operationId = "GetEmployeesTeamAndTitle",
            summary = "Get employee filtered by team and title",
            description = "Returns all employees filtered by team and title"
    )
    @APIResponse(
            responseCode = "200",
            description = "All employees filtered by team and title",
            content = @Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = EmployeeRead.class, ref="employee_team_and_title"))
    )
    @APIResponse(
            name = "unauthorized",
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "forbidden",
            responseCode = "403",
            description = "Forbidden",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    Response listEmployeeByTeamAndTitle(
            @QueryParam("employee_team") String employee_team,
            @QueryParam("employee_title") String employee_title);


    @PUT
    @Path("/update/{employee_id}")
    @Operation(
            operationId = "UpdateEmployee",
            summary = "Update an existing employee information",
            description = "Updated information about a specific employee"
    )
    @RequestBody(
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = EmployeeUpdate.class)),
            description = "The existing employee to update",
            required = true
    )
    @APIResponse(
            responseCode = "200",
            description = "Employee updated",
            content = @Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = EmployeeRead.class))
    )
    @APIResponse(
            name = "badRequest",
            responseCode = "400",
            description = "Validation errors were found in the submitted data.",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "unauthorized",
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "forbidden",
            responseCode = "403",
            description = "Forbidden",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "notFound",
            responseCode = "404",
            description = "Special day not found",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    Response updateEmployee(
            @Parameter(
                    name = "employee_id",
                    description = "Id of the Employee that needs to be updated",
                    required = true,
                    example = "81471222-5798-11e9-ae24-57fa13b361e1",
                    schema = @Schema(description = "uuid", required = true))
            @NotNull @PathParam("employee_id") String id, @NotNull EmployeeUpdate employeeUpdate);


    @DELETE
    @Path("/delete/{employee_id}")
    @Operation(
            operationId = "DeleteEmployee",
            summary = "Delete an existent Employee"
    )
    @APIResponse(responseCode = "204", description = "Employee successfully deleted")
    @APIResponse(
            name = "unauthorized",
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "forbidden",
            responseCode = "403",
            description = "Forbidden",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "notFound",
            responseCode = "404",
            description = "EventType not found",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(
                    mediaType = APPLICATION_JSON)
    )
    Response delete(
            @PathParam("employee_id")
            @Parameter(name = "employee_id", schema = @Schema(type = STRING))
                    String employee_id);

}
