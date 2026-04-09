CREATE TABLE
    posts (
        id UUID PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        content TEXT NOT NULL,
        status VARCHAR(20) NOT NULL DEFAULT 'DRAFT' CHECK (status IN ('DRAFT', 'PUBLISHED')),
        category_id UUID NOT NULL,
        author_id UUID NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT NOW (),
        updated_at TIMESTAMP NOT NULL DEFAULT NOW (),
        CONSTRAINT fk_posts_categories FOREIGN KEY (category_id) REFERENCES categories (id),
        CONSTRAINT fk_posts_users FOREIGN KEY (author_id) REFERENCES users (id)
    )