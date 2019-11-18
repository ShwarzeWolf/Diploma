--
-- PostgreSQL database dump
--

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
-- Name: volunteerfunctionstatus; Type: TABLE; Schema: volunteersservice; Owner: java
--

CREATE TABLE volunteersservice.volunteerfunctionstatus (
    statusid integer NOT NULL,
    name character varying(30) NOT NULL
);


ALTER TABLE volunteersservice.volunteerfunctionstatus OWNER TO java;

--
-- Name: volunteerfunctionstatus_statusid_seq; Type: SEQUENCE; Schema: volunteersservice; Owner: java
--

CREATE SEQUENCE volunteersservice.volunteerfunctionstatus_statusid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE volunteersservice.volunteerfunctionstatus_statusid_seq OWNER TO java;

--
-- Name: volunteerfunctionstatus_statusid_seq; Type: SEQUENCE OWNED BY; Schema: volunteersservice; Owner: java
--

ALTER SEQUENCE volunteersservice.volunteerfunctionstatus_statusid_seq OWNED BY volunteersservice.volunteerfunctionstatus.statusid;


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
-- Name: volunteerfunctions volunteerfunctionid; Type: DEFAULT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.volunteerfunctions ALTER COLUMN volunteerfunctionid SET DEFAULT nextval('volunteersservice.volunteerfunctions_volunteerfunctionid_seq'::regclass);


--
-- Name: volunteerfunctionstatus statusid; Type: DEFAULT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.volunteerfunctionstatus ALTER COLUMN statusid SET DEFAULT nextval('volunteersservice.volunteerfunctionstatus_statusid_seq'::regclass);


--
-- Data for Name: events; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.events (eventid, organiserid, name, description, place, datestart, datefinish, statusid) FROM stdin;
1	2	Christmas 2020	Christmas celebration on North Pole, we've got a lot of things to do	Noth pole	2020-01-01 10:00:00+03	2020-01-07 19:00:00+03	1
2	4	International women's day 2020	A lot of people come to celebrate internatinal women's day, but it's women's party, so we need men volunteers	Saint-Petersburg, Nevskiy prospect	2020-03-08 10:00:00+03	2020-03-08 22:20:00+03	1
\.


--
-- Data for Name: eventstatus; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.eventstatus (statusid, name) FROM stdin;
1	unchecked
2	approved
3	coordinated
4	published
5	expired
\.


--
-- Data for Name: userrole; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.userrole (roleid, name) FROM stdin;
1	organiser
2	manager
3	coordinator
4	volunteer
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.users (userid, login, email, name, registerdate, passwdhash1, passwdhash2, roleid) FROM stdin;
1	qwe	qwe@qwe	Qwe Q We	2019-11-18 17:46:32.587+03	489cd5dbc708c7e541de4d7cd91ce6d0f1613573b7fc5b40d3942ccb9555cf35	76d80224611fc919a5d54f0ff9fba446	4
2	asd	asd@asd	Asd As D	2019-11-18 17:46:43.193+03	688787d8ff144c502c7f5cffaafe2cc588d86079f9de88304c26b0cb99ce91c6	7815696ecbf1c96e6894b779456d330e	1
3	wer	wer@wer	Wer W Er	2019-11-18 20:18:47.444+03	e797c0013811a1d1e35ad7edd10fb99986db664b0996c76ed9ae5e0a5151bbf9	22c276a05aa7c90566ae2175bcc2a9b0	4
4	sdf	sdf@sdf	Sdf Sd F	2019-11-18 20:19:20.341+03	18ee24150dcb1d96752a4d6dd0f20dfd8ba8c38527e40aa8509b7adecf78f9c6	d9729feb74992cc3482b350163a1a010	1
\.


--
-- Data for Name: usersvolunteerfunctions; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.usersvolunteerfunctions (uservolunteerfunctionid, userid, volunteerfunctionid, statusid) FROM stdin;
1	1	1	1
3	3	1	1
4	1	2	1
\.


--
-- Data for Name: volunteerfunctions; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.volunteerfunctions (volunteerfunctionid, eventid, name, description, requirements, timestart, timefinish, numberneeded) FROM stdin;
1	1	Santa	Fly around the world and give presents to children	Needs to be very fast	2020-01-01 10:00:00+03	2020-01-01 10:00:00+03	1
2	2	Hello-men	Those men need to say hello to the women coming to the party	Speakable	2020-03-08 10:00:00+03	2020-03-08 10:00:00+03	2
3	2	Goodbye-men	Those men need to say goodbye to the women coming to the party	Speakable	2020-03-08 21:50:00+03	2020-03-08 21:50:00+03	2
4	2	Entertainer	Need to intertain wemen on their day	Funny	2020-03-08 11:00:00+03	2020-03-08 11:00:00+03	6
\.


--
-- Data for Name: volunteerfunctionstatus; Type: TABLE DATA; Schema: volunteersservice; Owner: java
--

COPY volunteersservice.volunteerfunctionstatus (statusid, name) FROM stdin;
1	unchecked
2	denied
3	approved
4	participated
5	partly
6	absent
\.


--
-- Name: events_eventid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.events_eventid_seq', 2, true);


--
-- Name: eventstatus_statusid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.eventstatus_statusid_seq', 5, true);


--
-- Name: userrole_roleid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.userrole_roleid_seq', 4, true);


--
-- Name: users_userid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.users_userid_seq', 4, true);


--
-- Name: usersvolunteerfunctions_uservolunteerfunctionid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.usersvolunteerfunctions_uservolunteerfunctionid_seq', 4, true);


--
-- Name: volunteerfunctions_volunteerfunctionid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.volunteerfunctions_volunteerfunctionid_seq', 4, true);


--
-- Name: volunteerfunctionstatus_statusid_seq; Type: SEQUENCE SET; Schema: volunteersservice; Owner: java
--

SELECT pg_catalog.setval('volunteersservice.volunteerfunctionstatus_statusid_seq', 6, true);


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
-- Name: volunteerfunctions volunteerfunctions_pkey; Type: CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.volunteerfunctions
    ADD CONSTRAINT volunteerfunctions_pkey PRIMARY KEY (volunteerfunctionid);


--
-- Name: volunteerfunctionstatus volunteerfunctionstatus_pkey; Type: CONSTRAINT; Schema: volunteersservice; Owner: java
--

ALTER TABLE ONLY volunteersservice.volunteerfunctionstatus
    ADD CONSTRAINT volunteerfunctionstatus_pkey PRIMARY KEY (statusid);


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
    ADD CONSTRAINT usersvolunteerfunctions_statusid_fkey FOREIGN KEY (statusid) REFERENCES volunteersservice.volunteerfunctionstatus(statusid);


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

