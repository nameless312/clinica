CREATE SEQUENCE tuser_user_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE taddress_address_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tconcelho_concelho_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tdistrict_district_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tmarketing_marketing_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tpartnerships_partnership_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

CREATE TABLE tuser
(
    user_id    serial4      NOT NULL,
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
    district_id   int4         NOT NULL,
    user_id       int4         NULL,
    dt_added      timestamp(6) NOT NULL,
    dt_update     timestamp(6) NULL,
    district_name varchar(255) NOT NULL,
    CONSTRAINT tdistrict_pkey PRIMARY KEY (district_id),
    CONSTRAINT tdistrict_user_id_key UNIQUE (user_id),
    CONSTRAINT fkdkmyj538xjjqyafab8ux3tkud FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

CREATE TABLE tconcelho
(
    concelho_id   int4         NOT NULL,
    district_id   int4         NULL,
    user_id       int4         NULL,
    dt_added      timestamp(6) NOT NULL,
    dt_update     timestamp(6) NULL,
    concelho_name varchar(255) NOT NULL,
    CONSTRAINT tconcelho_district_id_key UNIQUE (district_id),
    CONSTRAINT tconcelho_pkey PRIMARY KEY (concelho_id),
    CONSTRAINT tconcelho_user_id_key UNIQUE (user_id),
    CONSTRAINT fkiunko8jyugrale4dy2smo8qoo FOREIGN KEY (district_id) REFERENCES tdistrict (district_id),
    CONSTRAINT fks7irk2sxxikdykkq0vkvhggil FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

CREATE TABLE tpartnerships
(
    commision      int4         NOT NULL,
    partnership_id int4         NOT NULL,
    user_id        int4         NULL,
    dt_added       timestamp(6) NOT NULL,
    dt_update      timestamp(6) NULL,
    job            varchar(255) NOT NULL,
    locality       varchar(255) NOT NULL,
    mobile         varchar(255) NOT NULL,
    partner        varchar(255) NOT NULL,
    CONSTRAINT tpartnerships_pkey PRIMARY KEY (partnership_id),
    CONSTRAINT tpartnerships_user_id_key UNIQUE (user_id),
    CONSTRAINT fk23mbhxmnjjepacrknjeh10wpy FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

CREATE TABLE tmarketing
(
    marketing_id int4         NOT NULL,
    user_id      int4         NULL,
    dt_added     timestamp(6) NOT NULL,
    dt_update    timestamp(6) NULL,
    channel      varchar(255) NOT NULL,
    CONSTRAINT tmarketing_pkey PRIMARY KEY (marketing_id),
    CONSTRAINT tmarketing_user_id_key UNIQUE (user_id),
    CONSTRAINT fk486bivabswy6vu9j25b6vvhmb FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

CREATE TABLE taddress
(
    address_id  int4         NOT NULL,
    concelho_id int4         NULL,
    district_id int4         NULL,
    user_id     int4         NULL,
    dt_added    timestamp(6) NOT NULL,
    dt_update   timestamp(6) NULL,
    city        varchar(255) NOT NULL,
    locality    varchar(255) NOT NULL,
    street_name varchar(255) NOT NULL,
    zip_code    varchar(255) NOT NULL,
    CONSTRAINT taddress_concelho_id_key UNIQUE (concelho_id),
    CONSTRAINT taddress_district_id_key UNIQUE (district_id),
    CONSTRAINT taddress_pkey PRIMARY KEY (address_id),
    CONSTRAINT taddress_user_id_key UNIQUE (user_id),
    CONSTRAINT fkay8jjaa0h3a9j4t2xga7q4be0 FOREIGN KEY (concelho_id) REFERENCES tconcelho (concelho_id),
    CONSTRAINT fkgcxek6eeatw5id4u8cnc9m1x5 FOREIGN KEY (district_id) REFERENCES tdistrict (district_id),
    CONSTRAINT fkjd3fessnf0f9991sspbeqdio7 FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

CREATE TABLE tclient
(
    address_id     int4         NULL,
    client_id      int4         NOT NULL,
    marketing_id   int4         NULL,
    partnership_id int4         NULL,
    user_id        int4         NULL,
    birthdate      timestamp(6) NULL,
    dt_added       timestamp(6) NOT NULL,
    dt_registered  timestamp(6) NULL,
    dt_update      timestamp(6) NULL,
    email          varchar(255) NULL,
    full_name      varchar(255) NOT NULL,
    gender         varchar(255) NULL,
    lanline        varchar(255) NULL,
    mobile         varchar(255) NULL,
    name_abbr      varchar(255) NOT NULL,
    notes          varchar(255) NULL,
    ssn            varchar(255) NULL,
    CONSTRAINT tclient_address_id_key UNIQUE (address_id),
    CONSTRAINT tclient_marketing_id_key UNIQUE (marketing_id),
    CONSTRAINT tclient_partnership_id_key UNIQUE (partnership_id),
    CONSTRAINT tclient_pkey PRIMARY KEY (client_id),
    CONSTRAINT tclient_user_id_key UNIQUE (user_id),
    CONSTRAINT fk2qnaoi8mpmvs2j8ojpyqqp1ql FOREIGN KEY (marketing_id) REFERENCES tmarketing (marketing_id),
    CONSTRAINT fkcxsfb700utm4nm05qobx4odnd FOREIGN KEY (user_id) REFERENCES tuser (user_id),
    CONSTRAINT fkmordrblxnis8oy9t2j4iccjdy FOREIGN KEY (partnership_id) REFERENCES tpartnerships (partnership_id),
    CONSTRAINT fkpa7xl1a4cyii78d5jtwb8isg0 FOREIGN KEY (address_id) REFERENCES taddress (address_id)
);