package com.pickdate.iam.infrastructure;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;


@Profile({"local", "dev", "test"})
@RestController
public class DebugController {

    @GetMapping("/debug/forwarded")
    public Map<String, String> debug(HttpServletRequest request) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("scheme", request.getScheme());
        response.put("serverName", request.getServerName());
        response.put("serverPort", String.valueOf(request.getServerPort()));
        response.put("X-Forwarded-Proto", request.getHeader("X-Forwarded-Proto"));
        response.put("X-Forwarded-Host", request.getHeader("X-Forwarded-Host"));
        response.put("X-Forwarded-Port", request.getHeader("X-Forwarded-Port"));
        response.put("Forwarded", request.getHeader("Forwarded"));
        return response;
    }
}
