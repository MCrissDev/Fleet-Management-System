DROP DATABASE fleet_management;
CREATE DATABASE fleet_management;

CREATE TABLE ships (
  id  SERIAL PRIMARY KEY
, ship_name text NOT NULL
, imo_number numeric NOT NULL DEFAULT 0
);

CREATE TABLE owner (
  owner_id   SERIAL PRIMARY KEY
, owner_name text NOT NULL
);

CREATE TABLE ships_owners (
  ship_id    int REFERENCES ships (id) ON UPDATE CASCADE
, owner_id int REFERENCES owner(owner_id) ON UPDATE CASCADE
, CONSTRAINT ship_owner_pkey PRIMARY KEY (ship_id, owner_id) 
);

