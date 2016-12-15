--liquibase formatted sql

--changeset serg:1

INSERT INTO role(name) VALUES('ROLE_USER');
INSERT INTO role(name) VALUES('ROLE_ADMIN');
INSERT INTO role(name) VALUES('ROLE_MANAGER');

INSERT INTO house(city, street, zip_code, description) VALUES('Lviv', 'Rjashivska 9', '79040', 'a nice house with a lot of parking spaces');
INSERT INTO apartment (number, square, house_id, owner_id) VALUES(1, 44, 1, 1);
INSERT INTO apartment (number, square, house_id, owner_id) VALUES(2, 67, 1, 2);
INSERT INTO apartment (number, square, house_id, owner_id) VALUES(3, 98, 1, 3);
INSERT INTO apartment (number, square, house_id, owner_id) VALUES(4, 67, 1, 4);
INSERT INTO apartment (number, square, house_id, owner_id) VALUES(5, 100, 1, 5);
INSERT INTO apartment (number, square, house_id, owner_id) VALUES(6, 87, 1, 6);
INSERT INTO apartment (number, square, house_id, owner_id) VALUES(7, 89, 1, 7);
INSERT INTO apartment (number, square, house_id, owner_id) VALUES(8, 66, 1, 8);
INSERT INTO apartment (number, square, house_id) VALUES(9,  55, 1);
INSERT INTO apartment (number, square, house_id) VALUES(10, 69, 1);
INSERT INTO apartment (number, square, house_id) VALUES(11, 99, 1);

INSERT into regions(id,name)VALUES(1,'АР Крим');
INSERT into regions(id,name)VALUES(2,'Вінницька обл.');
INSERT into regions(id,name)VALUES(3,'Волинська обл.');
INSERT into regions(id,name)VALUES(4,'Дніпропетровська обл.');
INSERT into regions(id,name)VALUES(5,'Донецька обл.');
INSERT into regions(id,name)VALUES(6,'Житомирська обл.');
INSERT into regions(id,name)VALUES(7,'Закарпатська обл.');
INSERT into regions(id,name)VALUES(8,'Запорізька обл.');
INSERT into regions(id,name)VALUES(9,'Івано-Франківська обл.');
INSERT into regions(id,name)VALUES(10,'Київська обл.');
INSERT into regions(id,name)VALUES(11,'Кіровоградська обл.');
INSERT into regions(id,name)VALUES(12,'Луганська обл.');
INSERT into regions(id,name)VALUES(13,'Львівська обл.');
INSERT into regions(id,name)VALUES(14,'Миколаївська обл.');
INSERT into regions(id,name)VALUES(15,'Одеська обл.');
INSERT into regions(id,name)VALUES(16,'Полтавська обл.');
INSERT into regions(id,name)VALUES(17,'Рівненська обл.');
INSERT into regions(id,name)VALUES(18,'Сумська обл.');
INSERT into regions(id,name)VALUES(19,'Тернопільська обл.');
INSERT into regions(id,name)VALUES(20,'Харківська обл.');
INSERT into regions(id,name)VALUES(21,'Херсонська обл.');
INSERT into regions(id,name)VALUES(22,'Хмельницька обл.');
INSERT into regions(id,name)VALUES(23,'Черкаська обл.');
INSERT into regions(id,name)VALUES(24,'Чернівецька обл.');
INSERT into regions(id,name)VALUES(25,'Чернігівська обл.');

INSERT into cities(id,name,region)VALUES(1,'Львів',13);
INSERT into cities(id,name,region)VALUES(2,'Стрий',13);
INSERT into cities(id,name,region)VALUES(3,'Дрогобич',13);
INSERT into cities(id,name,region)VALUES(4,'Самбір',13);
INSERT into cities(id,name,region)VALUES(5,'Київ',10);
INSERT into cities(id,name,region)VALUES(6,'Васильків',10);
INSERT into cities(id,name,region)VALUES(7,'Тернопіль',19);
INSERT into cities(id,name,region)VALUES(8,'Ужгород',7);
INSERT into cities(id,name,region)VALUES(9,'Мукачево',7);

INSERT into streets(id,name,city)VALUES(1,'Героїв УПА',1);
INSERT into streets(id,name,city)VALUES(2,'Кульпарківська',1);
INSERT into streets(id,name,city)VALUES(3,'Володимира Великого',1);
INSERT into streets(id,name,city)VALUES(4,'Водогінна',1);
INSERT into streets(id,name,city)VALUES(5,'Курмановича',1);
INSERT into streets(id,name,city)VALUES(6,'Січових стрільців',2);
INSERT into streets(id,name,city)VALUES(7,'Нижанківського',2);
INSERT into streets(id,name,city)VALUES(8,'Коцюбинського',2);
INSERT into streets(id,name,city)VALUES(9,'Шевченка',3);
INSERT into streets(id,name,city)VALUES(10,'Франка',3);
INSERT into streets(id,name,city)VALUES(11,'Саксаганського',5);
INSERT into streets(id,name,city)VALUES(12,'Сагайдачного',5);
INSERT into streets(id,name,city)VALUES(13,'Короленка',5);
INSERT into streets(id,name,city)VALUES(14,'Перемоги пр-т',5);
INSERT into streets(id,name,city)VALUES(15,'Довженка',5);
INSERT into streets(id,name,city)VALUES(16,'Зелена',7);
INSERT into streets(id,name,city)VALUES(17,'Бандери',7);
INSERT into streets(id,name,city)VALUES(18,'Володимирська',8);
INSERT into streets(id,name,city)VALUES(19,'Собранецька',8);
INSERT into streets(id,name,city)VALUES(20,'Садова',9);

INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,is_owner,activated) VALUES ('2016-06-13','admin@admin','Admin','admin','Adminius','$2a$06$IeFQ1f0OMzPvfrwMvUrvqO6ghg.Qayu57t6nRAhgY9Ic18muPHGAC','380000000','2',1,TRUE,TRUE);
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,is_owner) VALUES ('1993-10-31','vilumkris@gmail.com','kris','female','vilum','$2a$06$IeFQ1f0OMzPvfrwMvUrvqO6ghg.Qayu57t6nRAhgY9Ic18muPHGAC','380931286864','1',2,TRUE );
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,is_owner) VALUES ('1990-11-28', 'n.svidersky@gmail.com','Nazar','male','Sviderskyi','$2a$06$fGiJsjD0U/ZoHbmjab2ytOoedYAB1zQ0XANiGUQXAwv9TcyxV.Qfe','0931544845','1',3,TRUE );
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,is_owner) VALUES ('1996-02-21','KostetskyRoma@gmail.com','Roman','male','Kostetsky','$2a$06$R33Bzp5v3k5l5xs1n3dwQuQ/fM1DjCMldqthEXhvnI7Cu3gOQ4ms6','380687773508','1',4,TRUE );
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,is_owner, activated) VALUES ('1992-12-30','butaroman@gmail.com','Roman','male','Buta','$2a$06$IeFQ1f0OMzPvfrwMvUrvqO6ghg.Qayu57t6nRAhgY9Ic18muPHGAC','0632571119','1',5,TRUE, TRUE );
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,is_owner) VALUES ('1997-06-02','cavayman@gmail.com','Oleg','male','Kotsik','$2a$06$IeFQ1f0OMzPvfrwMvUrvqO6ghg.Qayu57t6nRAhgY9Ic18muPHGAC','0679167305','1',6,TRUE );
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,is_owner) VALUES ('1991-04-14','nazardovhyi@gmail.com','Nazar','male','Dovhyi','$2a$06$FHyRBL.Yc54J8K1XoKPF9.wWzyThXZd/6kNBLY8BxyoaGUb.YtFjG','0973055976','3',7,TRUE );
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,is_owner,activated) VALUES ('1994-12-16','oleg111koval@gmail.com','Oleg','male','Koval','$2a$06$QfIksIErYqbeoD3Pnxai7Ott22NGF8G38GsC/pQVpGG/rt55IYhUy','095729666','1',8,TRUE,TRUE);
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,activated) VALUES ('1992-12-30','butaroman1@gmail.com','Roman1','male','Buta1','$2a$06$IeFQ1f0OMzPvfrwMvUrvqO6ghg.Qayu57t6nRAhgY9Ic18muPHGAC','0632571119','1',8,TRUE );
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,activated) VALUES ('1992-12-30','butaroman2@gmail.com','Roman2','male','Buta2','$2a$06$IeFQ1f0OMzPvfrwMvUrvqO6ghg.Qayu57t6nRAhgY9Ic18muPHGAC','0632571119','1',8,TRUE );
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,activated) VALUES ('1992-12-30','butaroman3@gmail.com','Roman3','male','Buta3','$2a$06$IeFQ1f0OMzPvfrwMvUrvqO6ghg.Qayu57t6nRAhgY9Ic18muPHGAC','0632571119','1',8,TRUE );
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,activated) VALUES ('1994-08-18','nkharabaruk@gmail.com','Nataliia','female','Kharabaruk','$2a$06$IeFQ1f0OMzPvfrwMvUrvqO6ghg.Qayu57t6nRAhgY9Ic18muPHGAC','0638142706','1',4,TRUE );
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,is_owner,activated) VALUES ('1996-02-21','user@gov.ua','User','male','TheUser','$2a$10$izk0e40i9NsxgRnwoafnnOANYf9iqhxyKp1P83LW.C2RysO0KnoOi','380687773508','1',4,TRUE,TRUE);
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,is_owner,activated) VALUES ('1983-12-31','ewing@nyk.com','Patrick','male','Ewing','$2a$10$UEjkwHIn9fMnNs7r4IXAduJ9rIv3tGatMqnt7GjWqaklljt2RoP9K','095729666','1',8,TRUE,TRUE);
INSERT INTO users(  birth_date,email, first_name, gender, last_name, password,phone_number,role,apartment_id,is_owner,activated) VALUES ('1989-12-22','headmaster@gov.ua','Arnold','male','Shwarzenegger','$2a$06$IeFQ1f0OMzPvfrwMvUrvqO6ghg.Qayu57t6nRAhgY9Ic18muPHGAC','380953334444','3',1,TRUE,TRUE);

INSERT INTO provider_type ( type_name) VALUES ('Internet');
INSERT INTO provider_type ( type_name) VALUES ('Trash');
INSERT INTO provider_type ( type_name) VALUES ('Electricity');
INSERT INTO provider_type ( type_name) VALUES ('Water');
INSERT INTO provider_type ( type_name) VALUES ('House cleaning');
INSERT INTO provider_type ( type_name) VALUES ('Some other provider');

INSERT INTO provider (name, description, logo_url, periodicity, provider_type_id, email, phone, address, schedule, active)
VALUES ('Volya', 'Інтернет провайдер', 'https://lh3.googleusercontent.com/ChIb4t3-f90R0s4qu3BGuTh2-t0HoA7q6L6fTkRdfMrMKDj3rgYBR3SiNxcjr_o9FMU=w300', 'PERMANENT_MONTHLY', 1,
'volya@gmail.com', '(093)226-86-34', 'м. Львів, вул. Золотогірська 5а', 'пн-пт 09.00-20.00', TRUE );
INSERT INTO provider (name, description, logo_url, periodicity, provider_type_id, email, phone, address, schedule, active)
VALUES ('Trash Provider', 'Служба виносу сміття', 'http://www.ci.stillwater.mn.us/vertical/Sites/%7B5BFEF821-C140-4887-AEB5-99440411EEFD%7D/uploads/%7B99CA72DD-0AF9-4598-BC43-EAA2BB6625B4%7D.GIF', 'PERMANENT_MONTHLY', 2,
'trash@gmail.com', '(093)226-86-34', 'м. Львів, вул. Широка 25', 'пн-пт 09.00-20.00', TRUE );
INSERT INTO provider (name, description, logo_url, periodicity, provider_type_id, email, phone, address, schedule, active)
VALUES ('Electro com', 'Електрослужба', 'https://stocklogos-pd.s3.amazonaws.com/styles/logo-medium-alt/logos/image/f576842989a8d05aa71c0f79ad1c48ae.png?itok=IK4NARiQ', 'PERMANENT_MONTHLY', 3,
'volya@gmail.com', '(093)226-86-34','м. Львів, вул. Личаківська 55',  'пн-пт 09.00-20.00', TRUE);
INSERT INTO provider (name, description, logo_url, periodicity, provider_type_id, email, phone, address, schedule, active)
VALUES ('True Water', 'Сервіс доставки води', 'http://edc1.securesites.net/~edc/drinkhydrasonic.com/images/logo/water-logo.png', 'PERMANENT_YEARLY', 4,
'true.water@gmail.com', '(093)226-86-34', 'м. Львів, вул. Городоцька 217', 'пн-пт 09.00-20.00', TRUE);
INSERT INTO provider (name, description, logo_url, periodicity, provider_type_id, email, phone, address, schedule, active)
VALUES ('Awesome', 'Прибирання під''їзду', 'http://www.sketchappsources.com/resources/source-image/pacman.jpg', 'PERMANENT_MONTHLY', 5,
'aws@gmail.com', '(093)226-86-34', 'м. Львів, вул. Пасічна 62б', 'пн-пт 09.00-20.00', TRUE );
INSERT INTO provider (name, description, logo_url, periodicity, provider_type_id, email, phone, address, schedule, active)
VALUES ('Google inc', 'Інформаційна компанія', 'https://pbs.twimg.com/profile_images/762369348300251136/5Obhonwa.jpg', 'ONE_TIME', 6,
'google@gmail.com', '(093)226-86-34', 'м. Львів, вул. Бенцаля 3', 'пн-пт 09.00-20.00', TRUE);

INSERT INTO contract ( date_start, date_finish, price, price_currency, text, provider_id, active)
VALUES ('2016-08-03', '2018-09-05', 100, 'UAH',  'Опис контракту', 1, TRUE);
INSERT INTO contract (date_start, date_finish, price, price_currency, text, provider_id, active)
VALUES ('2002-08-03', '2019-05-07', 630,  'UAH', 'Опис контракту', 2, TRUE);
INSERT INTO contract (date_start, date_finish, price, price_currency, text, provider_id, active)
VALUES ('2007-08-03', '2018-09-05', 3200,  'UAH','Опис контракту', 3, TRUE);
INSERT INTO contract (date_start, date_finish, price, price_currency, text, provider_id, active)
VALUES ('2010-03-03', '2018-10-02', 100,  'UAH', 'Опис контракту', 5, TRUE);
INSERT INTO contract (date_start, date_finish, price, price_currency, text, provider_id, active)
VALUES ('2010-03-03', '2010-10-02', 150,  'UAH', 'Опис контракту', 6, TRUE);
INSERT INTO contract (date_start, date_finish, price, price_currency, text, provider_id, active)
VALUES ('2010-03-03', '2021-10-02', 100,  'UAH', 'Опис контракту', 5, TRUE);
INSERT INTO contract (date_start, date_finish, price, price_currency, text, provider_id, active)
VALUES ('2010-03-03', '2015-10-02', 98,  'UAH', 'Опис контракту', 4, TRUE);
INSERT INTO contract (date_start, date_finish, price, price_currency, text, provider_id, active)
VALUES ('2010-03-03', '2012-10-02', 100,  'UAH', 'Опис контракту', 3, TRUE);
INSERT INTO contract (date_start, date_finish, price, price_currency, text, provider_id, active)
VALUES ('2010-03-03', '2015-10-02', 98,  'UAH', 'Опис контракту', 2, TRUE);
INSERT INTO contract ( date_start, date_finish, price, price_currency, text, provider_id, active)
VALUES ('2016-08-03', '2018-09-05', 100, 'UAH',  'Опис контракту', 1, TRUE);

INSERT INTO osbb(  name, description, address, district, creation_date) VALUES ('Мій Дім','осбб для людей','м.Львів вул.Городоцька 147а','Залізничний','2016-09-01 10:46:43.221000');
INSERT INTO osbb(  name, description, address, district, creation_date) VALUES ('Червона Калина','найкраще осбб','м.Львів вул.В.Великого 99','Франківський','2014-09-05 10:46:43.221220');
INSERT INTO osbb(  name, description, address, district, creation_date) VALUES ('Двір','рівні права','м.Львів вул.Б.Хмельницького 77','Франківський','2012-05-27 08:20:43.221000');
INSERT INTO osbb(  name, description, address, district, creation_date) VALUES ('Район','осбб','м.Львів вул.Сихівськаа 33','Сихів','2015-10-15 10:46:43.221000');
INSERT INTO osbb(  name, description, address, district, creation_date) VALUES ('Надія','файне осбб','м.Львів вул.Гната Хоткевича 47а','Сихів','2010-08-01 10:46:43.221000');
INSERT INTO osbb(  name, description, address, district, creation_date) VALUES ('Весна',' ','м.Львів вул.Проспект Червоної Калини 11','Сихів','2016-05-10 12:46:43.221000');
INSERT INTO osbb(  name, description, address, district, creation_date) VALUES ('Мрія',' ','м.Львів вул.Городоцька 147а','Залізничний','2016-09-01 10:46:43.221000');
INSERT INTO osbb(  name, description, address, district, creation_date) VALUES ('Моя Оселя','','м.Львів вул.Повітряна 17б','Залізничний','2013-10-12 08:46:43.221340');
INSERT INTO osbb(  name, description, address, district, creation_date) VALUES ('Сонечко','','м.Львів вул.Наукова 77','Франківський','2015-04-18 10:46:43.221270');

INSERT INTO folder (folder_id, parent_id, osbb_id, name) VALUES(1, NULL, 1, 'ROOT FOLDER');
INSERT INTO folder (folder_id, parent_id, osbb_id, name) VALUES(2, 1, 1, 'Установчі документи');
INSERT INTO folder (folder_id, parent_id, osbb_id, name) VALUES(3, 1, 1, 'Протоколи зборів');
INSERT INTO folder (folder_id, parent_id, osbb_id, name) VALUES(4, 1, 1, 'Фінінсова звітність');
INSERT INTO folder (folder_id, parent_id, osbb_id, name) VALUES(5, 2, 1, 'Статут');
INSERT INTO folder (folder_id, parent_id, osbb_id, name) VALUES(6, 2, 1, 'Накази про призначення');
INSERT INTO folder (folder_id, parent_id, osbb_id, name) VALUES(7, 3, 1, 'Протокол установчих зборів');
INSERT INTO folder (folder_id, parent_id, osbb_id, name) VALUES(8, 3, 1, 'Some folder');

INSERT INTO attachment(  path, type, file_name) VALUES ('http://itukraine.org.ua/sites/default/files/news/sserve.jpg', 'IMAGE', 'sserve.jpg');
INSERT INTO attachment(  path, type, file_name) VALUES ('http://ua-ekonomist.com/uploads/posts/2013-08/1376494055_932378712.jpg', 'IMAGE', '1376494055_932378712.jpg');
INSERT INTO attachment(  path, type, file_name) VALUES ('http://news.dks.ua/images/0915/i_08091517_1.jpg', 'IMAGE', 'i_08091517_1.jpg');
INSERT INTO attachment(  path, type, file_name) VALUES ('http://www.abmk.com.ua/content/images/projects/138047835156_orig.jpg', 'IMAGE', '138047835156_orig.jpg');
INSERT INTO attachment(  path, type, file_name) VALUES ('http://nomerodyn.com/public/img/big/main.jpg', 'IMAGE', 'main.jpg');
INSERT INTO attachment(  path, type, file_name) VALUES ('http://archfest.com/web/images/139479223222_1.jpg', 'IMAGE', '139479223222_1.jpg');
INSERT INTO attachment(  path, type, file_name) VALUES ('https://www.askideas.com/media/39/Awesome-Empire-State-Building-Picture.jpg', 'IMAGE', 'Awesome-Empire-State-Building-Picture.jpg');
INSERT INTO attachment(  path, type, file_name) VALUES ('http://img1.globalinfo.ua/im/2014/02/12/TZA04E.jpg', 'IMAGE', 'TZA04E.jpg');
INSERT INTO attachment(  path, type, file_name) VALUES ('http://kyiv.ridna.ua/wp-content/uploads/2015/06/Project2_0014.jpg', 'IMAGE', 'Project2_0014.jpg');

insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status) VALUES('2016-05-28', 'Прибирання території', 350.5,0, 4.5, 7, 1, 'NOT_PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status) VALUES('2016-05-28', 'Озеленення', 280.5,120, 4.5, 1, 1, 'NOT_PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, status, provider_id) VALUES('2016-05-28', 'Охорона', 320,320, 4.5, 3,  'PAID', 1);
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status)VALUES('2016-06-28', 'Прибирання території', 240.5,0, 2.5, 2, 1, 'NOT_PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, status, provider_id) VALUES('2016-06-28', 'Охорона', 356,356, 4.5, 7, 'PAID', 1);
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status)VALUES('2016-06-28', 'Озеленення', 60,0, 2, 8, 5, 'NOT_PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, status, provider_id) VALUES('2016-06-28', 'Прибирання сходової клітки', 120,120, 0.5, 6, 'PAID', 1);
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status)VALUES('2016-06-28', 'Прибирання території', 88,88, 1.5, 6, 6, 'PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, status, provider_id) VALUES('2016-06-28', 'Охорона', 356,356, 4.5, 7, 'PAID', 1);
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status) VALUES('2016-07-28', 'Прибирання сходової клітки', 240.5,0, 2, 3, 2, 'NOT_PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status) VALUES('2016-07-28', 'Озеленення', 150, 0, 4.5, 7, 3, 'NOT_PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status) VALUES('2016-07-28', 'Прибирання території', 188,50, 3, 5, 4, 'NOT_PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status) VALUES('2016-07-28', 'Озеленення', 120, 120, 4.5, 7, 4, 'PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, status, provider_id) VALUES('2016-07-28', 'Охорона', 350.5,350.5, 4.5, 4, 'PAID', 1);
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status) VALUES('2016-08-28', 'Прибирання сходової клітки', 90,0, 1, 1, 4, 'NOT_PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status) VALUES('2016-08-28', 'Прибирання території', 144.5, 144.5, 2, 2, 6, 'PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status) VALUES('2016-08-28', 'Озеленення', 199, 0, 2, 3, 3, 'NOT_PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id, status) VALUES('2016-08-28', 'Охорона', 120, 120, 4.5, 6, 6, 'PAID');
insert into bill(date, name, to_pay, paid, tariff, apartment_id, status, provider_id) VALUES('2016-08-28', 'Прибирання території', 150.5,150.5, 4, 7, 'PAID', 1);

UPDATE house SET osbb_id=1 WHERE house_id=1;

UPDATE users SET osbb_id=1;
UPDATE users SET osbb_id=2 WHERE email='butaroman2@gmail.com';

insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id)VALUES('2016-06-28', 'Охорона', 240.5,0, 2.5, 1, 1);
insert into bill(date, name, to_pay, paid, tariff, apartment_id, status, provider_id) VALUES('2016-06-28', 'Прибирання сходової клітки', 356,356, 4.5, 1, 'PAID', 1);
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id) VALUES('2016-07-28', 'Консьєржі', 240.5,0, 2, 3, 1);
insert into bill(date, name, to_pay, paid, tariff, apartment_id, provider_id) VALUES('2016-07-28', 'Охорона', 150, 0, 4.5, 2, 1);
insert into bill(date, name, to_pay, paid, tariff, apartment_id, status, provider_id) VALUES('2016-07-28', 'Прибирання сходової клітки', 350.5,0, 4.5, 4, 'PAID', 1);

INSERT into ticket(assigned,user_id,name,description,state,time,state_time, deadline)
VALUES(2,3,'Зламаний ліфт','Скільки вже дзвонимо майстру, просимо, щоб налаштував ліфт. Він строїть його, але наступного дня ліфт знову не працює.'
'Давате вже наймемо іншого майстра! Бо це тільки гроші бере, а нічого не вміє робити.','NEW','2016-09-02','2016-09-02', '2016-12-12');
INSERT into ticket(assigned,user_id,name,description,state,time,state_time, deadline)
VALUES(1,5,'Прибирання в під`їзді','Вже дістало це чургування в під`їзді. Хтось чесно виконує обов`язок, а хто навіть не береться за вінік.'
'Так не піде! Давайте наймено прибиральницю, і тоді в нас завжди буде чисто, і не буде отих сварок з-за прибирання.','DONE','2015-10-02','2015-12-12', '2016-12-12');
INSERT into ticket(assigned,user_id,name,description,state,time,state_time, deadline)
VALUES(2,2,'Замок на вхідні двері','Учора, повертаючись пізно з роботи, зустрів сплячого чоловіка. В нас не нічліжка, тому пропоную поставити'
'двері з кодовим замком, щоб ніхто не вештався по під`їзді. ','NEW','2015-11-02','2015-11-02', '2016-12-12');
INSERT into ticket(assigned,user_id,name,description,state,time,state_time, deadline)
VALUES(4,2,'Інтернет Воля','У вас добре працює інтернет? Бо я вже кілька днів не можу зайти на якийсь сайт! Воля, виправте, що це за перебої?','DONE','2016-04-02','2016-05-02', '2016-12-12');
INSERT into ticket(assigned,user_id,name,description,state,time,state_time, deadline)
VALUES(2,2,'Допоможіть! Візьміть Рекса на пару днів!!!','Завтра терміново їдемо по справах і не встигаємо віддати собаку родичам. Будемо дуже вдячні,'
'якщо хто зможе виручити нас. Всі ж знаєте Рекса, це дуже весела собака, з ним не засумуєте! Заздалегіть, дякуємо! ','NEW','2016-09-08','2016-09-08', '2016-12-12');
INSERT into ticket(assigned,user_id,name,description,state,time,state_time, deadline)
VALUES(6,3,'Назва тікету6','Опис тікету6','DONE','2015-01-02','2015-02-12', '2016-12-12');
INSERT into ticket(assigned,user_id,name,description,state,time,state_time, deadline)
VALUES(4,4,'Назва тікету7','Опис тікету7','NEW','2015-11-02','2015-12-12', '2016-12-12');
INSERT into ticket(assigned,user_id,name,description,state,time,state_time, deadline)
VALUES(1,5,'Назва тікету8','Опис тікету8','IN_PROGRESS','2015-08-02','2015-09-12', '2016-12-12');
INSERT into ticket(assigned,user_id,name,description,state,time,state_time, deadline)
VALUES(5,2,'Назва тікету9','Опис тікету9','NEW','2015-10-02','2015-12-12', '2016-12-12');

INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(1,TRUE,TRUE,TRUE,TRUE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(2,TRUE,TRUE,TRUE,TRUE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(3,TRUE,TRUE,TRUE,TRUE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(4,FALSE,FALSE,TRUE,TRUE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(5,TRUE,TRUE,TRUE,FALSE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(6,TRUE,FALSE,FALSE,TRUE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(7,FALSE,TRUE,TRUE,TRUE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(8,TRUE,TRUE,TRUE,FALSE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(9,TRUE,TRUE,FALSE,TRUE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(10,TRUE,TRUE,TRUE,TRUE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(11,FALSE,FALSE,FALSE,FALSE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(12,TRUE,FALSE,TRUE,TRUE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(13,FALSE,FALSE,FALSE,FALSE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(14,TRUE,FALSE,TRUE,TRUE );
INSERT into settings(user_id,assigned,creator,comment,answer)VALUES(15,TRUE,FALSE,TRUE,TRUE );

INSERT into event(title, author, start_time, end_time, description, repeats, osbb_id) VALUES(
'Процес пішов: три будинки передадуть з ЖЕКу до ОСББ',6,'2016-09-30 07:00:00','2016-10-10 07:00:00',
'Зокрема, на баланс ОСББ «Центр – Ч» передадуть два житлових будинки, а саме № 15 по вулиці Чернишевського та № 15-А по вулиці Чернишевського.'
'На баланс ОСББ «Реконструкція» планується передати житловий будинок № 18 по вулиці Мстиславській.','ONE_TIME',2);
INSERT into event(title, author, start_time, end_time, description, repeats, osbb_id) VALUES(
'В Києві відбудуться безкоштовні семінари із створення ОСББ',9,'2017-01-20 10:00:00','2017-01-20 12:30:00',
'20 та 23 січня в Києві стартує цикл безкоштовних семінарів з питань створення та організації ефективної роботи ОСББ.','PERMANENT_WEEKLY',6);
INSERT into event(title, author, start_time, end_time, description, repeats, osbb_id) VALUES(
'У Броварах відбувся Форум ОСББ',2,'2016-06-11 09:00:00','2016-06-12 00:00:00',
'11 червня в Броварах відбулася унікальна подія – масштабний Форум ОСББ, який зібрав експертів з усієї країни, ','ONE_TIME',1);
INSERT into event(title, author, start_time, end_time, description, repeats, osbb_id) VALUES(
'Мешканців міста знайомлять з порядком створення ОСББ',4,'2016-01-01 08:00:00','2017-01-01 08:00:00',
'1 липня 2015 р. набрав чинності Закон України «Про особливості здійснення права власності в багатоквартирному будинку».','ONE_TIME',4);

--rollback