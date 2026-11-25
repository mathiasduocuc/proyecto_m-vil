# Integracion local RIncon Sur

Iniciar Eureka primero, luego usuarios y Gateway. En PowerShell, en cada carpeta con pom.xml, cargar JWT_SECRET desde el .env de registros (no imprimir ni compartir):

```powershell
$env:JWT_SECRET = (Get-Content "C:\Users\Matix\Downloads\microservicio_movil\registros-service\registros-service\.env" | Where-Object { $_ -like "JWT_SECRET=*" }) -replace "^JWT_SECRET=", ""
docker compose up -d --build
docker compose logs --tail=50
```

Gateway GET /api/auth/users requiere JWT SUPERVISOR y va a usuarios:8081; POST /api/auth/login va a auth:8082. Registros y estadisticas conservan sus rutas. Otros endpoints de auth estan bloqueados en Gateway hasta revisar sus permisos. Usuarios tambien valida JWT y ya no expone login demo. No usar el contenedor antiguo de usuarios simultaneamente.

Eureka no requiere lista fija de servicios. Usuarios y Gateway tienen cliente configurado. Para auth, registros y estadisticas aun se debe agregar Eureka Client y configurar direcciones alcanzables. Las rutas Gateway son directas inicialmente; para usuarios se puede establecer USUARIOS_URL=lb://usuarios-service despues de verificar su registro y conectividad. host.docker.internal debe ser alcanzable desde Docker Desktop.

No se ha compilado ni ejecutado esta actualizacion. La base de usuarios sigue separada de auth y conserva las migraciones originales con contrasenas de prueba en texto plano; no usar en produccion. No se implementa sincronizacion de cuentas.
