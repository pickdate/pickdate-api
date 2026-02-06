package com.pickdate.iam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(name = "Users", description = "User account endpoints")
@SecurityRequirement(name = "basicAuth")
class UserSelfApi {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns details of the currently authenticated user")
    ResponseEntity<UserData> me(Principal principal) {
        var user = userService.getUserBy(principal);
        var response = UserData.from(user);
        return ResponseEntity.ok(response);
    }
}

