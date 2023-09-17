CREATE TABLE member
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    is_oauth         TINYINT(1),
    auth_type        VARCHAR(255),
    oauth_id         VARCHAR(255),
    nickname         VARCHAR(255),
    username         VARCHAR(255) UNIQUE,
    password         VARCHAR(255),
    created_datetime DATETIME
);

CREATE TABLE role
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255)
);

CREATE TABLE member_role
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT,
    role_id    BIGINT,
    grant_date DATETIME,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    UNIQUE KEY unique_member_role (member_id, role_id)
);
