package com.pickdate.ops.audit.infrastructure;

import com.pickdate.ops.audit.application.AuditEventUseCase;
import com.pickdate.ops.audit.domain.AuditEvent;
import com.pickdate.ops.audit.domain.LoginFailedEvent;
import com.pickdate.ops.audit.domain.LoginSuccessEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import static com.pickdate.ops.audit.infrastructure.IpExtractor.extractIp;
import static com.pickdate.ops.audit.infrastructure.IpExtractor.extractUserAgent;


@Slf4j
@Component
@AllArgsConstructor
class AuditListener {

    private AuditEventUseCase auditEventUseCase;

    @EventListener
    public void onLoginSuccess(AuthenticationSuccessEvent event) {
        var name = event.getAuthentication().getName();
        var ip = extractIp(event.getAuthentication());
        var userAgent = extractUserAgent(event.getAuthentication());
        var domainEvent = new LoginSuccessEvent(name, ip, userAgent);
        auditEventUseCase.save(domainEvent);
        log.debug("Login success for user {}", name);
    }

    @EventListener
    public void onLoginFailure(AbstractAuthenticationFailureEvent event) {
        var name = event.getAuthentication().getName();
        var ip = extractIp(event.getAuthentication());
        var userAgent = extractUserAgent(event.getAuthentication());
        var domainEvent = new LoginFailedEvent(name, ip, userAgent);
        auditEventUseCase.save(domainEvent);
        log.debug("Login failed for user {}", name);
    }

    @EventListener
    public void onAuditLog(AuditEvent auditEvent) {
        auditEventUseCase.save(auditEvent);
        log.debug("Audit: {}", auditEvent);
    }
}
