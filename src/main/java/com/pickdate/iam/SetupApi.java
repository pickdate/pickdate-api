package com.pickdate.iam;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/iam/setup")
@AllArgsConstructor
@Tag(name = "", description = "")
@SecurityRequirement(name = "basicAuth")
class SetupApi {

    @PostMapping("/crypto/aes-key")
    void generateAesKey() {
        String generateKey = AESKeyGenerator.generateKey();
    }

    @PostMapping("/domain")
    void setupDomain() {

    }

    @PostMapping("/user")
    void setupUser() {

    }


}
