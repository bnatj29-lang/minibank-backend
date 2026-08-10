package com.minibank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Essa é a "porta de entrada" da aplicação.
// Quando rodamos o projeto, é esse metodo main() que liga tudo:
// sobe o servidor, conecta no banco e deixa a API pronta para receber requisições.
@SpringBootApplication
public class MinibankApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinibankApplication.class, args);
    }
}
