USE candidate_management;

ALTER TABLE candidate_management.feedback
RENAME COLUMN feedback_comm TO comment,
RENAME COLUMN user_role TO role,
RENAME COLUMN feedback_status TO status;