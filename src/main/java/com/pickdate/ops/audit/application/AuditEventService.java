package com.pickdate.ops.audit.application;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.bootstrap.domain.Property;
import com.pickdate.bootstrap.exception.NotFoundException;
import com.pickdate.ops.audit.domain.Action;
import com.pickdate.ops.audit.domain.AuditEvent;
import com.pickdate.ops.audit.domain.AuditEventRepository;
import com.pickdate.ops.audit.domain.AuditLogEventEntity;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "activities")
class AuditEventService implements AuditEventUseCase {

    private final AuditEventRepository repository;

    @Transactional(readOnly = true)
    @Cacheable(key = "#id")
    public AuditLogEventEntity findById(String id) {
        return getAuditEventEntity(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#type + ':' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<AuditLogEventEntity> findByType(Pageable pageable, String type) {
        return repository.findByAction(Action.of(type), pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#userId + ':' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<AuditLogEventEntity> findByUserId(Pageable pageable, String userId) {
        return repository.findByUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "'all:' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<AuditLogEventEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void save(AuditEvent auditEvent) {
        var eventEntity = AuditEventFactory.createFrom(auditEvent);
        repository.save(eventEntity);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void delete(String id) {
        repository.deleteById(Identifier.of(id));
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteAll() {
        repository.deleteAll();
    }

    private AuditLogEventEntity getAuditEventEntity(String id) {
        return repository.findById(Identifier.of(id))
                .orElseThrow(() -> new NotFoundException(Property.of("id", id), "Audit with id %s not found".formatted(id)));
    }
}
