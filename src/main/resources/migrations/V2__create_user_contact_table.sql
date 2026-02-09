CREATE TABLE user_contact (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user_contact_user
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
);
