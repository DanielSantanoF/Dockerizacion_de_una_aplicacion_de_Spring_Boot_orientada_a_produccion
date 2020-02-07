# Dockerizacion_de_una_aplicacion_de_Spring_Boot_orientada_a_produccion
Repositorio sobre como dockerizar una aplicación de spring boot orientada a producción
Guia para la asignuatura:
* Sistemas de Gestión Empresarial

Proyecto para dockerizar de Spring Tool Suite con base de datos Postgresql


***

## Dockerizar una app de STS con Postgresql
Para ello deberemos de disponer de una base de datos postgresql que en este caso haremos con Docker primero haciendo pull de la imagen y más tarde creando el contenedor de postgresql para poder tenerlo disponible para nuestra aplicación de STS

```
docker pull postgres

docker run --name postgresql1 -p 5432:5432 -e POSTGRES_PASSWORD=postgresql1 -d postgres
```

Una vez lista la base de datos procederemos a desplegar la aplicación de STS

Indicamos que la base de datos es postgresql en el .properties del proyecto con las siguientes variables de configuración
```
# Postresql
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgresql1
spring.datasource.initialization-mode=always
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

Tras esto realizaremos un Maven Install sobre el proyecto de STS tras esto en la carpeta que se ha generado /target entraremos y realizaremos los siguientes comandos para separar las dependencias del codigo
```
mkdir dependency
jar -xf ../*.jar
```

Una vez realizado generaremos el archivo Dockerfile
```Dockerfile
FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","ProyectoRepasoApplication"]
```

Una vez que hemos realizado todos los pasos creamos la imagen y contenedor de nuestra app de Docker teniendo el contenedor de postgresql funcional
```
docker build -t <username>/<appname> .
docker run --name <containername> -p 9000:9000 -d <username>/<appname>
```

Una vez creado iremos al puerto indicado al crear el contenedor en este caso el 9000 y revisaremos que nuestra aplicación este funcionando