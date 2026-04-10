CREATE TABLE
    refresh_tokens (
        id UUID PRIMARY KEY,
        token VARCHAR(255) NOT NULL UNIQUE,
        owner_id UUID NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT NOW (),
        expires_at TIMESTAMP NOT NULL
    );