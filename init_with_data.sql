BEGIN TRANSACTION;
--
-- PostgreSQL database dump
--

DROP SCHEMA IF EXISTS volunteersservice CASCADE;
DROP ROLE IF EXISTS java;

CREATE ROLE java WITH LOGIN PASSWORD '123654';

-- Dumped from database version 12.0
-- Dumped by pg_dump version 12.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: volunteersservice; Type: SCHEMA; Schema: -; Owner: java
--

CREATE SCHEMA volunteersservice;


ALTER SCHEMA volunteersservice OWNER TO java;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: events; Type: TABLE; Schema: volunteersservice; Owner: java
--

CREATE TABLE volunteersservice.events (
    eventid integer NOT NULL,
    organiserid integer NOT NULL,
    coordinatorid integer,
    name character varying(100) NOT NULL,
    description character varying(2000) NOT NULL,
    place character varying(300) NOT NULL,
    datestart timestamp with time zone NOT NULL,
    datefinish timestamp with time zone NOT NULL,
    statusid integer NOT NULL,
    message character varying(200) DEFAULT ''::character varying NOT NULL
);


ALTER TABLE volunteersservice.events OWNER TO java;

--
-- Name: events_eventid_seq; Type: SEQUENCE; Schema: volunteersservice; Owner: java
--

CREATE SEQUENCE volunteersservice.events_eventid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE volunteersservice.events_eventid_seq OWNER TO java;

--
-- Name: events_eventid_seq; Type: SEQUENCE OWNED BY; Schema: volunteersservice; Owner: java
--

ALTER SEQUENCE volunteersservice.events_eventid_seq OWNED BY volunteersservice.events.eventid;


--
-- Name: eventstatus; Type: TABLE; Schema: volunteersservice; Owner: java
--

CREATE TABLE volunteersservice.eventstatus (
    statusid integer NOT NULL,
    name character varying(30) NOT NULL
);


ALTER TABLE volunteersservice.eventstatus OWNER TO java;

--
-- Name: eventstatus_statusid_seq; Type: SEQUENCE; Schema: volunteersservice; Owner: java
--

CREATE SEQUENCE volunteersservice.eventstatus_statusid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE volunteersservice.eventstatus_statusid_seq OWNER TO java;

--
-- Name: eventstatus_statusid_seq; Type: SEQUENCE OWNED BY; Schema: volunteersservice; Owner: java
--

ALTER SEQUENCE volunteersservice.eventstatus_statusid_seq OWNED BY volunteersservice.eventstatus.statusid;


--
-- Name: userrole; Type: TABLE; Schema: volunteersservice; Owner: java
--

CREATE TABLE volunteersservice.userrole (
    roleid integer NOT NULL,
    name character varying(30) NOT NULL
);


ALTER TABLE volunteersservice.userrole OWNER TO java;

--
-- Name: userrole_roleid_seq; Type: SEQUENCE; Schema: volunteersservice; Owner: java
--

CREATE SEQUENCE volunteersservice.userrole_roleid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE volunteersservice.userrole_roleid_seq OWNER TO java;

--
-- Name: userrole_roleid_seq; Type: SEQUENCE OWNED BY; Schema: volunteersservice; Owner: java
--

ALTER SEQUENCE volunteersservice.userrole_roleid_seq OWNED BY volunteersservice.userrole.roleid;


--
-- Name: users; Type: TABLE; Schema: volunteersservice; Owner: java
--

CREATE TABLE volunteersservice.users (
    userid integer NOT NULL,
    login character varying(20) NOT NULL,
    email character varying(40) NOT NULL,
    name character varying(60) NOT NULL,
    registerdate timestamp with time zone NOT NULL,
    passwdhash1 character varying(128) NOT NULL,
    passwdhash2 character varying(128) NOT NULL,
    roleid integer NOT NULL
);


ALTER TABLE volunteersservice.users OWNER TO java;

--
-- Name: users_userid_seq; Type: SEQUENCE; Schema: volunteersservice; Owner: java
--

CREATE SEQUENCE volunteersservice.users_userid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE volunteersservice.users_userid_seq OWNER TO java;

--
-- Name: users_userid_seq; Type: SEQUENCE OWNED BY; Schema: volunteersservice; Owner: java
--

ALTER SEQUENCE volunteersservice.users_userid_seq OWNED BY volunteersservice.users.userid;


--
-- Name: usersvolunteerfunctions; Type: TABLE; Schema: volunteersservice; Owner: java
--

CREATE TABLE volunteersservice.usersvolunteerfunctions (
    uservolunteerfunctionid integer NOT NULL,
    userid integer NOT NULL,
    volunteerfunctionid integer NOT NULL,
    statusid integer NOT NULL,
    numberofhours integer,
    estimation integer
);


ALTER TABLE volunteersservice.usersvolunteerfunctions OWNER TO java;

--
-- Name: usersvolunteerfunctions_uservolunteerfunctionid_seq; Type: SEQUENCE; Schema: volunteersservice; Owner: java
--

CREATE SEQUENCE volunteersservice.usersvolunteerfunctions_uservolunteerfunctionid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE volunteersservice.usersvolunteerfunctions_uservolunteerfunctionid_seq OWNER TO java;

--
-- Name: usersvolunteerfunctions_uservolunteerfunctionid_seq; Type: SEQUENCE OWNED BY; Schema: volunteersservice; Owner: java
--

ALTER SEQUENCE volunteersservice.usersvolunteerfunctions_uservolunteerfunctionid_seq OWNED BY volunteersservice.usersvolunteerfunctions.uservolunteerfunctionid;


--
-- Name: uservolunteerfunctionstatus; Type: TABLE; Schema: volunteersservice; Owner: java
--

CREATE TABLE volunteersservice.uservolunteerfunctionstatus (
    statusid integer NOT NULL,
    name character varying(30) NOT NULL
);


ALTER TABLE volunteersservice.uservolunteerfunctionstatus OWNER TO java;

--
-- Name: uservolunteerfunctionstatus_statusid_seq; Type: SEQUENCE; Schema: volunteersservice; Owner: java
--

CREATE SEQUENCE volunteersservice.uservolunteerfunctionstatus_statusid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE volunteersservice.uservolunteerfunctionstatus_statusid_seq OWNER TO java;

--
-- Name: uservolunteerfunctionstatus_statusid_seq; Type: SEQUENCE OWNED BY; Schema: volunteersservice; Owner: java
--

ALTER SEQUENCE volunteersservice.uservolunteerfunctionstatus_statusid_seq OWNED BY volunteersservice.uservolunteerfunctionstatus.statusid;


--
-- Name: volunteerfunctions; Type: TABLE; Schema: volunteersservice; Owner: java
--

CREATE TABLE volunteersservice.volunteerfunctions (
    volunteerfunctionid integer NOT NULL,
    eventid integer NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(500) NOT NULL,
    requirements character varying(200) DEFAULT ''::character varying NOT NULL,
    timestart timestamp with time zone NOT NULL,
    timefinish timestamp with time zone NOT NULL,
    numberneeded integer NOT NULL
);


ALTER TABLE volunteersservice.volunteerfunctions OWNER TO java;

--
-- Name: volunteerfunctions_volunteerfunctionid_seq; Type: SEQUENCE; Schema: volunteersservice; Owner: java
--

CREATE SEQUENCE volunteersservice.volunteerfunctions_volunteerfunctionid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE volunteersservice.volunteerfunctions_volunteerfunctionid_seq OWNER TO java;

--
-- Name: volunteerfunctions_volunteerfunctionid_seq; Type: SEQUENCE OWNED BY; Schema: volunteersservice; Owner: java
--

ALTER SEQUENCE volunteersservice.volunteerfunctions_volunteerfunctionid_seq OWNED BY volunteersservice.volunteerfunctions.volunteerfunctionid;


--
-- Name: events eventid; Type: DEFAULT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.events ALTER COLUMN eventid SET DEFAULT nextval('volunteersservice.events_eventid_seq'::regclass);


--
-- Name: eventstatus statusid; Type: DEFAULT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.eventstatus ALTER COLUMN statusid SET DEFAULT nextval('volunteersservice.eventstatus_statusid_seq'::regclass);


--
-- Name: userrole roleid; Type: DEFAULT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.userrole ALTER COLUMN roleid SET DEFAULT nextval('volunteersservice.userrole_roleid_seq'::regclass);


--
-- Name: users userid; Type: DEFAULT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.users ALTER COLUMN userid SET DEFAULT nextval('volunteersservice.users_userid_seq'::regclass);


--
-- Name: usersvolunteerfunctions uservolunteerfunctionid; Type: DEFAULT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.usersvolunteerfunctions ALTER COLUMN uservolunteerfunctionid SET DEFAULT nextval('volunteersservice.usersvolunteerfunctions_uservolunteerfunctionid_seq'::regclass);


--
-- Name: uservolunteerfunctionstatus statusid; Type: DEFAULT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.uservolunteerfunctionstatus ALTER COLUMN statusid SET DEFAULT nextval('volunteersservice.uservolunteerfunctionstatus_statusid_seq'::regclass);


--
-- Name: volunteerfunctions volunteerfunctionid; Type: DEFAULT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.volunteerfunctions ALTER COLUMN volunteerfunctionid SET DEFAULT nextval('volunteersservice.volunteerfunctions_volunteerfunctionid_seq'::regclass);


--
-- Data for Name: events; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.events (eventid, organiserid, coordinatorid, name, description, place, datestart, datefinish, statusid, message) FROM stdin;
3	1	6	День добровольца для серебряных волонтеров	5 декабря празднуется Международный день добровольца!    С 1985 года этот праздник проводится как напоминание о вкладе добровольцев в развитие различных сфер жизни общества.  Коллектив организации "Серебряный возраст" не остался в стороне. Его представители соберутся в стенах университета ИТМО, для 	пер. Гривцова 14-16	2019-12-08 11:00:00+03	2019-12-08 18:00:00+03	4	
1	1	8	Профориентационный проект "ITMO.STEP"	ITMO.STEP - бесплатный профориентационный проект для учащихся 8-11 классов. Курс помимо лекций содержит в себе мастер-классы, экскурсии в настоящие лаборатории и крутые тренинги, направленные на развитие личностных качеств, а также на знакомство со структурой университета. При успешном прохождении у	г. Санкт-Петербург, Ломоносова 9	2019-10-14 16:30:00+03	2019-11-16 19:00:00+03	4	
2	1	8	Квантовый потанцевал 5.0	«Квантовый потанцевал» возвращается! «Квантовый потанцевал» продолжается!  10 декабря в 19:00 в клубе «Космонавт» состоится уже пятый научный стендап от первого неклассического 🔥  Зовите друзей по парте, коллег по лабе, партнеров по гранту. Будет смешно!	Клуб "Космонавт"	2019-12-10 12:00:00+03	2019-12-10 21:00:00+03	4	
9	3	7	Фестиваль крыс в Этажах	Это отличное мероприятие для того, чтобы познакомиться с этими удивительными смышлеными созданиями!   Оба дня фестиваля посетителей ждет:  - экспозиция настоящих живых крыс от Клуба крысоводов СПб - фотозона с крысами - фонды помощи брошенным крысам - питомники декоративных крыс.	Этажи	2019-10-26 12:00:00+03	2019-10-27 19:00:00+03	4	
5	1	8	Мастер-класс в 22й больнице Новогодние открытки	Проводим мастер-класс в 22й детской больнице. Создадим вместе с детьми объёмные новогодние открытки. 	22я детская больница, Колпино	2019-12-17 16:00:00+03	2019-12-17 17:30:00+03	4	
4	1	8	Курс по обучению пожилых #Всети	Курсы по обучению пожилых смартфонам и социальным сетям "#Всети" возвращаются!  Все мы давно привыкли пользоваться смартфонами и научиться этому подсилу любому, даже бабушкам и дедушкам. Все что требуется, чуточку больше  времени и немного вашей помощи 😌  В рамках курса #Всети предлагаем вам провес	Центр Добрососедства "Дом"	2019-12-02 10:00:00+03	2019-12-13 16:00:00+03	4	
8	1	7	Клубный турнир «Питерские игры 2019»	С 16 по 20 октября 2019 года студенческий спортивный клуб Университета ИТМО «Кронверкские барсы» при поддержке Федерального агентства по делам молодежи и АССК России проведет в Санкт-Петербурге Всероссийский клубный турнир «Питерские игры 2019». 	Ломоносова 9	2019-10-16 08:00:00+03	2019-10-20 23:00:00+03	4	
7	2	6	Закрытие Марафона Долголетия 2019	5 октября 2019 в Выборге состоится закрытие соревнований по скандинавской ходьбе «Марафон долголетия 2019»! Марафон долголетия охватывает крупные города Ленинградской области и будет проходить уже в который раз.  Мы ищем волонтёров для помощи в проведении мероприятия 📢   	Стадион Авангард, Физкультурная ул., 2, Выборг, Ленинградская обл., 188800	2019-10-05 09:00:00+03	2019-10-05 15:00:00+03	4	
10	3	\N	Международная научно-практическая конференция «Све	Мероприятие является открытой международной дискуссионной платформой для представителей науки, бизнеса, искусства и инновационных технологий и соберет свыше 300 ведущих российских и иностранных экспертов в области светового дизайна, архитектуры, искусства, урбанистики, инженерии, IT и мультимедиа. 	Этажи	2019-10-31 10:00:00+03	2019-11-02 18:00:00+03	5	Неполное описание
6	2	6	Осенний марафон	20 октября 2019 в Лемболово состоится легкоатлетический кросс и соревнование по скандинавской ходьбе  «Осенний марафон»!   Мы ищем волонтёров для помощи в проведении мероприятия 📢 	Лемболово 	2019-10-20 09:00:00+03	2019-10-20 16:00:00+03	4	
\.


--
-- Data for Name: eventstatus; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.eventstatus (statusid, name) FROM stdin;
1	UNCHECKED
2	APPROVED
3	COORDINATED
4	PUBLISHED
5	REJECTED
\.


--
-- Data for Name: userrole; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.userrole (roleid, name) FROM stdin;
1	ORGANISER
2	MANAGER
3	COORDINATOR
4	VOLUNTEER
5	ADMIN
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.users (userid, login, email, name, registerdate, passwdhash1, passwdhash2, roleid) FROM stdin;
1	ITMO	itmo@mail.ru	Университет ИТМО	2019-12-15 22:36:56.359+03	09dfd683f06d00c2b133ef83012840389f89b498220d2a15e1f73af7da6e3241	b6a893d8fb12d4f119490b4921587c69	1
2	fast_run	run@mal.ru	Марафон долголетия	2019-09-16 00:24:19.877+03	a85123bf0a4682c8b0c71df3f6fa754b609d72503e0fed4b3f05b265e25d3992	f6a679d8b0ef9b47a6081a26a38050ca	1
3	Floors	Floors@mail.ru	Этажи	2019-09-16 01:00:39.435+03	f20080f987706c5ab6f6a06694a26dbcb6e18b464dfbb32c3a5b46e1b9d7d959	8d74234efb65264ead93ed1cfd5b1641	1
4	root	root@mail.ru	root	2019-12-16 02:12:16.746+03	4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2	63a9f0ea7bb98050796b649e85481845	5
5	manager	manager@mail.ru	manager	2019-12-16 02:35:30.818+03	6ee4a469cd4e91053847f5d3fcb61dbcc91e8f0ef10be7748da4c4a1ba382d17	1d0258c2440a8d19e716292b231e3190	2
6	sharaeva	sharaeva@mail.ru	Шараева Кристина	2019-12-16 02:37:30.772+03	e23f0a6462d0bd064bc24c32e745c7f5b0f62371b7b62af0e66f713b577f4a6b	332de775a36bbfcb140e1caa06299a8a	3
7	brunova	brunova@mail	Брунова Анастасия	2019-12-16 02:38:41.434+03	e23f0a6462d0bd064bc24c32e745c7f5b0f62371b7b62af0e66f713b577f4a6b	332de775a36bbfcb140e1caa06299a8a	3
8	vasilyeva	vika@mail.ru	Васильева Виктория	2019-12-16 02:39:28.342+03	e23f0a6462d0bd064bc24c32e745c7f5b0f62371b7b62af0e66f713b577f4a6b	332de775a36bbfcb140e1caa06299a8a	3
9	vol1	vol1@m	Волкович Мария	2019-09-16 02:46:30.274+03	b9f6da279ec59af95d121d1e1e502ecf056504e267f2f7dfc747ebd0f3f74298	0acf8be1231e24946c3d10b649fb576f	4
10	vol2	vol2@mail	Веневцев Илья	2019-09-16 02:49:09.647+03	b9f6da279ec59af95d121d1e1e502ecf056504e267f2f7dfc747ebd0f3f74298	0acf8be1231e24946c3d10b649fb576f	4
11	vol3	vol3@mail	Салин Сергей	2019-09-16 02:51:24.429+03	b9f6da279ec59af95d121d1e1e502ecf056504e267f2f7dfc747ebd0f3f74298	0acf8be1231e24946c3d10b649fb576f	4
12	vol4	vol4@mail.ru	Помиркованная Вера	2019-09-16 02:53:25.359+03	b9f6da279ec59af95d121d1e1e502ecf056504e267f2f7dfc747ebd0f3f74298	0acf8be1231e24946c3d10b649fb576f	4
13	vol5	vol5@mail.ru	Данилова Анна	2019-09-16 02:55:33.667+03	b9f6da279ec59af95d121d1e1e502ecf056504e267f2f7dfc747ebd0f3f74298	0acf8be1231e24946c3d10b649fb576f	4
\.


--
-- Data for Name: usersvolunteerfunctions; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.usersvolunteerfunctions (uservolunteerfunctionid, userid, volunteerfunctionid, statusid, numberofhours, estimation) FROM stdin;
4	9	21	5	5	5
11	10	2	2	0	0
13	10	21	5	5	5
14	10	18	5	5	5
15	10	17	5	5	5
23	11	17	6	0	0
24	11	19	5	5	5
9	9	5	3	0	0
29	11	5	3	0	0
44	13	5	3	0	0
2	9	1	5	4	5
22	11	4	6	0	0
25	11	18	5	5	5
12	10	3	5	4	5
26	11	21	6	0	0
31	12	4	5	5	5
39	13	1	5	5	5
32	12	21	5	5	5
19	10	8	5	10	5
20	10	9	5	1	5
33	12	19	5	6	4
7	9	6	5	2	4
40	13	21	6	0	0
18	10	6	5	2	5
36	12	6	5	3	5
6	9	22	5	10	5
3	9	17	5	3	5
17	10	23	5	10	5
27	11	22	5	10	5
37	12	22	5	10	5
42	13	22	5	10	5
1	9	14	5	10	5
10	10	14	5	10	5
21	11	14	5	10	5
30	12	14	5	10	5
38	13	16	5	10	5
5	9	11	6	0	0
16	10	11	5	10	5
28	11	11	5	10	5
34	12	11	5	10	5
41	13	13	5	10	5
8	9	10	5	5	5
35	12	10	5	5	5
43	13	10	5	5	5
\.


--
-- Data for Name: uservolunteerfunctionstatus; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.uservolunteerfunctionstatus (statusid, name) FROM stdin;
1	UNCHECKED
2	DENIED
3	APPROVED
4	RECALLED
5	PARTICIPATED
6	ABSENT
\.


--
-- Data for Name: volunteerfunctions; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.volunteerfunctions (volunteerfunctionid, eventid, name, description, requirements, timestart, timefinish, numberneeded) FROM stdin;
1	1	Ассистент мероприятия	Встреча школьников в холле, отметка об их посещении в служебном листе. Навигация до аудитории. Составление теста по теме лекции, с ориентировкой на презентацию 		2019-11-01 15:20:00+03	2019-11-01 17:30:00+03	1
2	1	Ассистент мероприятия	Встреча школьников в холле, отметка об их посещении в служебном листе. Навигация до аудитории. Составление теста по теме лекции, с ориентировкой на презентацию		2019-11-04 16:30:00+03	2019-11-04 19:00:00+03	1
3	1	Ассистент мероприятия	Встреча школьников в холле, отметка об их посещении в служебном листе. Навигация до аудитории. Составление теста по теме лекции, с ориентировкой на презентацию		2019-10-14 16:30:00+03	2019-10-14 19:00:00+03	1
4	1	Ассистент мероприятия	Помощь на закрытии проекта. Навигация до аудитории		2019-11-16 16:30:00+03	2019-11-16 19:00:00+03	2
5	5	Работа с детьми	Помогать детям делать объемные открытки	Усидчивость	2019-12-17 16:00:00+03	2019-12-17 17:30:00+03	10
6	4	Ассистент мероприятия	Проводить лекции по использованию смартфонов 		2019-12-02 10:00:00+03	2019-12-13 16:00:00+03	10
7	4	Координатор волонтеров	Составить график работы волонтеров		2019-12-02 10:00:00+03	2019-12-13 16:00:00+03	1
8	2	Монтаж площадки	Разгрузить из машины стулья и расставить их	парни или крепкие девушки	2019-12-10 12:00:00+03	2019-12-10 21:00:00+03	5
9	2	Демонтаж площадки 	Загрузить в машину стулья с мероприятия	парни или крепкие девушки	2019-12-10 20:00:00+03	2019-12-10 21:00:00+03	5
10	3	Ассистент мероприятия	Помощь в монтаже площадки. По требованию - помощь организаторам		2019-12-08 11:00:00+03	2019-12-08 18:00:00+03	5
11	6	Ассистент мероприятия	Проведение квеста для детей; помощь на трассе; монтаж и демонтаж палаток		2019-10-20 09:00:00+03	2019-10-20 16:00:00+03	16
12	6	Координатор волонтеров	Набор и координация волонтеров на площадке		2019-10-20 09:00:00+03	2019-10-20 16:00:00+03	1
13	6	Медиа	Фотографирование волонтеров и мероприятия	Наличие фотоаппарата	2019-10-20 09:00:00+03	2019-10-20 16:00:00+03	1
14	7	Ассистент мероприятия	Навигация на трассе, регистрация участников, монтаж, демонтаж		2019-10-05 09:00:00+03	2019-10-05 15:00:00+03	8
15	7	Координатор волонтеров	Набор и координация волонтеров на площадке		2019-10-05 09:00:00+03	2019-10-05 15:00:00+03	1
16	7	Фотограф	Фотографирование людей на трассе	Иметь фотоаппарат	2019-10-05 09:00:00+03	2019-10-05 15:00:00+03	1
17	8	Регистрация участников	Отмечание участников в листе регистрации 		2019-10-16 10:00:00+03	2019-10-16 12:00:00+03	3
18	8	Помощь в столовой	Накрывание на стол, контроль прохода в столовую только участников		2019-10-16 14:00:00+03	2019-10-16 15:00:00+03	2
19	8	Навигация участников	Сопровождение участников от отеля до Ломоносова 9		2019-10-16 08:00:00+03	2019-10-20 10:00:00+03	5
20	8	Координатор волонтеров	Набор и координация волонтеров на площадке		2019-10-16 08:00:00+03	2019-10-20 23:00:00+03	1
21	8	Сопровождение на Газпром Арену	Сопровождение гостей с Ломоносова 9 на Газпром Арену		2019-10-20 17:00:00+03	2019-10-20 23:00:00+03	5
22	9	Ассистент мероприятия	Регистрацию участников, навигация на площадке		2019-10-26 12:00:00+03	2019-10-27 19:00:00+03	10
23	9	Монтаж	Монтаж площадки, сборка вольеров	парни или крепкие девушки	2019-10-25 19:00:00+03	2019-10-25 21:00:00+03	5
24	9	Координатор волонтеров	Набор и координация волонтеров на площадке		2019-10-25 19:00:00+03	2019-10-27 19:00:00+03	1
\.


--
-- Name: events_eventid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.events_eventid_seq', 10, true);


--
-- Name: eventstatus_statusid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.eventstatus_statusid_seq', 5, true);


--
-- Name: userrole_roleid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.userrole_roleid_seq', 5, true);


--
-- Name: users_userid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.users_userid_seq', 13, true);


--
-- Name: usersvolunteerfunctions_uservolunteerfunctionid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.usersvolunteerfunctions_uservolunteerfunctionid_seq', 44, true);


--
-- Name: uservolunteerfunctionstatus_statusid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.uservolunteerfunctionstatus_statusid_seq', 6, true);


--
-- Name: volunteerfunctions_volunteerfunctionid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.volunteerfunctions_volunteerfunctionid_seq', 24, true);


--
-- Name: events events_pkey; Type: CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (eventid);


--
-- Name: eventstatus eventstatus_pkey; Type: CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.eventstatus
    ADD CONSTRAINT eventstatus_pkey PRIMARY KEY (statusid);


--
-- Name: userrole userrole_pkey; Type: CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.userrole
    ADD CONSTRAINT userrole_pkey PRIMARY KEY (roleid);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_login_key; Type: CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.users
    ADD CONSTRAINT users_login_key UNIQUE (login);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (userid);


--
-- Name: usersvolunteerfunctions usersvolunteerfunctions_pkey; Type: CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.usersvolunteerfunctions
    ADD CONSTRAINT usersvolunteerfunctions_pkey PRIMARY KEY (uservolunteerfunctionid);


--
-- Name: uservolunteerfunctionstatus uservolunteerfunctionstatus_pkey; Type: CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.uservolunteerfunctionstatus
    ADD CONSTRAINT uservolunteerfunctionstatus_pkey PRIMARY KEY (statusid);


--
-- Name: volunteerfunctions volunteerfunctions_pkey; Type: CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.volunteerfunctions
    ADD CONSTRAINT volunteerfunctions_pkey PRIMARY KEY (volunteerfunctionid);


--
-- Name: events events_coordinatorid_fkey; Type: FK CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.events
    ADD CONSTRAINT events_coordinatorid_fkey FOREIGN KEY (coordinatorid) REFERENCES volunteersservice.users(userid);


--
-- Name: events events_organiserid_fkey; Type: FK CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.events
    ADD CONSTRAINT events_organiserid_fkey FOREIGN KEY (organiserid) REFERENCES volunteersservice.users(userid);


--
-- Name: events events_statusid_fkey; Type: FK CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.events
    ADD CONSTRAINT events_statusid_fkey FOREIGN KEY (statusid) REFERENCES volunteersservice.eventstatus(statusid);


--
-- Name: users users_roleid_fkey; Type: FK CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.users
    ADD CONSTRAINT users_roleid_fkey FOREIGN KEY (roleid) REFERENCES volunteersservice.userrole(roleid);


--
-- Name: usersvolunteerfunctions usersvolunteerfunctions_statusid_fkey; Type: FK CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.usersvolunteerfunctions
    ADD CONSTRAINT usersvolunteerfunctions_statusid_fkey FOREIGN KEY (statusid) REFERENCES volunteersservice.uservolunteerfunctionstatus(statusid);


--
-- Name: usersvolunteerfunctions usersvolunteerfunctions_userid_fkey; Type: FK CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.usersvolunteerfunctions
    ADD CONSTRAINT usersvolunteerfunctions_userid_fkey FOREIGN KEY (userid) REFERENCES volunteersservice.users(userid) ON DELETE SET DEFAULT;


--
-- Name: usersvolunteerfunctions usersvolunteerfunctions_volunteerfunctionid_fkey; Type: FK CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.usersvolunteerfunctions
    ADD CONSTRAINT usersvolunteerfunctions_volunteerfunctionid_fkey FOREIGN KEY (volunteerfunctionid) REFERENCES volunteersservice.volunteerfunctions(volunteerfunctionid) ON DELETE CASCADE;


--
-- Name: volunteerfunctions volunteerfunctions_eventid_fkey; Type: FK CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.volunteerfunctions
    ADD CONSTRAINT volunteerfunctions_eventid_fkey FOREIGN KEY (eventid) REFERENCES volunteersservice.events(eventid) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

END TRANSACTION;
