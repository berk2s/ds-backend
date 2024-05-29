package com.berk2s.ds.api.infrastructure.employee.controllers;

import com.berk2s.ds.api.application.employee.usecase.CreateEmployeeUseCaseHandler;
import com.berk2s.ds.api.application.employee.usecase.UpdateEmployeeUseCaseHandler;
import com.berk2s.ds.api.domain.shared.EventPublisher;
import com.berk2s.ds.api.infrastructure.employee.EmployeeFacade;
import com.berk2s.ds.api.infrastructure.employee.dto.CreateEmployeeRequest;
import com.berk2s.ds.api.infrastructure.employee.dto.EmployeeResponse;
import com.berk2s.ds.api.infrastructure.employee.dto.UpdateEmployeeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping(EmployeeController.ENDPOINT)
@RestController
public class EmployeeController {
    public static final String ENDPOINT = "/employees";
    private final CreateEmployeeUseCaseHandler createEmployeeUseCaseHandler;
    private final UpdateEmployeeUseCaseHandler updateEmployeeUseCaseHandler;
    private final EventPublisher eventPublisher;
    private final EmployeeFacade employeeFacade;

    @GetMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable UUID employeeId) {
        var response = employeeFacade.getEmployeeById(employeeId);

        mapLinks(employeeId, response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody CreateEmployeeRequest req) {
        var employee = createEmployeeUseCaseHandler
                .execute(req.toUseCase());

        eventPublisher.publish(employee.pickDomainEvents());

        var response = EmployeeResponse.fromModel(employee);

        mapLinks(employee.getId(), response);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{employeeId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable UUID employeeId,
                                                           @Valid @RequestBody UpdateEmployeeRequest req) {
        var employee = updateEmployeeUseCaseHandler
                .execute(req.toUseCase(employeeId));

        eventPublisher.publish(employee.pickDomainEvents());

        var response = EmployeeResponse.fromModel(employee);

        mapLinks(employee.getId(), response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private static void mapLinks(UUID employeeId, EmployeeResponse response) {
        response
                .add(linkTo(methodOn(EmployeeController.class)
                        .getEmployee(employeeId))
                        .withRel("self"));

        response
                .add(linkTo(methodOn(EmployeeController.class)
                        .updateEmployee(employeeId, null))
                        .withRel("update"));

        response
                .add(linkTo(methodOn(EmployeeController.class)
                        .getEmployee(employeeId))
                        .withRel("delete"));

        response
                .add(linkTo(methodOn(EmployeeController.class)
                        .getEmployee(employeeId))
                        .withRel("fire"));

        response
                .add(linkTo(methodOn(EmployeeController.class)
                        .getEmployee(employeeId))
                        .withRel("departments"));
    }
}
