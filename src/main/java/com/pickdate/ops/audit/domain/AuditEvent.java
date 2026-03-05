package com.pickdate.ops.audit.domain;

import com.pickdate.bootstrap.functional.Maybe;


public interface AuditEvent {

    String userId();

    Action action();

    String remoteAddress();

    String userAgent();

    default Maybe<AuditPayload> payload() { return Maybe.none(); }
}

