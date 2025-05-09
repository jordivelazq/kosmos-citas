# Sistema de Citas Médicas

## Tecnologías  
- Java 21  
- Spring Boot 3.4.5  
- Spring Data JPA (H2)  
- Thymeleaf + Bootstrap 5  
- Maven  

## Requisitos  
- JDK 21  
- Maven 3.8+  
- Git  

## Instalación y ejecución  

1. **Clonar el repositorio**  
   git clone https://github.com/jordivelazq/kosmos-citas.git
   cd kosmos-citas
   
2. **Compilar y arrancar**  
./mvnw clean package
java -jar target/kosmos-0.0.1-SNAPSHOT.jar

3. **Compilar y arrancar**  
Acceder desde el navegador
Listado de citas: http://localhost:8080/citas
Crear nueva cita: http://localhost:8080/citas/nuevo
Editar cita: http://localhost:8080/citas/editar/{id}

Consola H2: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:db
Usuario: sa
Contraseña: (vacía)
