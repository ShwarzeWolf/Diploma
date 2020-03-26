# VolunteersService

This is a web-application which improves the work of volunteers coordinators, it provides interface for organisers, manager(s) and coordinators.

--------

## Build and launch

### For launch you will need

* [Maven](http://maven.apache.org/download.cgi)
* [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/2133151) ( >= 1.8 )
* [PostgreSQL](https://www.postgresql.org/download/) running on localhost:5432

### Building and running

1. Download and install programs listed above
2. Clone this project to your computer
3. Launch ```psql -U postgres -f init_postgres_base.sql``` (```sudo -u postgres psql -f ./init_postgres_base.sql``` on linux) and from project root, then enter your password and check that transaction has passed
4. Launch ```mvn package``` to build. Or you can use batch script files: ```build.bat```, ```run.bat``` and ```build_run.bat``` to build and run under Windows
5. Main page will be located at localhost:8080/

Note that launching command in ```3.``` will create (deleting the previous if there were one) user 'java' with password '123456', and it will recreate schema VolunteersService.

Also this command will need read permission on Linux for user postgres. You can copy .sql file to /tmp and read it from there.

If you need to specify a port number, you need to do this before compication, at file src/main/resources/application.properties with ```server.port=<port_number>```  
And you also can change database connection properties, host and port for example. The properties file is ```src/main/resources/hibernate.cfg.xml```

### Usage

You can register as organiser right from the beginning, but there is no point until you have manager and organiser.  
The right way to register them is registration by administrator (admin role).
And the proper way to add admin now: you need to register it as organiser, and then change its ```RoleID``` in database to the one
which is used for role ADMIN in ```VolunteersService.UserRoles```. Technically, you can live without an ADMIN role, and you can create manager and
organiser in the same way.

### Sample data

There is another .sql file _init\_postgres\_additional.sql_ containig a few users accounts (login : password):

* Administrator (admin : admin)
* Manager (manager : manager)
* Coordinators:
  * (coord1 : coord1)
  * (coord2 : coord2)
* Organisers:
  * (org1 : org1)
  * (org2 : org2)

  You can use it the same way as base file, right after it.
  