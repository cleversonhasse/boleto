package com.contaazul.boleto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *	Classe que inicializa o spring boot 
 * 
 * @author Cléverson Hasse
 * @version 1.0.0
 * 
 */

@SpringBootApplication
public class BoletoApplication {

	 /**
     * Método main invocado no início da aplicação
     */
	public static void main(String[] args) {
		SpringApplication.run(BoletoApplication.class, args);
	}
}
