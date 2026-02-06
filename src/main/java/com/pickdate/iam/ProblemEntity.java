package com.pickdate.iam;

import com.pickdate.shared.exception.InvalidParams;
import com.pickdate.shared.model.Identifier;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.type.SqlTypes.JSON;


@With
@Getter
@Entity
@Table(name = "problem_events")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ProblemEntity {

    @EmbeddedId
    private Identifier id = Identifier.generate();

    private String title;
    private int status;
    private String detail;
    private String instance;
    private String stackTrace;

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(JSON)
    @Column(columnDefinition = "jsonb")
    private InvalidParams invalidParams = new InvalidParams();

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
