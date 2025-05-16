package com.github.cidarosa.ms_pedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsPedidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPedidoApplication.class, args);
	}

}
