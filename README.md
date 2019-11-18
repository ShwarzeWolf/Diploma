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

### Sample data

You can initialize service with sample data, in that case you do not need to launch ```init_postgres.sql``` script, but ```init_with_data.sql``` with the same command.  
For now, there are two volunteers (Username:Password are qwe:qwe and wer:wer) and two organisers (asd:asd and sdf:sdf).
