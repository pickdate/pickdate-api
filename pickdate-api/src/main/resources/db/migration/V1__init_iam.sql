CREATE TABLE IF NOT EXISTS users
(
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    email      VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities
(
    authority  VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (authority)
);

CREATE TABLE IF NOT EXISTS user_authorities
(
    username  VARCHAR(255) NOT NULL REFERENCES users (username) ON DELETE CASCADE,
    authority VARCHAR(255) NOT NULL REFERENCES authorities (authority) ON DELETE CASCADE,
    PRIMARY KEY (username, authority)
);
