CREATE TABLE refresh_token(
    id BIGINT NOT NULL PRIMARY KEY UNIQUE ,
    token VARCHAR(255) NOT NULL UNIQUE ,
    expiry_date  TIMESTAMP NOT NULL ,
    user_id VARCHAR NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE categories
DROP COLUMN description