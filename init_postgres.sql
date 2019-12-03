BEGIN TRANSACTION;

\! echo Deleting java role and schema

DROP SCHEMA IF EXISTS VolunteersService CASCADE;

\! echo Creating role and schema


CREATE SCHEMA VolunteersService AUTHORIZATION java;

\! echo Creating tables

CREATE TABLE VolunteersService.EventStatus (
    StatusID     SERIAL    PRIMARY KEY  NOT NULL,
    Name         VARCHAR(30)            NOT NULL
);

CREATE TABLE VolunteersService.UserRole (
    RoleID       SERIAL    PRIMARY KEY  NOT NULL,
    Name         VARCHAR(30)            NOT NULL
);

CREATE TABLE VolunteersService.UserVolunteerFunctionStatus (
    StatusID     SERIAL    PRIMARY KEY  NOT NULL,
    Name         VARCHAR(30)            NOT NULL
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
    EventID       SERIAL    PRIMARY KEY  NOT NULL,
    OrganiserID   INTEGER                NOT NULL REFERENCES VolunteersService.Users(UserID),
    CoordinatorID INTEGER                         REFERENCES VolunteersService.Users(UserID),
    Name          VARCHAR(50)            NOT NULL,
    Description   VARCHAR(300)           NOT NULL,
    Place         VARCHAR(120)           NOT NULL,
    DateStart     TIMESTAMPTZ            NOT NULL,
    DateFinish    TIMESTAMPTZ            NOT NULL,
    StatusID      INTEGER                NOT NULL REFERENCES VolunteersService.EventStatus(StatusID),
    Message       VARCHAR(200)           NOT NULL DEFAULT ''
);

CREATE TABLE VolunteersService.VolunteerFunctions (
    VolunteerFunctionID       SERIAL    PRIMARY KEY  NOT NULL,
    EventID      INTEGER                NOT NULL REFERENCES VolunteersService.Events(EventID) ON DELETE CASCADE,
    Name         VARCHAR(50)            NOT NULL,
    Description  VARCHAR(200)           NOT NULL,
    Requirements VARCHAR(200)           NOT NULL DEFAULT '',
    TimeStart    TIMESTAMPTZ            NOT NULL,
    TimeFinish   TIMESTAMPTZ            NOT NULL,
    NumberNeeded INTEGER                NOT NULL
);

CREATE TABLE VolunteersService.UsersVolunteerFunctions (
    UserVolunteerFunctionID   SERIAL    PRIMARY KEY  NOT NULL,
    UserID                    INTEGER NOT NULL REFERENCES VolunteersService.Users(UserID)                           ON DELETE SET DEFAULT,
    VolunteerFunctionID       INTEGER NOT NULL REFERENCES VolunteersService.VolunteerFunctions(VolunteerFunctionID) ON DELETE CASCADE,
    StatusID                  INTEGER NOT NULL REFERENCES VolunteersService.UserVolunteerFunctionStatus(StatusID),
    NumberOfHours             INTEGER NOT NULL DEFAULT 0,
    Estimation                INTEGER NOT NULL DEFAULT 0
);

ALTER TABLE VolunteersService.EventStatus                 OWNER TO java;
ALTER TABLE VolunteersService.UserRole                    OWNER TO java;
ALTER TABLE VolunteersService.UserVolunteerFunctionStatus OWNER TO java;
ALTER TABLE VolunteersService.Users                       OWNER TO java;
ALTER TABLE VolunteersService.Events                      OWNER TO java;
ALTER TABLE VolunteersService.VolunteerFunctions          OWNER TO java;
ALTER TABLE VolunteersService.UsersVolunteerFunctions     OWNER TO java;

\! echo Inserting data

INSERT INTO VolunteersService.UserRole                    (Name) values ('ORGANISER'), ('MANAGER'), ('COORDINATOR'), ('VOLUNTEER'), ('ADMIN');
INSERT INTO VolunteersService.EventStatus                 (Name) values ('UNCHECKED'), ('APPROVED'), ('COORDINATED'), ('PUBLISHED'), ('REJECTED');
INSERT INTO VolunteersService.UserVolunteerFunctionStatus (Name) values ('UNCHECKED'), ('DENIED'), ('APPROVED'), ('RECALLED'), ('PARTICIPATED'), ('ABSENT');

END TRANSACTION;
