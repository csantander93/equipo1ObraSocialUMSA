# Proyecto Backend - Sistema Integral de Obra Social para AlMedin
<p>
  <h1 ><b>EQUIPO 1</b></h1>
</p>


<a href="https://github.com/csantander93/equipo1ObraSocialUMSA/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=csantander93/equipo1ObraSocialUMSA" />
</a>
<a href="https://github.com/csantander93/equipo1ObraSocialUMSA/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=celinalopez/Egg" />
</a>
<a href="https://github.com/csantander93/equipo1ObraSocialUMSA/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=juliancolman/recetas" />
</a>


## Descripción del Proyecto

La primera parte de este proyecto se enfoca en el desarrollo del backend de una aplicación para la empresa AlMedin. El objetivo es crear una API RESTful básica con funcionalidades clave para mejorar el proceso de pedidos de turnos, vista de la cartilla de especialistas y descarga de recetas médicas.

## Acciones

La aplicación permitirá las siguientes operaciones:

### 1. Crear turno médico
- **Método HTTP**: POST
- **Ruta de la API**: `/turnos`
- **Descripción**: Este endpoint permitirá a los usuarios crear un nuevo turno médico proporcionando los siguientes datos en el cuerpo de la solicitud:
  - Nombre del paciente
  - Fecha y hora de la cita
  - ID del médico especialista
  - Motivo de la consulta

### 2. Consultar cartilla de especialistas
- **Método HTTP**: GET
- **Ruta de la API**: `/especialistas`
- **Descripción**: Este endpoint devolverá una lista de médicos especialistas disponibles. Cada elemento de la lista contendrá la siguiente información:
  - Nombre del médico
  - Especialidad médica
  - Horarios de consulta
  - Ubicación de la consulta

### 3. Actualizar turno médico
- **Método HTTP**: PUT
- **Ruta de la API**: `/turnos/{id}`
- **Descripción**: Este endpoint permitirá a los usuarios actualizar la información de un turno médico existente identificado por su ID. Se deberá enviar en el cuerpo de la solicitud los datos actualizados, que podrían incluir:
  - Nueva fecha y hora de la cita
  - ID del nuevo médico especialista
  - Nuevo motivo de la consulta

### 4. Eliminar turno médico
- **Método HTTP**: DELETE
- **Ruta de la API**: `/turnos/{id}`
- **Descripción**: Este endpoint permitirá a los usuarios cancelar un turno médico existente identificado por su ID. No se requerirá enviar ningún dato en el cuerpo de la solicitud.

### 5. Descargar receta médica
- **Método HTTP**: GET
- **Ruta de la API**: `/recetas/{id}`
- **Descripción**: Este endpoint permitirá a los pacientes autorizados descargar su receta médica proporcionando el ID del turno asociado a la receta. Se verificará la autenticación del usuario y la validez de la receta antes de permitir la descarga.

## Información Técnica del proyecto

- **Lenguaje de Programación**: Java
- **Framework**: Quarkus
- **Persistencia de Datos**: Utilizacion de Panache (parte de Quarkus) para la capa de persistencia de datos.
  - Hibernate ORM junto con Panache para simplificar aún más la interacción con la base de datos.
- **Documentación de la API**: Swagger 

## Instrucciones para Configuración y Ejecución

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/nombre-del-repositorio.git
   cd nombre-del-repositorio

2. **Configurar las dependencias**:
   Asegúrate de tener Java y Maven instalados en tu sistema.
   Ejecuta mvn clean install para descargar las dependencias necesarias.

3. **Configurar la base de datos**:
   Configura la conexión a la base de datos en el archivo application.properties según tu entorno.

4. **Ejecutar la aplicación**:
    mvn quarkus:dev

5. **Acceder a la documentación de la API**:
   Una vez que la aplicación esté en funcionamiento, puedes acceder a la documentación de Swagger en http://localhost:8080/swagger-ui.
   Ademas en el directorio raiz del proyecto se encuentra la carpeta "img" con screenshots asociadas al proyecto.

<p>
  <h2><b>Si tenes alguna pregunta o necesitas ayuda adicional, no dudes en abrir un issue en el repositorio. ¡Gracias por tu colaboración! 👋👋👋</b></h2>
</p> 
   
-------------------------------------------------------------------------------

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/equipo1obrasocial-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- REST resources for Hibernate ORM with Panache ([guide](https://quarkus.io/guides/rest-data-panache)): Generate Jakarta REST resources for your Hibernate Panache entities and repositories
- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- Quarkus Extension for Spring Security API ([guide](https://quarkus.io/guides/spring-security)): Secure your application with Spring Security annotations
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- SmallRye JWT ([guide](https://quarkus.io/guides/security-jwt)): Secure your applications with JSON Web Token
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC
