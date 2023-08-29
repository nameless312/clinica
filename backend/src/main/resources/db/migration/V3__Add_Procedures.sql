-- noinspection SqlNoDataSourceInspectionForFile

create table if not exists tprocedures(
    procedure_id  serial,
    user_id       int4         NULL,
    dt_added      timestamp(6) NOT NULL,
    dt_update     timestamp(6) NULL,
    procedure_desc varchar(50) not null,
    CONSTRAINT tprocedures_pkey PRIMARY KEY (procedure_id),
    CONSTRAINT fkdkmyj538xjjqyafab8ux5tkud FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

alter table tprocedures
alter column dt_added SET DEFAULT CURRENT_TIMESTAMP;