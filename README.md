# Foro Hub - API REST
## Descripción
Foro Hub es una API REST desarrollada como parte del challenge de Alura Latam. Esta API proporciona funcionalidades para gestionar usuarios, cursos, tópicos y respuestas en un foro. Está construida utilizando Spring Boot y otras tecnologías modernas para asegurar un rendimiento robusto y seguro.

## Objetivo
El objetivo de este proyecto es implementar una API REST que permita a los usuarios interactuar con un foro de manera eficiente y segura, cumpliendo con los requisitos y estándares establecidos por Alura Latam en su desafío.

## Características
Gestión de Usuarios: Registro, autenticación, actualización y desactivación de usuarios.
Gestión de Cursos: Creación, actualización y eliminación de cursos.
Gestión de Tópicos: Creación, listado, búsqueda, actualización y eliminación de tópicos.
Gestión de Respuestas: Creación, listado y eliminación de respuestas a tópicos.
Seguridad: Implementación de JWT para autenticación y autorización de usuarios.
Validaciones: Validaciones de datos de entrada para asegurar la integridad de la información.
Tecnologías Utilizadas
Java 17
Spring Boot 3.3.1
Spring Data JPA
Spring Security
Flyway (para migraciones de base de datos)
MySQL (base de datos)
Lombok (para reducir el boilerplate)
Spring Boot DevTools (para facilitar el desarrollo)
Swagger/OpenAPI (para la documentación de la API)
JWT (para la gestión de tokens de seguridad)

## Uso
La API expone los siguientes endpoints principales:

/usuarios: Endpoints para la gestión de usuarios.
/cursos: Endpoints para la gestión de cursos.
/topicos: Endpoints para la gestión de tópicos.
/respuestas: Endpoints para la gestión de respuestas.
Para más detalles sobre los endpoints y su uso, se puede acceder a la documentación de Swagger en: http://localhost:8080/swagger-ui.html
