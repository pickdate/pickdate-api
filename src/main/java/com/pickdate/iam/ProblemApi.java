package com.pickdate.iam;

import com.pickdate.shared.exception.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/iam/problems")
@AllArgsConstructor
@Tag(name = "Problems", description = "Problem and error reporting endpoints")
@SecurityRequirement(name = "basicAuth")
class ProblemApi {

    private final ProblemService service;

    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "Successful list of problems",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class),
                    examples = @ExampleObject(
                            name = "Page<Problem> example",
                            summary = "Paginated problems response",
                            value = ProblemApiConst.PROBLEMS_EXAMPLE_JSON)

            )
    ))
    @GetMapping
    @Operation(
            summary = "List problems",
            description = "Lists application problems and error reports",
            requestBody = @RequestBody(
                    content = @Content(mediaType = "application/json")
            ))
    public ResponseEntity<Page<Problem>> getProblems(@ParameterObject Pageable pageable) {
        var problemEntities = service.getProblems(pageable);
        var problemsDetails = problemEntities.map(ProblemEventMapper::toProblem);
        return ResponseEntity.ok(problemsDetails);
    }
}
