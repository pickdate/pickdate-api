package com.pickdate.iam.infrastructure;

import com.pickdate.bootstrap.web.RequestDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;


class RequestDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, RequestDetails> {

    @Override
    public RequestDetails buildDetails(HttpServletRequest request) {
        String ip = resolve(request);
        String userAgent = request.getHeader("User-Agent");
        return new RequestDetails(ip, userAgent);
    }

    private static String resolve(HttpServletRequest request) {
        String header = request.getHeader("X-Forwarded-For");
        if (header != null && !header.isBlank()) {
            String[] ips = header.split(",");
            return ips[0].trim();
        }

        String fallback = request.getHeader("X-Real-IP");
        if (fallback != null && !fallback.isBlank()) {
            return fallback.trim();
        }

        return request.getRemoteAddr();
    }
}
