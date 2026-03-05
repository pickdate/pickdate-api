package com.pickdate.iam.infrastructure;

import com.pickdate.bootstrap.domain.Email;
import com.pickdate.iam.application.UserUseCase;
import com.pickdate.iam.domain.Password;
import com.pickdate.iam.domain.User;
import com.pickdate.iam.domain.UserData;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/iam/users")
@AllArgsConstructor
@Tag(name = "Users", description = "User account endpoints")
@SecurityRequirement(name = "basicAuth")
class UserApi {

    private final UserUseCase userUseCase;

    @GetMapping
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "Successful list of users",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserData.class),
                    examples = @ExampleObject(
                            name = "Page<UserData> example",
                            summary = "Paginated users response",
                            value = UserApiConst.USERS_EXAMPLE_JSON)

            )
    ))
    @Operation(summary = "List users", description = "Returns a list of all users")
    ResponseEntity<Page<UserData>> getAllUsers(@ParameterObject Pageable pageable) {
        var users = userUseCase.getAllUsers(pageable)
                .map(UserData::from);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "User details",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserData.class),
                    examples = @ExampleObject(
                            name = "UserData example",
                            summary = "User by id",
                            value = UserApiConst.USER_BY_ID_EXAMPLE_JSON
                    )
            )
    ))
    @Operation(summary = "Get user by id", description = "Returns details of a user by id")
    ResponseEntity<UserData> getById(@PathVariable String id) {
        var user = userUseCase.getUserById(id);
        var response = UserData.from(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    @Operation(summary = "Create user", description = "Creates a new user account")
    ResponseEntity<UserData> create(@RequestBody CreateUserRequest request) {
        var user = new User(
                Email.of(request.email()),
                Password.fromPlaintext(request.password())
        );

        var created = userUseCase.createUser(user);
        return new ResponseEntity<>(UserData.from(created), HttpStatus.CREATED);
    }
}
