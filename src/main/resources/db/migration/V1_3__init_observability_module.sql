DROP TABLE IF EXISTS audit_log_events CASCADE;
DROP TABLE IF EXISTS problem_log_events CASCADE;

CREATE TABLE IF NOT EXISTS audit_log_events
(
    id             VARCHAR(255) PRIMARY KEY NOT NULL,
    action         VARCHAR(255)             NOT NULL,
    user_id        VARCHAR(255)             NULL,
    remote_address VARCHAR(255)             NULL,
    user_agent     VARCHAR(255)             NULL,
    payload        JSONB                    NULL,
    created_at     TIMESTAMP WITH TIME ZONE NULL
);

CREATE INDEX IF NOT EXISTS idx_audit_log_events_created_at ON audit_log_events (created_at);
CREATE INDEX IF NOT EXISTS idx_audit_log_events_action_created_at ON audit_log_events (action, created_at);
CREATE INDEX IF NOT EXISTS idx_audit_log_events_user_created_at ON audit_log_events (user_id, created_at);

CREATE TABLE IF NOT EXISTS problem_log_events
(
    id             VARCHAR(255) PRIMARY KEY NOT NULL,
    title          TEXT                     NULL,
    status         INTEGER                  NOT NULL,
    detail         TEXT                     NULL,
    instance       TEXT                     NULL,
    stack_trace    TEXT                     NULL,
    invalid_params JSONB                    NULL,
    created_at     TIMESTAMP WITH TIME ZONE NULL
);

CREATE INDEX IF NOT EXISTS idx_problem_log_events_created_at ON problem_log_events (created_at);
CREATE INDEX IF NOT EXISTS idx_problem_log_events_status ON problem_log_events (status);
CREATE INDEX IF NOT EXISTS idx_problem_log_events_title ON problem_log_events (title);
