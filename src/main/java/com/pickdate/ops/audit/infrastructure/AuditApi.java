package com.pickdate.ops.audit.infrastructure;

import com.pickdate.ops.audit.application.AuditEventUseCase;
import com.pickdate.ops.audit.domain.AuditLogEventEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/ops/audit")
@AllArgsConstructor
@Tag(name = "Audit", description = "Audit events")
@SecurityRequirement(name = "basicAuth")
class AuditApi {

    private AuditEventUseCase auditEventUseCase;

    @GetMapping
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "Successful list of audit events",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Page<AuditEventEntity> example",
                            summary = "Paginated audit response",
                            value = AuditApiConst.AUDITS_EXAMPLE_JSON)
            )
    ))
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "List audit events", description = "Lists audit events")
    public Page<AuditLogEventEntity> getAudits(@ParameterObject Pageable pageable) {
        return auditEventUseCase.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "Audit event details",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuditLogEventEntity.class),
                    examples = @ExampleObject(
                            name = "AuditEventEntity example",
                            summary = "Single audit event",
                            value = AuditApiConst.AUDIT_EXAMPLE_JSON)

            )
    ))
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Get audit", description = "Gets audit event by identifier")
    public AuditLogEventEntity getAudit(
            @PathVariable
            @Parameter(name = "id", description = "Audit identifier", example = "a1b2c3d4-1111-2222-3333-444455556666")
            String id
    ) {
        return auditEventUseCase.findById(id);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "All audits deleted")
    @Operation(summary = "Delete all audits", description = "Deletes all audits events")
    public void deleteAll() {
        auditEventUseCase.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "Audit deleted")
    @Operation(summary = "Delete audit", description = "Deletes audit event by identifier")
    public void delete(
            @PathVariable
            @Parameter(name = "id", description = "Audit identifier", example = "a1b2c3d4-1111-2222-3333-444455556666")
            String id
    ) {
        auditEventUseCase.delete(id);
    }
}
