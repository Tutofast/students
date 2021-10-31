# TutoFast "Students" Micro-service

__Recommended Version Manager: [asdf](https://asdf-vm.com/guide/getting-started.html#_1-install-dependencies)__
## Dependencies
* PostgresSQL 12.8
* open-JDK 17
* Maven 3.8.2
* [Axon Server](https://axoniq.io/download)  4.5.8 

### Notes
* This projects works with ENV variables to set up the development environment:

*Previously you need to create the database manually*

```sh
ENVIRONMENT=dev
POSTGRES_DATABASE_URI=jdbc:postgresql://localhost:5432/{database_name}
POSTGRES_DATABASE_USERNAME={your_username}
POSTGRES_DATABASE_PASSWORD={your_password}
```