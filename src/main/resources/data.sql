DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    roles VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, roles),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert default admin user
INSERT INTO users (username, password) VALUES ('admin', '$2a$10$nws/7sRMAQJ/iAFlMglBmO6AL4.FFAZRPfYOLMZmkFVlraWdAHlgi');

-- Assign ROLE_ADMIN to admin user using dynamic user ID
INSERT INTO user_roles (user_id, roles)
VALUES (
  (SELECT id FROM users WHERE username = 'admin'),
  'ROLE_ADMIN'
);