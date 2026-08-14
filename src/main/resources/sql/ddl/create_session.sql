CREATE TABLE IF NOT EXISTS sessions(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_sessions_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
)