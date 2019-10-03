CREATE TABLE category (
  id     BIGINT NOT NULL,
  icon   LONGBLOB,
  name   VARCHAR(30),
  parent BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE category_filter (
  category_id BIGINT NOT NULL,
  filter_id   BIGINT NOT NULL,
  PRIMARY KEY (category_id, filter_id)
);

CREATE TABLE company (
  full_name        VARCHAR(100),
  f_home_apartment VARCHAR(20),
  f_home_city      VARCHAR(100),
  f_home_street    VARCHAR(20),
  f_home_zipcode   VARCHAR(6),
  restricted_name  VARCHAR(40),
  tax_id           VARCHAR(8),
  customer_id      BIGINT NOT NULL,
  PRIMARY KEY (customer_id)
);

CREATE TABLE customer (
  apartment        VARCHAR(20),
  city             VARCHAR(100),
  street           VARCHAR(20),
  zip_code         VARCHAR(6),
  contact_name     VARCHAR(40) DEFAULT '',
  contact_phone    VARCHAR(12),
  contact_title    VARCHAR(40) DEFAULT '',
  last_modifiedldt DATETIME,
  user_id          BIGINT NOT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE customer_order (
  id              BIGINT NOT NULL,
  createldt       DATETIME,
  order_status    VARCHAR(40),
  requiredldt     DATETIME,
  apartment       VARCHAR(20),
  city            VARCHAR(100),
  street          VARCHAR(20),
  zip_code        VARCHAR(6),
  ship_via        VARCHAR(40),
  shippedldt      DATETIME,
  employee_id     BIGINT,
  freight_id      BIGINT,
  order_detail_id BIGINT,
  user_id         BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE employee (
  birthld           DATETIME,
  expire_contractld DATETIME NOT NULL,
  hireld            DATETIME,
  photo             LONGBLOB,
  salary            INTEGER  NOT NULL,
  title             VARCHAR(20),
  person_id         BIGINT   NOT NULL,
  PRIMARY KEY (person_id)
);

CREATE TABLE filter (
  filter_id   BIGINT NOT NULL,
  description VARCHAR(255),
  name        VARCHAR(40),
  necessary   BIT    NOT NULL,
  PRIMARY KEY (filter_id)
);

CREATE TABLE freight (
  id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO hibernate_sequence VALUES (1);
INSERT INTO hibernate_sequence VALUES (1);
INSERT INTO hibernate_sequence VALUES (1);
INSERT INTO hibernate_sequence VALUES (1);
INSERT INTO hibernate_sequence VALUES (1);
INSERT INTO hibernate_sequence VALUES (1);
INSERT INTO hibernate_sequence VALUES (1);
INSERT INTO hibernate_sequence VALUES (1);
INSERT INTO hibernate_sequence VALUES (1);
INSERT INTO hibernate_sequence VALUES (1);

CREATE TABLE order_detail (
  id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE person (
  createdldt  DATETIME,
  father_name VARCHAR(40),
  first_name  VARCHAR(40),
  last_name   VARCHAR(40),
  customer_id BIGINT NOT NULL,
  PRIMARY KEY (customer_id)
);

CREATE TABLE product (
  product_id        BIGINT           NOT NULL,
  code              VARCHAR(60),
  delivery_country  VARCHAR(20) DEFAULT '',
  description       VARCHAR(1000),
  icon              LONGBLOB,
  image             LONGBLOB,
  made_country      VARCHAR(20) DEFAULT '',
  name              VARCHAR(100),
  price             DOUBLE PRECISION NOT NULL,
  quantity_future   INTEGER          NOT NULL,
  quantity_in_stock INTEGER          NOT NULL,
  quantity_reserved INTEGER          NOT NULL,
  vendor            VARCHAR(20),
  category_id       BIGINT,
  supplier_id       BIGINT,
  PRIMARY KEY (product_id)
);

CREATE TABLE product_filter (
  filter_id  BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  data       VARCHAR(40) DEFAULT '',
  PRIMARY KEY (filter_id, product_id)
);

CREATE TABLE product_detail (
  id              BIGINT  NOT NULL,
  count           INTEGER NOT NULL,
  discount        DECIMAL(19, 2),
  order_status    VARCHAR(255),
  price           DECIMAL(19, 2),
  order_detail_id BIGINT,
  product_id      BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE supplier (
  id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE category_filter
  ADD CONSTRAINT FKqwcdorkudyroawiskarmhqopu FOREIGN KEY (filter_id) REFERENCES filter (filter_id);
ALTER TABLE category_filter
  ADD CONSTRAINT FKdme9e3n7y9akaymvh3s7gvi2b FOREIGN KEY (category_id) REFERENCES category (id);
ALTER TABLE company
  ADD CONSTRAINT FK2wwo1k62uauhsigdlf4hgfnky FOREIGN KEY (customer_id) REFERENCES customer (user_id);
ALTER TABLE customer
  ADD CONSTRAINT FKptv929uyoreysdjm9aqaofnx5 FOREIGN KEY (user_id) REFERENCES usr (id);
ALTER TABLE customer_order
  ADD CONSTRAINT FKf52e0mwmej2lj9vs20iapb5gm FOREIGN KEY (employee_id) REFERENCES employee (person_id);
ALTER TABLE customer_order
  ADD CONSTRAINT FK6bvpmmrc4uaptcp8d8un6s3ps FOREIGN KEY (freight_id) REFERENCES freight (id);
ALTER TABLE customer_order
  ADD CONSTRAINT FKhohqy7yh5iav8t8qowme8s26o FOREIGN KEY (order_detail_id) REFERENCES order_detail (id);
ALTER TABLE customer_order
  ADD CONSTRAINT FKpvp6c5m100a76e54bf673txyu FOREIGN KEY (user_id) REFERENCES usr (id);
ALTER TABLE employee
  ADD CONSTRAINT FKfm68kmqett1iydj8xgfb6two8 FOREIGN KEY (person_id) REFERENCES person (customer_id);
ALTER TABLE message
  ADD CONSTRAINT FK70bv6o4exfe3fbrho7nuotopf FOREIGN KEY (user_id) REFERENCES usr (id);
ALTER TABLE person
  ADD CONSTRAINT FK6peuv9pl9e7ofaddti6louuh1 FOREIGN KEY (customer_id) REFERENCES customer (user_id);
ALTER TABLE product
  ADD CONSTRAINT FK1mtsbur82frn64de7balymq9s FOREIGN KEY (category_id) REFERENCES category (id);
ALTER TABLE product
  ADD CONSTRAINT FK2kxvbr72tmtscjvyp9yqb12by FOREIGN KEY (supplier_id) REFERENCES supplier (id);
ALTER TABLE product_filter
  ADD CONSTRAINT FKk7gguod0k9gjmmutcyp9idyxp FOREIGN KEY (filter_id) REFERENCES filter (filter_id);
ALTER TABLE product_filter
  ADD CONSTRAINT FKgvf8d7inf5k7l745vsomqpxcg FOREIGN KEY (product_id) REFERENCES product (product_id);
ALTER TABLE product_detail
  ADD CONSTRAINT FKfjgw5bh36m7lx7t8nowtxmnfj FOREIGN KEY (order_detail_id) REFERENCES order_detail (id);
ALTER TABLE product_detail
  ADD CONSTRAINT FKilxoi77ctyin6jn9robktb16c FOREIGN KEY (product_id) REFERENCES product (product_id);