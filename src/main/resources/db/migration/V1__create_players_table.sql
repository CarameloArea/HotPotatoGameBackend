CREATE TABLE players
(
    id            SERIAL PRIMARY KEY,
    nickname      VARCHAR(100) NOT NULL,
    email         VARCHAR(150) NOT NULL,
    password      VARCHAR NOT NULL,
    icon          VARCHAR(50)

);