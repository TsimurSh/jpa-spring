package pl.tsimur.jpatest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//  https://www.baeldung.com/spring-rest-openapi-documentation
@OpenAPIDefinition(info = @Info(title = "Jpa example"), servers = {@Server(url = "http://localhost:8080")})
@SpringBootApplication
public class JpaApplication {

    public static void main(String[] args) {
        System.out.println("\t📁 API document : http://localhost:8080/swagger-ui.html \n");
        System.out.println("\t🐑 Swagger Yaml : http://localhost:8080/docs.yaml\n");

        SpringApplication.run(JpaApplication.class, args);
    }

}
