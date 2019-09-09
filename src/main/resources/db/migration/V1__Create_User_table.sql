CREATE TABLE user
(
  id           INT AUTO_INCREMENT PRIMARY KEY,
  account_id   VARCHAR(50) NULL,
  name         VARCHAR(50) NULL,
  token        VARCHAR(50) NULL,
  gmt_create   BIGINT      NULL,
  gmt_modified BIGINT      NULL
)
  ENGINE = InnoDB;