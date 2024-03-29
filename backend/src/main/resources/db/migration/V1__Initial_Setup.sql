CREATE TABLE tuser
(
    user_id    serial,
    dt_added   timestamp(6) NOT NULL,
    dt_update  timestamp(6) NULL,
    email      varchar(255) NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name  varchar(255) NOT NULL,
    "password" varchar(255) NOT NULL,
    "role"     varchar(255) NOT NULL,
    enabled    int2         NOT NULL,
    CONSTRAINT tuser_email_key UNIQUE (email),
    CONSTRAINT tuser_pkey PRIMARY KEY (user_id)
);

CREATE TABLE tdistrict
(
    district_id   serial,
    user_id       int4         NULL,
    dt_added      timestamp(6) NOT NULL,
    dt_update     timestamp(6) NULL,
    district_name varchar(255) NOT NULL,
    CONSTRAINT tdistrict_pkey PRIMARY KEY (district_id),
    CONSTRAINT fkdkmyj538xjjqyafab8ux3tkud FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

CREATE TABLE tconcelho
(
    concelho_id   serial,
    district_id   int4         NULL,
    user_id       int4         NULL,
    dt_added      timestamp(6) NOT NULL,
    dt_update     timestamp(6) NULL,
    concelho_name varchar(255) NOT NULL,
    CONSTRAINT tconcelho_pkey PRIMARY KEY (concelho_id),
    CONSTRAINT fkiunko8jyugrale4dy2smo8qoo FOREIGN KEY (district_id) REFERENCES tdistrict (district_id),
    CONSTRAINT fks7irk2sxxikdykkq0vkvhggil FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

CREATE TABLE tpartnerships
(
    commision      int4         NOT NULL,
    partnership_id serial,
    user_id        int4         NULL,
    dt_added       timestamp(6) NOT NULL,
    dt_update      timestamp(6) NULL,
    job            varchar(255) NOT NULL,
    locality       varchar(255) NOT NULL,
    mobile         varchar(255) NOT NULL,
    partner        varchar(255) NOT NULL,
    CONSTRAINT tpartnerships_pkey PRIMARY KEY (partnership_id),
    CONSTRAINT fk23mbhxmnjjepacrknjeh10wpy FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

CREATE TABLE tmarketing
(
    marketing_id serial,
    user_id      int4         NULL,
    dt_added     timestamp(6) NOT NULL,
    dt_update    timestamp(6) NULL,
    channel      varchar(255) NOT NULL,
    CONSTRAINT tmarketing_pkey PRIMARY KEY (marketing_id),
    CONSTRAINT fk486bivabswy6vu9j25b6vvhmb FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

CREATE TABLE taddress
(
    address_id  serial,
    concelho_id int4         NULL,
    district_id int4         NULL,
    user_id     int4         NULL,
    dt_added    timestamp(6) NOT NULL,
    dt_update   timestamp(6) NULL,
    city        varchar(255) NOT NULL,
    locality    varchar(255) NOT NULL,
    street_name varchar(255) NOT NULL,
    zip_code    varchar(255) NOT NULL,
    CONSTRAINT taddress_pkey PRIMARY KEY (address_id),
    CONSTRAINT fkay8jjaa0h3a9j4t2xga7q4be0 FOREIGN KEY (concelho_id) REFERENCES tconcelho (concelho_id),
    CONSTRAINT fkgcxek6eeatw5id4u8cnc9m1x5 FOREIGN KEY (district_id) REFERENCES tdistrict (district_id),
    CONSTRAINT fkjd3fessnf0f9991sspbeqdio7 FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

CREATE TABLE tclient
(
    address_id     int4         NULL,
    client_id      serial,
    marketing_id   int4         NULL,
    partnership_id int4         NULL,
    user_id        int4         NULL,
    birthdate      timestamp(6) NULL,
    dt_added       timestamp(6) NOT NULL,
    dt_registered  timestamp(6) NOT NULL,
    dt_update      timestamp(6) NULL,
    email          varchar(255) NULL,
    full_name      varchar(255) NOT NULL,
    gender         varchar(255) NULL,
    lanline        varchar(255) NULL,
    mobile         varchar(255) NULL,
    name_abbr      varchar(255) NOT NULL,
    notes          varchar(255) NULL,
    ssn            varchar(255) NULL,
    CONSTRAINT tclient_pkey PRIMARY KEY (client_id),
    CONSTRAINT fk2qnaoi8mpmvs2j8ojpyqqp1ql FOREIGN KEY (marketing_id) REFERENCES tmarketing (marketing_id),
    CONSTRAINT fkcxsfb700utm4nm05qobx4odnd FOREIGN KEY (user_id) REFERENCES tuser (user_id),
    CONSTRAINT fkmordrblxnis8oy9t2j4iccjdy FOREIGN KEY (partnership_id) REFERENCES tpartnerships (partnership_id),
    CONSTRAINT fkpa7xl1a4cyii78d5jtwb8isg0 FOREIGN KEY (address_id) REFERENCES taddress (address_id)
);