CREATE TABLE shop (
  id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE book (
  id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE shop_book (
  shop_id BIGINT NOT NULL,
  book_id BIGINT NOT NULL,
  PRIMARY KEY (shop_id, book_id)
);
ALTER TABLE shop_book
  ADD CONSTRAINT shop_id_fk FOREIGN KEY (shop_id) REFERENCES shop(id);
ALTER TABLE shop_book
    ADD CONSTRAINT book_id_fk FOREIGN KEY (book_id) REFERENCES  book(id);

