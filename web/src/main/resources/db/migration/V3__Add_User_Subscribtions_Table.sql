CREATE TABLE user_subscribtions (
  channel_id    BIGINT NOT NULL,
  subscriber_id BIGINT NOT NULL,
  PRIMARY KEY (channel_id, subscriber_id)
);
ALTER TABLE user_subscribtions
  ADD CONSTRAINT channel_id_fk FOREIGN KEY (channel_id) REFERENCES usr (id);
ALTER TABLE user_subscribtions
    ADD CONSTRAINT subscriber_id_fk FOREIGN KEY (subscriber_id) REFERENCES  usr(id);

