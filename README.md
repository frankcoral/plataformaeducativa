# Plataforma Educativa - Semana 7

Proyecto desarrollado para la actividad formativa de la Semana 7 de Desarrollo Cloud Native (CDY2204).

Esta actividad integra RabbitMQ con Spring Boot para trabajar con colas y mensajes asincronos. El flujo implementado permite crear una inscripcion, enviar su resumen a una cola RabbitMQ, consumir el mensaje desde un endpoint y guardar el resumen procesado en Oracle Cloud.

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Security
- Spring AMQP
- RabbitMQ
- Docker Compose
- Oracle Cloud Database
- Postman

## RabbitMQ con Docker Compose

Para levantar RabbitMQ desde la raiz del proyecto:

```bash
docker compose up -d
```

Para verificar el contenedor:

```bash
docker ps
```

Panel de administracion RabbitMQ:

```text
http://localhost:15672
```

Credenciales:

```text
Usuario: guest
Contrasena: guest
```

Para detener RabbitMQ:

```bash
docker compose down
```

## Configuracion RabbitMQ

La aplicacion utiliza la siguiente configuracion:

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

app.rabbitmq.queue=resumen.inscripcion.queue
app.rabbitmq.exchange=resumen.inscripcion.exchange
app.rabbitmq.routing-key=resumen.inscripcion.routing
```

## Endpoints Semana 7

Crear inscripcion:

```http
POST /api/inscripciones
```

Enviar resumen de inscripcion a RabbitMQ:

```http
POST /api/resumenes-mq/{idInscripcion}/enviar-cola
```

Consumir mensaje desde RabbitMQ y guardar en Oracle Cloud:

```http
POST /api/resumenes-mq/consumir-guardar
```

Listar resumenes guardados:

```http
GET /api/resumenes-mq/guardados
```

## Flujo probado

1. Se levanto RabbitMQ con Docker Compose.
2. Se ingreso al panel de administracion de RabbitMQ.
3. Se creo una inscripcion desde Postman.
4. Se envio el resumen de inscripcion a la cola `resumen.inscripcion.queue`.
5. Se verifico el mensaje disponible en RabbitMQ.
6. Se consumio el mensaje desde un endpoint Spring Boot.
7. Se guardo el resumen procesado en Oracle Cloud.
8. Se verifico el registro almacenado desde Postman.

## Ejecucion de la aplicacion

Primero levantar RabbitMQ:

```bash
docker compose up -d
```

Luego ejecutar la aplicacion:

```bash
.\mvnw.cmd spring-boot:run
```

## Evidencia

La solucion fue validada mediante Postman y RabbitMQ Management, demostrando el envio del mensaje a la cola, el consumo del mensaje y el almacenamiento final en Oracle Cloud.
