# VolunteersService

This is a web-application which improves the work of volunteers managers, it provides interface for organisers, managers, volunteers and coordinators.

--------

## Build and launch

### For launch you will need

* [Gradle](https://gradle.org/install/) ( >= 4.4 )
* [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/2133151) ( >= 1.8 )
* [PostgreSQL](https://www.postgresql.org/download/) running on localhost:5432

### Building

1. Download and install programs listed above
2. Clone this project to your computer
3. Launch ```psql -U postgres -f init_postgres.sql``` (maybe ```sudo -u postgres psql -f ./init_postgres.sql``` at linux) and from project root, then enter your password and check that transaction has passed
4. Launch ```gradle build``` to build or ```gradle run``` to build and run
5. Main page will be located at localhost:8080/
