package com.pds.pingou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
	info = @Info(
		title = "Camisa Club API",
		version = "1.0",
		description = "API REST para sistema de assinatura de camisas de futebol"
	),
	servers = { @Server(url = "/", description = "Default Server URL")}
)
@SpringBootApplication
public class PingouApplication {

	public static void main(String[] args) {
		SpringApplication.run(PingouApplication.class, args);
		System.out.println("\n===========================================");
		System.out.println("🎽 CAMISA CLUB - Sistema de Assinatura");
		System.out.println("===========================================");
		System.out.println("✅ Aplicação iniciada com sucesso!");
		System.out.println("📚 Swagger UI: http://localhost:8080/swagger-ui.html");
		System.out.println("🔗 API Base: http://localhost:8080/api");
		System.out.println("===========================================\n");
	}

}
