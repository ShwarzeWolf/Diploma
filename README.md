# VolunteersService

This is a web-application which improves the work of volunteers managers, it provides interface for organisers, managers, volunteers and coordinators.

--------

## Build and launch

### For launch you will need

* [Maven](http://maven.apache.org/download.cgi)
* [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/2133151) ( >= 1.8 )
* [PostgreSQL](https://www.postgresql.org/download/) running on localhost:5432

### Building

1. Download and install programs listed above
2. Clone this project to your computer
3. Launch ```psql -U postgres -f init_postgres.sql``` (maybe ```sudo -U postgres psql -f ./init_postgres.sql``` at linux) and from project root, then enter your password and check that transaction has passed
4. Launch ```mvn package``` to build. Or you can use batch script files: ```build.bat```, ```run.bat``` and ```build_run.bat``` to build and run
5. Main page will be located at localhost:8080/

### Usage

You can create volunteers and organisers right from the beginning, but there is no point until you have manager and organiser.  
The right way to register them is registration by admin.
And the proper way to add admin now: you need to register it as volunteer/organiser, and then change its ```RoleID``` in database to the one
which is used for role ADMIN in ```VolunteersService.UserRoles```. Technically, you can live without an ADMIN role, and you can create manager and
organiser in the same way.

### Sample data

You can initialize service with sample data, in that case you do not need to launch ```init_postgres.sql``` script, but ```init_with_data.sql``` with the same command.
Beware of losing your data as this script will drop schema on start and recreate it with sample data.  
For now, there are two volunteers (Username:Password are qwe:qwe and wer:wer), two organisers (asd:asd and sdf:sdf), coordinator (coordinator:coordinator),
manager (manager:manager) and administrator (admin:admin).  
