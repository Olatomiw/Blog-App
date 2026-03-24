-- V{n}__create_user_preferences.sql

CREATE TABLE user_preferences (
                                  id VARCHAR(36) NOT NULL PRIMARY KEY UNIQUE,
                                  user_id  VARCHAR(36) NOT NULL UNIQUE,

                                  CONSTRAINT fk_user_preferences_user
                                      FOREIGN KEY (user_id)
                                          REFERENCES users(id)
                                          ON DELETE CASCADE
);

CREATE TABLE user_preferred_categories (
                                           preference_id   VARCHAR(36)      NOT NULL,
                                           category_id     VARCHAR(36) NOT NULL,
                                           PRIMARY KEY (preference_id, category_id),

                                           CONSTRAINT fk_upc_preference
                                               FOREIGN KEY (preference_id)
                                                   REFERENCES user_preferences(id)
                                                   ON DELETE CASCADE,

                                           CONSTRAINT fk_upc_category
                                               FOREIGN KEY (category_id)
                                                   REFERENCES categories(id)
                                                   ON DELETE CASCADE
);

CREATE INDEX idx_user_preferences_user_id ON user_preferences(user_id);
CREATE INDEX idx_upc_preference_id ON user_preferred_categories(preference_id);
CREATE INDEX idx_upc_category_id ON user_preferred_categories(category_id);