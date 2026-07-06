create Table quarkus-social;

CREATE Table users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL UNIQUE
);