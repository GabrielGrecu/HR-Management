CREATE TABLE candidate_management.job (
	id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(45) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE candidate_management.candidate (
	id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(45) NOT NULL,
	birthday DATE NULL,
	email VARCHAR(45) NOT NULL UNIQUE,
	phone_number VARCHAR(45) NOT NULL UNIQUE,
	city VARCHAR(45) NULL,
	address VARCHAR(150) NULL,
	faculty VARCHAR(65) NULL,
	years_experience INT NULL,
	recruitment_channel VARCHAR(45) NULL,
	candidate_status VARCHAR(25) NULL,
	status_date DATE NULL,
    job_id INT NOT NULL,
	PRIMARY KEY (id),
    CONSTRAINT fk_job_id
        FOREIGN KEY (job_id) REFERENCES candidate_management.job (id)
            ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE candidate_management.document (
	id INT NOT NULL AUTO_INCREMENT,
    doc_type VARCHAR(25) NOT NULL,
    doc_name VARCHAR(45) NOT NULL,
    doc_content BLOB NOT NULL,
    candidate_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_doc_candidate_id
	  FOREIGN KEY (candidate_id) REFERENCES candidate_management.candidate (id)
	  ON DELETE CASCADE 
      ON UPDATE CASCADE
);

CREATE TABLE candidate_management.feedback (
	id INT NOT NULL AUTO_INCREMENT,
    comment VARCHAR(255) NOT NULL,
    role VARCHAR(45) NOT NULL,
    status VARCHAR(45) NOT NULL,
    candidate_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_fb_candidate_id
	  FOREIGN KEY (candidate_id) REFERENCES candidate_management.candidate (id)
	  ON DELETE CASCADE 
      ON UPDATE CASCADE
);