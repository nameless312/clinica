-- noinspection SqlNoDataSourceInspectionForFile
create table if not exists tservice_type(
    service_type_id serial,
    user_id         int4,
    dt_added        timestamp(6) NOT NULL,
    dt_update       timestamp(6) NULL,
    service_name    varchar(50) not null,
    reference_cost  decimal not null,
    CONSTRAINT tservice_type_pkey PRIMARY KEY (service_type_id),
    CONSTRAINT fkdkmyj531xjjqyafab8ux5tkud FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);


alter table tservice_type
alter column dt_added SET DEFAULT CURRENT_TIMESTAMP;