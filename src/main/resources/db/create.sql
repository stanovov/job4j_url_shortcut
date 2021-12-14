CREATE TABLE IF NOT EXISTS sites
(
    id       SERIAL PRIMARY KEY  NOT NULL,
    site_url VARCHAR(255) UNIQUE NOT NULL,
    login    VARCHAR(50) UNIQUE  NOT NULL,
    password VARCHAR(150)        NOT NULL
);

CREATE TABLE IF NOT EXISTS urls
(
    id           SERIAL PRIMARY KEY   NOT NULL,
    original_url VARCHAR(2048) UNIQUE NOT NULL,
    code         VARCHAR(30)          NOT NULL,
    call_counter INT DEFAULT 0        NOT NULL,
    site_id      INT                  NOT NULL,
    FOREIGN KEY (site_id) REFERENCES sites (id)
);