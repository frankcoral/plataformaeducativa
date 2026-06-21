## Ejecución con Docker Compose

Como mejora técnica, se incorpora un archivo `docker-compose.yml` para facilitar el levantamiento del backend de la plataforma educativa desde un único comando.

Antes de ejecutar el contenedor, se deben configurar las variables de entorno de AWS requeridas por la aplicación:

```bash
AWS_ACCESS_KEY_ID=TU_ACCESS_KEY
AWS_SECRET_ACCESS_KEY=TU_SECRET_KEY
AWS_SESSION_TOKEN=TU_SESSION_TOKEN