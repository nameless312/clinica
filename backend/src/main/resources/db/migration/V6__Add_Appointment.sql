-- noinspection SqlNoDataSourceInspectionForFile
create table if not exists tappointments(
    appointment_id  serial,
    user_id         int4,
    client_id       int4,
    service_type_id int4,
    dt_added        timestamp(6) NOT NULL,
    dt_update       timestamp(6) NULL,
    dt_appointment  timestamp(6) not null,
    CONSTRAINT tappointments_pkey PRIMARY KEY (appointment_id),
    CONSTRAINT fkdkmtpgr1xjjqyafab8ux5tkud FOREIGN KEY (user_id) REFERENCES tuser (user_id),
    CONSTRAINT fkdkmtpgr1xjtsrafab8ux5tkud FOREIGN KEY (client_id) REFERENCES tclient (client_id),
    CONSTRAINT fkdkmtpgr1xjjqyaoyb8ux5tkud FOREIGN KEY (service_type_id) REFERENCES tservice_type (service_type_id)
);

alter table tappointments
alter column dt_added SET DEFAULT CURRENT_TIMESTAMP;