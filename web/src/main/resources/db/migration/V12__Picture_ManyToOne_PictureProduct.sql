CREATE TABLE picture (
  id BIGINT NOT NULL,
  path VARCHAR(255),
  priority INTEGER NOT NULL,
  product_gallery_id BIGINT,
  product_history_gallery_id BIGINT,
  PRIMARY KEY (id)
);

ALTER TABLE picture
  ADD CONSTRAINT FKproductGallery FOREIGN KEY (product_gallery_id) REFERENCES product (id);

ALTER TABLE picture
  ADD CONSTRAINT FKproductHistoryGallery FOREIGN KEY (product_history_gallery_id) REFERENCES product (id);