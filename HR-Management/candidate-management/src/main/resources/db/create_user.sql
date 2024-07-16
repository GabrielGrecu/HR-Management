CREATE USER 'si-2024'@'%' IDENTIFIED BY 'internship2024';
GRANT ALL PRIVILEGES ON candidate_management.* TO 'si-2024'@'%';
GRANT ALL PRIVILEGES ON user_management.* TO 'si-2024'@'%';
FLUSH PRIVILEGES;