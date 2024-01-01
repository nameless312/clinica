-- noinspection SqlNoDataSourceInspectionForFile
create table if not exists ttechnique(
    technique_id    serial,
    user_id         int4,
    dt_added        timestamp(6) NOT NULL,
    dt_update       timestamp(6) NULL,
    technique       varchar(50) not null,
    CONSTRAINT technique_pkey PRIMARY KEY (technique_id),
    CONSTRAINT fkdkmtpgr1xjjqyafab8ux5tkud FOREIGN KEY (user_id) REFERENCES tuser (user_id)
);

alter table ttechnique
alter column dt_added SET DEFAULT CURRENT_TIMESTAMP;