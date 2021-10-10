package com.felipe.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.felipe.helpdesk.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	// qndo o perfil de test estiver ativo, esse método será invocado de forma automatica
	@Bean
	public void instanciaDB() {
		this.dbService.instanciaDB();
	}
}
