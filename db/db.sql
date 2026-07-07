create DATABASE quarkus-social;

CREATE Table users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL UNIQUE
);

CREATE TABLE posts(
    id BIGSERIAL PRIMARY KEY,
    post_test VARCHAR(150) NOT NULL,
    dateTime TIMESTAMP,
    user_id BIGINT NOT NULL REFERENCES users(id)
);