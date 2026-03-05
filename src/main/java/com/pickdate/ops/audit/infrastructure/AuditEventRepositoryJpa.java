package com.pickdate.ops.audit.infrastructure;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.ops.audit.domain.AuditEventRepository;
import com.pickdate.ops.audit.domain.AuditLogEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
interface AuditEventRepositoryJpa extends AuditEventRepository, JpaRepository<AuditLogEventEntity, Identifier> {
}
