CREATE DATABASE DBS_WEINE;
USE DBS_WEINE;

CREATE TABLE ERZEUGER (
  Weingut varchar(20) primary key,
  Anbaugebiet varchar(20),
  Region varchar(20) not null
);

CREATE TABLE WEINE (
  WeinID int primary key auto_increment,
  Name varchar(20) not null,
  Farbe ENUM('Rot', 'Weiß', 'Rose') DEFAULT 'Rot',
  Jahrgang int,
  Weingut varchar(20) REFERENCES ERZEUGER (Weingut),
  UNIQUE(WeinID, Name, Weingut)
);

CREATE TABLE WEIN_EMPFEHLUNG (
  Wein varchar(20) not null,
  Kritiker varchar(20) not null,
  primary key (Wein, Kritiker)
);

CREATE TABLE GUIDES1 (
  Kritiker varchar (20) primary key
);

CREATE TABLE GUIDES2 (
  Kritiker varchar (20) primary key
);

INSERT INTO ERZEUGER VALUES ('Creek', 'Barossa Valley', 'South Australia');
INSERT INTO ERZEUGER VALUES ('Helena', 'Napa Valley', 'Kalifornien');
INSERT INTO ERZEUGER VALUES ('Chateau La Rose', 'Saint-Emilion', 'Bordeaux');
INSERT INTO ERZEUGER VALUES ('Chateau La Pointe', 'Pomerol', 'Bordeaux');
INSERT INTO ERZEUGER VALUES ('Müller', 'Rheingau', 'Hessen');
INSERT INTO ERZEUGER VALUES ('Bighorn', 'Napa Valley', 'Kalifornien');

INSERT INTO WEINE VALUES (1042, 'La Rose Grand Cru', 'Rot', 1998, 'Chateau La Rose');
INSERT INTO WEINE VALUES (2168, 'Creek Shiraz', 'Rot', 2003, 'Creek');
INSERT INTO WEINE VALUES (3456, 'Zinfandel', 'Rot', 2004, 'Helena');
INSERT INTO WEINE VALUES (2171, 'Pinot Noir', 'Rot', 2001, 'Creek');
INSERT INTO WEINE VALUES (3478, 'Pinot Noir', 'Rot', 1999, 'Helena');
INSERT INTO WEINE VALUES (4711, 'Riesling Reserve', 'Weiß', 1999, 'Müller');
INSERT INTO WEINE VALUES (4961, 'Chardonnay', 'Weiß', 2002, 'Bighorn');

INSERT INTO WEIN_EMPFEHLUNG VALUES ('La Rose Grand Cru','Parker');
INSERT INTO WEIN_EMPFEHLUNG VALUES ('Pinot Noir','Parker');
INSERT INTO WEIN_EMPFEHLUNG VALUES ('Riesling Reserve','Parker');
INSERT INTO WEIN_EMPFEHLUNG VALUES ('La Rose Grand Cru','Clarke');
INSERT INTO WEIN_EMPFEHLUNG VALUES ('Pinot Noir','Clarke');
INSERT INTO WEIN_EMPFEHLUNG VALUES ('Riesling Reserve','Gault-Millau');

INSERT INTO GUIDES1 VALUES ('Parker');
INSERT INTO GUIDES1 VALUES ('Clarke');

INSERT INTO GUIDES2 VALUES ('Parker');
INSERT INTO GUIDES2 VALUES ('Gault-Millau');
