CREATE TABLE IF NOT EXISTS Role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES Role(id)
);

CREATE TABLE IF NOT EXISTS Interview (
    id INT AUTO_INCREMENT PRIMARY KEY,
    candidate_email VARCHAR(100) NOT NULL UNIQUE,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    interview_type VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    subject VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Interview_User (
    interview_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (interview_id, user_id),
    FOREIGN KEY (interview_id) REFERENCES Interview(id),
    FOREIGN KEY (user_id) REFERENCES User(id)
);