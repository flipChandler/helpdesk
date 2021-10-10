package com.felipe.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
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

	public void instanciaDB() {
		Tecnico tecnico1 = new Tecnico(null, "Felipe Santos", "05062419042", "felipe@gmail.com", "123");
		tecnico1.addPerfil(Perfil.ADMIN);

		Cliente cliente1 = new Cliente(null, "Linus Torvalds", "57683488089", "linus@gmail.com", "456");

		Chamado chamado1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado",
				tecnico1, cliente1);

		tecnicoRepository.saveAll(Arrays.asList(tecnico1));
		clienteRepository.saveAll(Arrays.asList(cliente1));
		chamadoRepository.saveAll(Arrays.asList(chamado1));

	}
}
