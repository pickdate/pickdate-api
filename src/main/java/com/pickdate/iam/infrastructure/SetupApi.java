package com.pickdate.iam.infrastructure;

import com.pickdate.iam.application.ApplicationSetupUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/iam/setup")
@AllArgsConstructor
@Tag(name = "Setup", description = "Bootstrap endpoints for initial application configuration (encryption, public domain/origin, and initial admin user). Available only until setup is completed.")
class SetupApi {

    private final ApplicationSetupUseCase applicationSetupUseCase;

    @PostMapping("/domain")
    @Operation(summary = "Set public domain/origin for the application")
    ResponseEntity<Void> setupDomain(@RequestBody SetupDomainRequest request) {
        applicationSetupUseCase.setupDomain(request.toDomainUrl());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/user")
    @Operation(summary = "Create initial admin user")
    ResponseEntity<Void> initializeAdminUser(@RequestBody CreateUserRequest request) {
        applicationSetupUseCase.setupAdmin(request.toUser());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    @Operation(summary = "Complete setup (locks bootstrap endpoints)")
    ResponseEntity<Void> completeSetup() {
        applicationSetupUseCase.completeSetup();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
