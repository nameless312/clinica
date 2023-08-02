alter table tclient
alter column birthdate type Date;

alter table tuser
alter column dt_added SET DEFAULT CURRENT_TIMESTAMP;

alter table tdistrict
alter column dt_added SET DEFAULT CURRENT_TIMESTAMP;

alter table tconcelho
alter column dt_added SET DEFAULT CURRENT_TIMESTAMP;

alter table tpartnerships
alter column dt_added SET DEFAULT CURRENT_TIMESTAMP;

alter table tmarketing
alter column dt_added SET DEFAULT CURRENT_TIMESTAMP;

alter table taddress
alter column dt_added SET DEFAULT CURRENT_TIMESTAMP;

alter table tclient
alter column dt_added SET DEFAULT CURRENT_TIMESTAMP;