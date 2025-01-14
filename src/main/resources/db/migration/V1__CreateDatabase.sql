CREATE TABLE users (
    id VARCHAR(36)NOT NULL PRIMARY KEY UNIQUE ,
    firstname VARCHAR NOT NULL ,
    lastname VARCHAR NOT NULL ,
    username VARCHAR NOT NULL ,
    email VARCHAR NOT NULL UNIQUE ,
    bio TEXT NOT NULL ,
    password VARCHAR NOT NULL ,
    role VARCHAR NOT NULL ,
    image VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE posts (
   id VARCHAR(36) NOT NULL PRIMARY KEY UNIQUE ,
   title VARCHAR NOT NULL ,
   content TEXT NOT NULL ,
   author_id VARCHAR NOT NULL ,
   FOREIGN KEY (author_id) REFERENCES users(id),
   status VARCHAR NOT NULL ,
   created_at TIMESTAMP NOT NULL ,
   updated_at TIMESTAMP NOT NULL
);

CREATE TABLE comments(
    id VARCHAR(36) NOT NULL PRIMARY KEY UNIQUE ,
    author_id VARCHAR NOT NULL ,
    FOREIGN KEY (author_id) REFERENCES users(id),
    text VARCHAR NOT NULL ,
    post_id VARCHAR NOT NULL ,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE categories(
    id  VARCHAR(36) NOT NULL PRIMARY KEY UNIQUE ,
    name VARCHAR(36) NOT NULL UNIQUE ,
    description VARCHAR NOT NULL
);
CREATE TABLE post_category(
    post_id VARCHAR NOT NULL ,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    category_id VARCHAR NOT NULL ,
    FOREIGN KEY (category_id) REFERENCES categories(id)
)