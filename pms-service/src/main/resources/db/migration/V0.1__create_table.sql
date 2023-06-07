DROP SEQUENCE IF EXISTS currency_seq CASCADE;
CREATE SEQUENCE currency_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS currency CASCADE;
CREATE TABLE currency(
                         id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('currency_seq'),
                         name VARCHAR(3) NOT NULL
);


DROP SEQUENCE IF EXISTS asset_type_seq CASCADE;
CREATE SEQUENCE asset_type_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS asset_type CASCADE;
CREATE TABLE asset_type
(
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('asset_type_seq'),
    name VARCHAR(100) NOT NULL
);


DROP SEQUENCE IF EXISTS asset_seq CASCADE;
CREATE SEQUENCE asset_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS asset CASCADE;
CREATE TABLE asset
(
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('asset_seq'),
    name VARCHAR(150) NOT NULL,
    currency_id BIGINT NOT NULL REFERENCES currency(id),
    asset_type_id BIGINT NOT NULL REFERENCES asset_type(id),
    user_id BIGINT NOT NULL,
    status BOOL NOT NULL
);


DROP SEQUENCE IF EXISTS transaction_type_seq CASCADE;
CREATE SEQUENCE transaction_type_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS transaction_type CASCADE;
CREATE TABLE transaction_type
(
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('transaction_type_seq'),
    name VARCHAR(70) NOT NULL
);


DROP SEQUENCE IF EXISTS portfolio_seq CASCADE;
CREATE SEQUENCE portfolio_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS portfolio CASCADE;
CREATE TABLE portfolio
(
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('portfolio_seq'),
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    currency_id BIGINT NOT NULL REFERENCES currency(id),
    status BOOL NOT NULL
);


DROP SEQUENCE IF EXISTS share_transaction_seq CASCADE;
CREATE SEQUENCE share_transaction_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS share_transaction CASCADE;
CREATE TABLE share_transaction
(
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('share_transaction_seq'),
    portfolio_id BIGINT NOT NULL REFERENCES portfolio(id),
    change_of_quantity NUMERIC(12,6) NOT NULL,
    change_of_cost BIGINT NOT NULL,
    time TIMESTAMP NOT NULL
);


DROP SEQUENCE IF EXISTS transaction_seq CASCADE;
CREATE SEQUENCE transaction_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction
(
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('transaction_seq'),
    portfolio_id BIGINT NOT NULL REFERENCES portfolio(id),
    asset_id BIGINT NOT NULL REFERENCES asset(id),
    transaction_type_id BIGINT NOT NULL REFERENCES transaction_type(id),
    change_of_quantity NUMERIC(10,6) NOT NULL,
    change_of_main_cost BIGINT NOT NULL,
    change_of_portfolio_cost BIGINT NOT NULL,
    time TIMESTAMP NOT NULL
);


DROP SEQUENCE IF EXISTS asset_track_seq CASCADE;
CREATE SEQUENCE asset_track_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS asset_track CASCADE;
CREATE TABLE asset_track
(
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('asset_track_seq'),
    asset_id BIGINT NOT NULL REFERENCES asset(id),
    value BIGINT NOT NULL,
    time TIMESTAMP NOT NULL
);