CREATE TABLE users
(
    id                      UUID                                      NOT NULL,
    email                   VARCHAR(255)                              NOT NULL,
    password                VARCHAR(255)                              NOT NULL,
    name                    VARCHAR(255)                              NOT NULL,
    authentication_provider VARCHAR(255)                              NOT NULL,
    role                    VARCHAR(255)                DEFAULT 'USER',
    created_at              TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at              TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);
