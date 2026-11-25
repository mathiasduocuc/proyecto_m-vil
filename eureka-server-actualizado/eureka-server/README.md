# eureka-server

Java 21 / Spring Boot 4.0.10 / Spring Cloud 2025.1.0.

Desde la carpeta con pom.xml: `docker compose up -d --build` y `docker compose logs --tail=50`.

Dashboard: http://localhost:8761. No expone el puerto fuera de localhost. Los clientes existentes requieren agregar Eureka Client y configurar su URL de registro.

No se ha compilado ni ejecutado este proyecto en el entorno de entrega.

Usuarios-service (8081) y api-gateway (8080) se registran mediante Eureka Client; no se necesita una lista fija. Ver README de Gateway para instrucciones.
