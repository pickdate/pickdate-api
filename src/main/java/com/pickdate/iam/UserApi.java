package com.pickdate.iam;

import com.pickdate.shared.model.Email;
import com.pickdate.shared.model.Password;
import com.pickdate.shared.model.Username;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/v1/iam/users")
@AllArgsConstructor
@Tag(name = "Users", description = "User account endpoints")
@SecurityRequirement(name = "basicAuth")
class UserApi {

    private final UserService userService;

    @GetMapping("/me")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "Current authenticated user details",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserData.class),
                    examples = @ExampleObject(
                            name = "UserData example",
                            summary = "Current user",
                            value = UserApiConst.USER_ME_EXAMPLE_JSON
                    )
            )
    ))
    @Operation(summary = "Get current user", description = "Returns details of the currently authenticated user")
    ResponseEntity<UserData> me(Principal principal) {
        var user = userService.getUserBy(principal);
        var response = UserData.from(user);
        return ResponseEntity.ok(response);
    }

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
    ResponseEntity<Page<UserData>> list(@ParameterObject Pageable pageable) {
        var users = userService.getAllUsers();
        var page = new PageImpl<>(users, pageable, users.size());
        var response = page.map(UserData::from);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "User details",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserData.class),
                    examples = @ExampleObject(
                            name = "UserData example",
                            summary = "User by username",
                            value = UserApiConst.USER_BY_USERNAME_EXAMPLE_JSON
                    )
            )
    ))
    @Operation(summary = "Get user by username", description = "Returns details of a user by their username")
    ResponseEntity<UserData> getById(@PathVariable String username) {
        var user = userService.getUserByUsername(username);
        var response = UserData.from(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    @Operation(summary = "Create user", description = "Creates a new user account")
    ResponseEntity<Void> create(@RequestBody CreateUserRequest request) {
        var user = new User(
                Username.of(request.username()),
                Password.fromPlaintext(request.password()),
                Email.of(request.email()));

        userService.createUser(user);
        return ResponseEntity.status(201).build();
    }
}
