--create table  if not exists air (
--    percent varchar(255),
--     id bigint not null,
--     primary key (id)
--      ) engine=InnoDB;
--
--create table  if not exists corna (
--    percent varchar(255),
--    id bigint not null,
--    primary key (id)
--     ) engine=InnoDB;
--create table if not exists  macak (
--    percent varchar(255),
--    id bigint not null,
--    primary key (id)
--     ) engine=InnoDB;

create table if not exists diagnosis_table (
    disease_type varchar(31) not null,
    diagnosis_id bigint not null auto_increment,
    dog_id varchar(255),
    percent varchar(255),
    member_id bigint,
    primary key (diagnosis_id)
    ) engine=InnoDB;

create table  if not exists disease (
    id bigint not null auto_increment,
    description varchar(255),
    name varchar(255),
    primary key (id)
    ) engine=InnoDB;

create table  if not exists dog (
    id bigint not null auto_increment,
     age varchar(255),
     birth varchar(255),
     gender varchar(255),
     name varchar(255),
     type varchar(255),
     symptom_cause varchar(255),
     member_id bigint,
     primary key (id)
    ) engine=InnoDB;

create table  if not exists hospital (
    hospital_id bigint not null auto_increment,
    hospital_address varchar(255),
    hospital_name varchar(255),
    hospital_tel varchar(255),
    member_id bigint, primary key (hospital_id)
    ) engine=InnoDB;



create table  if not exists member (
    member_id bigint not null auto_increment,
    create_date datetime(6),
    modified_date datetime(6),
    city varchar(255),
    street varchar(255),
    zipcode varchar(255),
    birth varchar(255),
    email varchar(255),

    name varchar(255),
    password varchar(255),
    phone varchar(255),
    role varchar(255),
    hospital_id bigint,
    primary key (member_id)
    ) engine=InnoDB;

create table  if not exists reserve (
    reserve_id bigint not null auto_increment,
    address varchar(255),
    visit_date varchar(255) not null,
    description longtext,
    hospital_name varchar(255),
    tel varchar(255) not null,
    hospital_id bigint,
    member_id bigint,
    primary key (reserve_id)
    ) engine=InnoDB;

create table  if not exists symptom (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
    ) engine=InnoDB;

--create table  if not exists user (
--    id bigint not null auto_increment,
--    create_date datetime(6),
--    modified_date datetime(6),
--    email varchar(255) not null,
--    name varchar(255) not null,
--    picture varchar(255),
--    role varchar(255) not null,
--    primary key (id)
--   ) engine=InnoDB;

--alter table air
--    add constraint FKcc6eol2xu9t5d8wg872ka47s5h
--    foreign key (id)
--    references diagnosis_table (id);
--
--alter table corna
--    add constraint FKqc7c84v7u97m996jq56c5hcych
--    foreign key (id)
--    references diagnosis_table (id);
--
--alter table macak
--    add constraint FKa7si8i1w3qy6rwci0rta9fmt1h
--    foreign key (id)
--    references diagnosis_table (id);

alter table diagnosis_table
    add constraint FKfavgiy2gdj7j4jr4osk1nkx2hh
    foreign key (member_id)
    references member (member_id);

alter table dog
    add constraint FKhdpjd6l15cy5li9vatn0jh02xh
     foreign key (member_id)
     references member (member_id);

alter table hospital
    add constraint FK1pqmfky6triopw9mxlf3efm74h
    foreign key (member_id)
    references member (member_id);



alter table member
    add constraint FKenc3mb3qetarul3282iu5dny0h
     foreign key (hospital_id)
     references hospital (hospital_id);

alter table reserve
    add constraint FKfgy8n1drbcm6r8h1x4u7qiioih
    foreign key (hospital_id)
    references hospital (hospital_id);

alter table reserve
    add constraint FKktaxwc9vryh5v78n280p3fq1oh
    foreign key (member_id)
    references member (member_id);


create TABLE if not exists SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

create UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
create INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
create INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

create TABLE if not exists SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON delete CASCADE
);