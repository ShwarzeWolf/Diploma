--
-- PostgreSQL database dump
--

-- Dumped from database version 12.0
-- Dumped by pg_dump version 12.0

BEGIN TRANSACTION;

SET statement_timeout = 0;
SET lock_timeout = 0;
-- SET idle_in_transaction_session_timeout = 0;
-- SET client_encoding = 'UTF-8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: volunteersservice; Type: SCHEMA; Schema: -; Owner: java
--

DROP SCHEMA IF EXISTS volunteersservice CASCADE;
DROP ROLE IF EXISTS java;

CREATE ROLE java WITH LOGIN PASSWORD '123654';

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
    name character varying(50) NOT NULL,
    description character varying(300) NOT NULL,
    place character varying(120) NOT NULL,
    datestart timestamp with time zone NOT NULL,
    datefinish timestamp with time zone NOT NULL,
    statusid integer NOT NULL
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
    name character varying(40) NOT NULL,
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
    statusid integer NOT NULL
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
    name character varying(50) NOT NULL,
    description character varying(200) NOT NULL,
    requirements character varying(200),
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

COPY volunteersservice.events (eventid, organiserid, name, description, place, datestart, datefinish, statusid) FROM stdin;
1	3	Christmas 2020	Christmas celebration event. This will be great really	North Pole, Santa's place	2020-01-01 00:00:00+03	2020-01-06 22:00:00+03	1
2	4	International women's day 2020	8th of March is a holiday for women, so we need only male volunteers to help with celebration preparations.	Russia, Saint-Petersburg, Nevskiy Prospect, building #16	2020-03-08 12:00:00+03	2020-03-08 22:30:00+03	1
\.


--
-- Data for Name: eventstatus; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.eventstatus (statusid, name) FROM stdin;
1	UNCHECKED
2	APPROVED
3	COORDINATED
4	PUBLISHED
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
1	qwe	qwe@qwe	Qwe We	2019-11-19 20:38:24.021+03	489cd5dbc708c7e541de4d7cd91ce6d0f1613573b7fc5b40d3942ccb9555cf35	76d80224611fc919a5d54f0ff9fba446	4
2	wer	wer@wer	Wer Er	2019-11-19 20:39:04.808+03	e797c0013811a1d1e35ad7edd10fb99986db664b0996c76ed9ae5e0a5151bbf9	22c276a05aa7c90566ae2175bcc2a9b0	4
3	asd	asd@asd	Asd Sd	2019-11-19 20:39:17.445+03	688787d8ff144c502c7f5cffaafe2cc588d86079f9de88304c26b0cb99ce91c6	7815696ecbf1c96e6894b779456d330e	1
4	sdf	sdf@sdf	Sdf Df	2019-11-19 20:39:34.382+03	18ee24150dcb1d96752a4d6dd0f20dfd8ba8c38527e40aa8509b7adecf78f9c6	d9729feb74992cc3482b350163a1a010	1
5	admin	admin@admin	Administrator	2019-11-19 20:39:49.21+03	8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918	21232f297a57a5a743894a0e4a801fc3	5
6	manager	manager@manager	Manager	2019-11-19 22:56:02.588+03	6ee4a469cd4e91053847f5d3fcb61dbcc91e8f0ef10be7748da4c4a1ba382d17	1d0258c2440a8d19e716292b231e3190	2
7	coordinator	coordinator@coordinator	Coordinator	2019-11-19 22:56:42.233+03	bf24385098410391a81d92b2de72d3a2946d24f42ee387e51004a868281a2408	c4312c2a07bf7ded608a4d7cee2228dd	3
\.


--
-- Data for Name: usersvolunteerfunctions; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.usersvolunteerfunctions (uservolunteerfunctionid, userid, volunteerfunctionid, statusid) FROM stdin;
1	1	1	1
2	2	1	1
3	2	4	1
4	2	5	1
\.


--
-- Data for Name: uservolunteerfunctionstatus; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.uservolunteerfunctionstatus (statusid, name) FROM stdin;
1	UNCHECKED
2	DENIED
3	APPRIVED
4	PARTICIPATED
5	PARTICIPATED
6	ABSENT
\.


--
-- Data for Name: volunteerfunctions; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.volunteerfunctions (volunteerfunctionid, eventid, name, description, requirements, timestart, timefinish, numberneeded) FROM stdin;
2	1	Elf, first day	Elf manufactures presents to be given to children	Accurate hand-work is needed	2020-01-01 00:00:00+03	2020-01-01 18:00:00+03	8
3	1	Elf, second day	Elf manufactures presents to be given to children	Accurate hand-work is needed	2020-01-02 00:00:00+03	2020-01-02 18:00:00+03	8
1	1	Santa	Santa flies around the world and give presents to children.	Needs to be very fast	2020-01-01 00:00:00+03	2020-01-02 02:00:00+03	1
4	2	Greetings-man	Says hello to women when they come	Needs to speak	2020-03-08 12:00:00+03	2020-03-08 13:00:00+03	2
5	2	Bye-man	Says goodbye to women when they leave	Needs to speak	2020-03-08 21:30:00+03	2020-03-08 22:30:00+03	2
6	2	Main entertainer	Entertain women, because it's their time to party	No anti-femenism jokes, man needs to be funny	2020-03-08 14:00:00+03	2020-03-08 19:00:00+03	1
\.


--
-- Name: events_eventid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.events_eventid_seq', 2, true);


--
-- Name: eventstatus_statusid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.eventstatus_statusid_seq', 4, true);


--
-- Name: userrole_roleid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.userrole_roleid_seq', 5, true);


--
-- Name: users_userid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.users_userid_seq', 7, true);


--
-- Name: usersvolunteerfunctions_uservolunteerfunctionid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.usersvolunteerfunctions_uservolunteerfunctionid_seq', 4, true);


--
-- Name: uservolunteerfunctionstatus_statusid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.uservolunteerfunctionstatus_statusid_seq', 6, true);


--
-- Name: volunteerfunctions_volunteerfunctionid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.volunteerfunctions_volunteerfunctionid_seq', 6, true);


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