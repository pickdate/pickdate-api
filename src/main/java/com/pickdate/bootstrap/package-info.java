/**
 * Bootstrap module.
 *
 * <p>
 * This module provides shared building blocks used across the application.
 * It contains cross-cutting primitives and infrastructure that are not owned by a single feature module,
 * such as:
 * </p>
 *
 * <ul>
 *   <li><b>Core domain primitives</b> – common value objects and identifiers (e.g. {@code Identifier}, {@code Email},
 *       {@code DisplayName}) and base abstractions (e.g. {@code Value}).</li>
 *   <li><b>Validation utilities</b> – lightweight assertion and matcher helpers used to enforce invariants.</li>
 *   <li><b>Error model</b> – a structured problem representation and exception types used consistently across modules.</li>
 *   <li><b>Shared infrastructure</b> – converters and foundational web/view utilities used by multiple modules.</li>
 *   <li><b>Web context</b> – request-scoped technical details (e.g. {@code RequestDetails}) used by security/auditing.</li>
 * </ul>
 *
 * <p>
 * The module is <b>open</b>: it acts as a shared kernel and may be referenced by other modules.
 * </p>
 */

@Nonnull
@ApplicationModule(type = OPEN)
package com.pickdate.bootstrap;

import jakarta.annotation.Nonnull;
import org.springframework.modulith.ApplicationModule;

import static org.springframework.modulith.ApplicationModule.Type.OPEN;
