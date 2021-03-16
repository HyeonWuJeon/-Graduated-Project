insert into member(city,street,zipcode,birth,email,name,password,phone,role) values('서울 강북구 도봉로 88','303호','01165',
'1995-11-29','user@gmail.com',
'유저1','$2a$10$vc.n7gsWnwwWt38bEh7wnuSSUQjNNGEOIWiEKP4XUGq2p1jD00pey',
'010-111-111','GUEST');
insert into member(city,street,zipcode,birth,email,name,password,phone,role) values('서울 강북구 도봉로1','302호',
'01167','2001-11-29','doctor@gmail.com',
'수의사1','$2a$10$vc.n7gsWnwwWt38bEh7wnuSSUQjNNGEOIWiEKP4XUGq2p1jD00pey',
'010-111-111','VET');
insert into member(city,street,zipcode,birth,email,name,password,phone,role) values('충남 아산시 중앙로','302호',
'01167','2001-11-29','doctor1@gmail.com',
'수의사2','$2a$10$vc.n7gsWnwwWt38bEh7wnuSSUQjNNGEOIWiEKP4XUGq2p1jD00pey',
'010-111-111','VET');
insert into member(city,street,zipcode,birth,email,name,password,phone,role) values('서울 강남구 압구정로 437','302호',
'01167','2001-11-29','doctor2@gmail.com',
'수의사3','$2a$10$vc.n7gsWnwwWt38bEh7wnuSSUQjNNGEOIWiEKP4XUGq2p1jD00pey',
'010-111-111','VET');
insert into member(city,street,zipcode,birth,email,name,password,phone,role) values('서울 강북구 도봉로 1','302호',
'01167','1995-11-29','admin@gmail.com',
'관리자1','$2a$10$vc.n7gsWnwwWt38bEh7wnuSSUQjNNGEOIWiEKP4XUGq2p1jD00pey',
'010-111-111','ADMIN');


insert into hospital(hospital_address,hospital_name, hospital_tel,member_id) values('서울 강북구 도봉로 88','중앙동물병원','02-982-8698',2);
insert into hospital(hospital_address,hospital_name, hospital_tel,member_id) values('충남 아산시 중앙로 10','주앤동물병원','041-547-0275',3);
insert into hospital(hospital_address,hospital_name, hospital_tel,member_id) values('서울 강남구 압구정로 437','혜민동물병원','02-454-8275', 4);

update member set hospital_id = 1 where member_id=2;
update member set hospital_id = 2 where member_id=3;
update member set hospital_id = 3 where member_id=4;


insert into dog(age, birth,gender, name,type,member_id) values('생후 12개월 미만','2020-06-30','암컷','다은리','포메라니안',1);
insert into symptom(name) values('발열');
insert into symptom(name) values('설사');
insert into symptom(name) values('급사');
insert into symptom(name) values('과호흡');
insert into symptom(name) values('탈모');
insert into symptom(name) values('무기력');
insert into symptom(name) values('식욕부진');
insert into symptom(name) values('거리감각불능');

