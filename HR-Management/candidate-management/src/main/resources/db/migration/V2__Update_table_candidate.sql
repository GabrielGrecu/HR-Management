USE candidate_management;

ALTER TABLE candidate ADD COLUMN address VARCHAR(150) NULL AFTER city;

ALTER TABLE candidate ADD COLUMN candidate_status VARCHAR(25) NULL AFTER recruitment_channel;

ALTER TABLE candidate ADD COLUMN status_date DATE NULL AFTER candidate_status;

ALTER TABLE candidate ADD COLUMN job_id INT NULL;

ALTER TABLE candidate_management.candidate
    ADD CONSTRAINT fk_job_id
        FOREIGN KEY(job_id) REFERENCES candidate_management.job(id)
            ON DELETE CASCADE ON UPDATE CASCADE;