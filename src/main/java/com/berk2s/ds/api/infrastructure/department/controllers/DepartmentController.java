package com.berk2s.ds.api.infrastructure.department.controllers;

import com.berk2s.ds.api.application.department.usecase.CreateDepartmentUseCaseHandler;
import com.berk2s.ds.api.infrastructure.department.dto.CreateDepartmentRequest;
import com.berk2s.ds.api.infrastructure.department.dto.DepartmentResponse;
import com.berk2s.ds.api.infrastructure.employee.controllers.EmployeeController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping(DepartmentController.ENDPOINT)
@RestController
public class DepartmentController {
    public static final String ENDPOINT = "/departments";

    private final CreateDepartmentUseCaseHandler createDepartmentUseCaseHandler;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody CreateDepartmentRequest req) {
        var department = createDepartmentUseCaseHandler
                .execute(req.toUseCase());

        var response = DepartmentResponse
                .fromModel(department);

        mapLinks(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private static void mapLinks(DepartmentResponse response) {
        response
                .getEmployees()
                .forEach((i) -> i.add(linkTo(methodOn(EmployeeController.class)
                                .getEmployee(UUID.fromString(i.getId())))
                                .withRel("self")));

        response
                .add(linkTo(methodOn(EmployeeController.class)
                        .createEmployee(null))
                        .withRel("self"));

        response
                .add(linkTo(methodOn(EmployeeController.class)
                        .createEmployee(null))
                        .withRel("update"));

        response
                .add(linkTo(methodOn(EmployeeController.class)
                        .createEmployee(null))
                        .withRel("delete"));
    }
}
