package com.felipe.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.felipe.helpdesk.domain.Chamado;
import com.felipe.helpdesk.domain.Cliente;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.enums.Perfil;
import com.felipe.helpdesk.domain.enums.Prioridade;
import com.felipe.helpdesk.domain.enums.Status;
import com.felipe.helpdesk.repositories.ChamadoRepository;
import com.felipe.helpdesk.repositories.ClienteRepository;
import com.felipe.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private ChamadoRepository chamadoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instanciaDB() {
		Tecnico tecnico1 = new Tecnico(null, "Felipe Santos", "05062419042", "felipe@gmail.com", encoder.encode("123"));
		tecnico1.addPerfil(Perfil.ADMIN);
		Tecnico tecnico2 = new Tecnico(null, "Jackie Brown", "83044666007", "jackie@gmail.com", encoder.encode("456"));
		Tecnico tecnico3 = new Tecnico(null, "Ayn Rand", "35661064020", "rand@gmail.com", encoder.encode("789"));
		Tecnico tecnico4 = new Tecnico(null, "Eva Mendes", "21942523041", "eva@gmail.com", encoder.encode("147"));

		Cliente cliente1 = new Cliente(null, "Linus Torvalds", "84984904054", "linus@gmail.com", encoder.encode("357"));
		Cliente cliente2 = new Cliente(null, "Bill Gates", "96446415079", "bill@gmail.com", encoder.encode("741"));
		Cliente cliente3 = new Cliente(null, "Steve Jobs", "07347795001", "jobs@gmail.com", encoder.encode("619"));
		Cliente cliente4 = new Cliente(null, "James Gosling", "16572473090", "gosling@gmail.com", encoder.encode("7891"));

		Chamado chamado1 = new Chamado(null, 
									   Prioridade.MEDIA,
									   Status.ANDAMENTO,
									   "Chamado 01",
									   "Primeiro Chamado",
									   tecnico1, cliente1);
		
		Chamado chamado2 = new Chamado(null,
									   Prioridade.BAIXA,
									   Status.ANDAMENTO,
									   "Chamado 02",
									   "Segundo Chamado",
									   tecnico2, cliente4);
		
		Chamado chamado3 = new Chamado(null,
									   Prioridade.ALTA,
									   Status.ANDAMENTO,
									   "Chamado 03",
									   "Terceiro Chamado",
									   tecnico1, cliente2);
		
		Chamado chamado4 = new Chamado(null, 
									   Prioridade.MEDIA,
									   Status.ANDAMENTO, 
									   "Chamado 04", 
									   "Quarto Chamado",
									   tecnico3, cliente1);

		tecnicoRepository.saveAll(Arrays.asList(tecnico1, tecnico2, tecnico3, tecnico4));
		clienteRepository.saveAll(Arrays.asList(cliente1, cliente2, cliente3, cliente4));
		chamadoRepository.saveAll(Arrays.asList(chamado1, chamado2, chamado3, chamado4));

	}
}
