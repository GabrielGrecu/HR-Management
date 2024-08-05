ALTER TABLE user_management.Interview
    ADD COLUMN candidate_email VARCHAR(100) NOT NULL UNIQUE AFTER id;
