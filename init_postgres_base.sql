BEGIN TRANSACTION;
DROP SCHEMA IF EXISTS VolunteersService CASCADE;

\! echo "Creating role and schema"

CREATE SCHEMA VolunteersService AUTHORIZATION java;

\! echo "Creating tables"

CREATE TABLE VolunteersService.UserRole (
    RoleID       SERIAL    PRIMARY KEY  NOT NULL,
    Name         VARCHAR(40)            NOT NULL
);

CREATE TABLE VolunteersService.Users (
    UserID       SERIAL    PRIMARY KEY  NOT NULL,
    Login        VARCHAR(20)  UNIQUE    NOT NULL,
    Email        VARCHAR(40)  UNIQUE    NOT NULL,
    Name         VARCHAR(30)            NOT NULL,
    Surname      VARCHAR(30)            NOT NULL,
    RegisterDate TIMESTAMPTZ            NOT NULL,
    PasswdHash1  VARCHAR(128)           NOT NULL,
    PasswdHash2  VARCHAR(128)           NOT NULL,
    RoleID       INTEGER                NOT NULL REFERENCES VolunteersService.UserRole(RoleID)
);

CREATE TABLE VolunteersService.EventStatus (
    StatusID     SERIAL    PRIMARY KEY  NOT NULL,
    Name         VARCHAR(30)            NOT NULL
);

CREATE TABLE VolunteersService.Events (
    EventID       SERIAL    PRIMARY KEY  NOT NULL,
    OrganiserID   INTEGER                NOT NULL REFERENCES VolunteersService.Users(UserID),
    CoordinatorID INTEGER                         REFERENCES VolunteersService.Users(UserID),
    Name          VARCHAR(150)           NOT NULL,
    Description   TEXT                   NOT NULL,
    Place         VARCHAR(300)           NOT NULL,
    DateStart     TIMESTAMPTZ            NOT NULL,
    DateFinish    TIMESTAMPTZ            NOT NULL,
    Requirements  VARCHAR(300)           NOT NULL DEFAULT '',
    ClothesType   VARCHAR(300)           NOT NULL DEFAULT '',
    Accommodation VARCHAR(300)           NOT NULL DEFAULT '',
    Food          VARCHAR(300)           NOT NULL DEFAULT '',
    StatusID      INTEGER                NOT NULL REFERENCES VolunteersService.EventStatus(StatusID),
    Message       VARCHAR(100)           NOT NULL DEFAULT ''
);


CREATE TABLE VolunteersService.VolunteerFunctions (
    VolunteerFunctionID       SERIAL    PRIMARY KEY  NOT NULL,
    EventID      INTEGER                NOT NULL REFERENCES VolunteersService.Events(EventID) ON DELETE CASCADE,
    Name         VARCHAR(100)           NOT NULL,
    Description  VARCHAR(500)           NOT NULL,
    Requirements VARCHAR(200)           NOT NULL DEFAULT '',
    TimeStart    TIMESTAMPTZ            NOT NULL,
    TimeFinish   TIMESTAMPTZ            NOT NULL,
    NumberNeeded INTEGER                NOT NULL
);

ALTER TABLE VolunteersService.EventStatus        OWNER TO java;
ALTER TABLE VolunteersService.UserRole           OWNER TO java;
ALTER TABLE VolunteersService.Users              OWNER TO java;
ALTER TABLE VolunteersService.Events             OWNER TO java;
ALTER TABLE VolunteersService.VolunteerFunctions OWNER TO java;

\! echo "Inserting base data"

INSERT INTO VolunteersService.UserRole    (Name) values ('ORGANISER'), ('MANAGER') , ('COORDINATOR'), ('ADMIN'), ('MOVEMENTLEADER');
INSERT INTO VolunteersService.EventStatus (Name) values ('CREATED'), ('UNCHECKED'), ('APPROVED'), ('DENIED'), ('ASSIGNED'), ('FINISHED');

END TRANSACTION;
