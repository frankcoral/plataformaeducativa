## Ejecución con Docker Compose

Como mejora técnica, se incorpora un archivo `docker-compose.yml` para facilitar el levantamiento del backend de la plataforma educativa desde un único comando. Esta mejora permite administrar el contenedor de forma más ordenada, facilitando pruebas locales, despliegues y futuros procesos de integración continua.

Antes de ejecutar el contenedor, se deben configurar las variables de entorno de AWS requeridas por la aplicación. Para esto, se incluye el archivo `.env.example` como referencia:

```bash
AWS_ACCESS_KEY_ID=TU_ACCESS_KEY
AWS_SECRET_ACCESS_KEY=TU_SECRET_KEY
AWS_SESSION_TOKEN=TU_SESSION_TOKEN
```

Estas variables permiten que la aplicación pueda conectarse correctamente con los servicios de AWS utilizados en el proyecto, como el almacenamiento de archivos en S3.

Para levantar la aplicación con Docker Compose, ejecutar el siguiente comando desde la raíz del proyecto:

```bash
docker compose up -d
```

Para verificar que el contenedor quedó en ejecución:

```bash
docker compose ps
```

Para probar que la aplicación responde y que Spring Security está activo:

```bash
curl.exe -i http://localhost:8080/api/cursos
```

La respuesta esperada sin token es:

```bash
HTTP/1.1 401
WWW-Authenticate: Bearer
```

Esto confirma que el backend está protegido mediante Spring Security y que requiere un token JWT válido para acceder a sus endpoints.

Para detener el contenedor:

```bash
docker compose down
```
