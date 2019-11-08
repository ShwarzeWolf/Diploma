BEGIN TRANSACTION;

\! echo Creating role and schema

CREATE ROLE java WITH LOGIN PASSWORD '123654';

CREATE SCHEMA VolunteersService AUTHORIZATION java;

\! echo Creating tables

CREATE TABLE VolunteersService.EventStatus (
    StatusID     SERIAL    PRIMARY KEY  NOT NULL,
    Name         VARCHAR(20)            NOT NULL
);

CREATE TABLE VolunteersService.UserRole (
    RoleID       SERIAL    PRIMARY KEY  NOT NULL,
    Name         VARCHAR(20)            NOT NULL
);

CREATE TABLE VolunteersService.VolunteerFunctionStatus (
    StatusID     SERIAL    PRIMARY KEY  NOT NULL,
    Name         VARCHAR(20)            NOT NULL
);

CREATE TABLE VolunteersService.Users (
    UserID       SERIAL    PRIMARY KEY  NOT NULL,
    Login        VARCHAR(20)  UNIQUE    NOT NULL,
    Email        VARCHAR(40)  UNIQUE    NOT NULL,
    Name         VARCHAR(40)            NOT NULL,
    RegisterDate TIMESTAMPTZ            NOT NULL,
    PasswdHash1  VARCHAR(128)           NOT NULL,
    PasswdHash2  VARCHAR(128)           NOT NULL,
    RoleID       INTEGER                NOT NULL REFERENCES VolunteersService.UserRole(RoleID)
);

CREATE TABLE VolunteersService.Events (
    EventID      SERIAL    PRIMARY KEY  NOT NULL,
    Name         VARCHAR(40)            NOT NULL,
    Description  VARCHAR(300)           NOT NULL,
    DateStart    TIMESTAMPTZ            NOT NULL,
    DateFinish   TIMESTAMPTZ            NOT NULL,
    StatusID     INTEGER                NOT NULL REFERENCES VolunteersService.EventStatus(StatusID)
);

CREATE TABLE VolunteersService.VolunteerFunctions (
    VolunteerFunctionID       SERIAL    PRIMARY KEY  NOT NULL,
    EventID      INTEGER                NOT NULL REFERENCES VolunteersService.Events(EventID) ON DELETE CASCADE,
    Name         VARCHAR(20)            NOT NULL,
    Description  VARCHAR(200)           NOT NULL,
    Requirements VARCHAR(200)           NOT NULL,
    TimeStart    TIMESTAMPTZ            NOT NULL,
    TimeFinish   TIMESTAMPTZ            NOT NULL,
    NumberNeeded INTEGER                NOT NULL
);

CREATE TABLE VolunteersService.UsersVolunteerFunctions (
    UserVolunteerFunctionID   SERIAL    PRIMARY KEY  NOT NULL,
    UserID                    INTEGER NOT NULL REFERENCES VolunteersService.Users(UserID)                           ON DELETE SET DEFAULT,
    VolunteerFunctionID       INTEGER NOT NULL REFERENCES VolunteersService.VolunteerFunctions(VolunteerFunctionID) ON DELETE CASCADE,
    StatusID                  INTEGER NOT NULL REFERENCES VolunteersService.VolunteerFunctionStatus(StatusID)
);

ALTER TABLE VolunteersService.EventStatus              OWNER TO java;
ALTER TABLE VolunteersService.UserRole                 OWNER TO java;
ALTER TABLE VolunteersService.VolunteerFunctionStatus  OWNER TO java;
ALTER TABLE VolunteersService.Users                    OWNER TO java;
ALTER TABLE VolunteersService.Events                   OWNER TO java;
ALTER TABLE VolunteersService.VolunteerFunctions       OWNER TO java;
ALTER TABLE VolunteersService.UsersVolunteerFunctions  OWNER TO java;

\! echo Inserting data

INSERT INTO VolunteersService.UserRole                (Name) values ('organiser'), ('manager'), ('coordinator'), ('volunteer');
INSERT INTO VolunteersService.EventStatus             (Name) values ('unchecked'), ('approved'), ('coordinated'), ('published'), ('expired');
INSERT INTO VolunteersService.VolunteerFunctionStatus (Name) values ('unchecked'), ('denied'), ('approved'), ('participated'), ('partly'), ('absent');

END TRANSACTION;