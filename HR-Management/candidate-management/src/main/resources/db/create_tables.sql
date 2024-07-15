CREATE TABLE candidate_management.candidate (
	id INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(55) NOT NULL,
	birthday DATE NULL,
	email VARCHAR(45) NOT NULL,
	phone_number VARCHAR(45) NOT NULL,
	city VARCHAR(45) NULL,
	faculty VARCHAR(65) NULL,
	years_experience INT NULL,
	recruitment_channel VARCHAR(45) NULL,
	PRIMARY KEY (id)
);

CREATE TABLE candidate_management.job (
	id INT NOT NULL AUTO_INCREMENT,
    title ENUM('Full-stack Developer', 'Front-end Developer', 'Back-end Developer', 'QA Tester') NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE candidate_management.candidate_job (
	candidate_id INT NOT NULL,
	job_id INT NOT NULL,
    PRIMARY KEY (candidate_id, job_id),
    CONSTRAINT candidate_id
        FOREIGN KEY (candidate_id) REFERENCES candidate_management.candidate (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT job_id
        FOREIGN KEY (job_id) REFERENCES candidate_management.job (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE candidate_management.document (
	id INT NOT NULL AUTO_INCREMENT,
    `type` ENUM('PDF', 'Word') NOT NULL,
    `name` VARCHAR(55) NOT NULL,
    `content` BLOB NOT NULL,
    candidate_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_doc_candidate_id
	  FOREIGN KEY (candidate_id) REFERENCES candidate_management.candidate (id)
	  ON DELETE CASCADE 
      ON UPDATE CASCADE
);

CREATE TABLE candidate_management.feedback (
	id INT NOT NULL AUTO_INCREMENT,
    `comment` VARCHAR(155) NOT NULL,
    user_role ENUM('Hr', 'Tech') NOT NULL,
    `status` ENUM('In Review', 'Rejected', 'Accepted', 'Archived') NOT NULL,
    candidate_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_fb_candidate_id
	  FOREIGN KEY (candidate_id) REFERENCES candidate_management.candidate (id)
	  ON DELETE CASCADE 
      ON UPDATE CASCADE
);