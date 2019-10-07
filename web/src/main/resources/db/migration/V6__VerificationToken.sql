CREATE TABLE verification_token (
  token       VARCHAR(16) NOT NULL,
  expiry_date DATETIME,
  user_id     BIGINT      NOT NULL,
  PRIMARY KEY (token)
);

ALTER TABLE verification_token
  ADD CONSTRAINT FK5fi7mv5p74mow94h0xxw50rui FOREIGN KEY (user_id) REFERENCES usr (id)