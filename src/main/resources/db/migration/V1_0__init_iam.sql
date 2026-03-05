DROP TABLE IF EXISTS user_authorities CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;
DROP TABLE IF EXISTS config CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id         VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    PRIMARY KEY (id),
    CONSTRAINT email_unique UNIQUE (email),
    CONSTRAINT users_email_lowercase CHECK (email = lower(email))
);

CREATE TABLE IF NOT EXISTS authorities
(
    authority  VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    PRIMARY KEY (authority)
);

CREATE TABLE IF NOT EXISTS user_authorities
(
    user_id VARCHAR(255) NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    authority VARCHAR(255) NOT NULL REFERENCES authorities (authority) ON DELETE CASCADE,
    PRIMARY KEY (user_id, authority)
);

CREATE TABLE IF NOT EXISTS config
(
    id           varchar(255) PRIMARY KEY,
    domain_url   varchar(255),
    setup_status varchar(255)
);


CREATE INDEX IF NOT EXISTS idx_user_authorities_authority ON user_authorities (authority);
