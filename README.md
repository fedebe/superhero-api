# Superhero API

Superhero API permite realizar operaciones CRUD sobre superheroes.

* El microservicio esta dockerizado, para correr el mismo puede realizarlo ejecutando los siguientes comandos:

    * `docker build -t superhero-api .`
	
    * `docker run -d -p 8080:8080 superhero-api`

* Puede revisar la documentacion del API desde `http://localhost:8080/documentation`

* Utiliza una base de datos H2 en memoria junto con Liquidbase para administrar las modificaciones que puedan surgir en la misma.

