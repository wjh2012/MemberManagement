CREATE TABLE member
(
    id               BIGINT AUTO_INCREMENT,
    is_oauth         bit,
    auth_type        VARCHAR(255),
    oauth_id         VARCHAR(255),
    nickname         VARCHAR(255),
    username         VARCHAR(255),
    password         VARCHAR(255),
    created_datetime datetime(6),

    PRIMARY KEY (id)
);

CREATE TABLE role
(
    id        BIGINT AUTO_INCREMENT,
    role_name VARCHAR(255),

    PRIMARY KEY (id)
);

CREATE TABLE member_role
(
    id        BIGINT AUTO_INCREMENT,
    member_id BIGINT,
    role_id   BIGINT,
    grant_date datetime(6),

    PRIMARY KEY (id)
);


CREATE TABLE team
(
    id        BIGINT AUTO_INCREMENT,
    team_name VARCHAR(255),
    created_datetime datetime(6),

    PRIMARY KEY (id)
);

CREATE TABLE member_team
(
    id        BIGINT AUTO_INCREMENT,
    member_id BIGINT,
    team_id   BIGINT,
    join_date datetime(6),

    PRIMARY KEY (id)
);

