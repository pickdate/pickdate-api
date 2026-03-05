DROP TABLE IF EXISTS votes CASCADE;
DROP TABLE IF EXISTS options CASCADE;
DROP TABLE IF EXISTS participants CASCADE;
DROP TABLE IF EXISTS polls CASCADE;
DROP TABLE IF EXISTS geo_locations CASCADE;

CREATE TABLE IF NOT EXISTS geo_locations
(
    id         VARCHAR(36) PRIMARY KEY,
    latitude   DOUBLE PRECISION NOT NULL,
    longitude  DOUBLE PRECISION NOT NULL,
    place_id   VARCHAR(255),
    address    VARCHAR(1000),
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS polls
(
    id          VARCHAR(36) PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    location_id VARCHAR(36)  REFERENCES geo_locations (id) ON DELETE SET NULL,
    created_at  TIMESTAMP WITH TIME ZONE,
    updated_at  TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS participants
(
    id           VARCHAR(36) PRIMARY KEY,
    poll_id      VARCHAR(36)  NOT NULL REFERENCES polls (id) ON DELETE CASCADE,
    display_name VARCHAR(255) NOT NULL,
    email        VARCHAR(255),
    phone        VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS options
(
    id        VARCHAR(36) PRIMARY KEY,
    poll_id   VARCHAR(36)              NOT NULL REFERENCES polls (id) ON DELETE CASCADE,
    start_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    end_at    TIMESTAMP WITH TIME ZONE NOT NULL,
    whole_day BOOLEAN                  NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS votes
(
    participant_id VARCHAR(36) NOT NULL REFERENCES participants (id) ON DELETE CASCADE,
    option_id      VARCHAR(36) NOT NULL REFERENCES options (id) ON DELETE CASCADE,
    poll_id        VARCHAR(36) NOT NULL REFERENCES polls (id) ON DELETE CASCADE,
    availability   VARCHAR(10) NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE,
    updated_at     TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (participant_id, option_id)
);

CREATE INDEX IF NOT EXISTS idx_polls_location ON polls (location_id);
CREATE INDEX IF NOT EXISTS idx_participants_poll ON participants (poll_id);
CREATE INDEX IF NOT EXISTS idx_options_poll ON options (poll_id);
CREATE INDEX IF NOT EXISTS idx_votes_poll ON votes (poll_id);
CREATE INDEX IF NOT EXISTS idx_votes_option ON votes (option_id);
CREATE INDEX IF NOT EXISTS idx_votes_participant ON votes (participant_id);
