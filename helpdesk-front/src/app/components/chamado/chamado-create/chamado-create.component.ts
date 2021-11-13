import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Cliente } from 'src/app/models/cliente';
import { Tecnico } from 'src/app/models/tecnico';
import { ClienteService } from 'src/app/services/cliente.service';
import { TecnicoService } from 'src/app/services/tecnico.service';
import { ChamadoService } from 'src/app/services/chamado.service';
import { Chamado } from 'src/app/models/chamado';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-chamado-create',
  templateUrl: './chamado-create.component.html',
  styleUrls: ['./chamado-create.component.css']
})
export class ChamadoCreateComponent implements OnInit {

  chamado: Chamado = {
    prioridade:  '',
    status:      '',
    titulo:      '',
    observacoes: '',
    tecnico:     '',
    cliente:     '',
    nomeCliente: '',
    nomeTecnico: '',
  }

  clientes: Cliente[] = [];
  tecnicos: Tecnico[] = [];

  prioridade: FormControl = new FormControl(null, [Validators.required]);
  status:     FormControl = new FormControl(null, [Validators.required]);
  titulo:     FormControl = new FormControl(null, [Validators.required]);
  observacoes:FormControl = new FormControl(null, [Validators.required]);
  tecnico:    FormControl = new FormControl(null, [Validators.required]);
  cliente:    FormControl = new FormControl(null, [Validators.required]);

  constructor(private chamadoService: ChamadoService,
              private clienteService: ClienteService,
              private tecnicoService: TecnicoService,
              private toastService: ToastrService,
              private router: Router) { }

  ngOnInit(): void {
    this.findAllClientes();
    this.findAllTecnicos();
  }

  findAllClientes() {
    this.clienteService.findAll().subscribe(response => {
      this.clientes = response;      
    });
  }

  findAllTecnicos() {
    this.tecnicoService.findAll().subscribe(response => {
      this.tecnicos =  response;
    })
  }

  create() {
    this.chamadoService.create(this.chamado).subscribe(response => {
      this.toastService.success('Chamado criado com sucesso!', 'Novo Chamado');
      this.router.navigate(['chamados']);
    }, exception => {
      this.toastService.error(exception.error.error);
    })
  }

  validaCampos(): boolean {
    return this.prioridade.valid && 
           this.status.valid && 
           this.titulo.valid && 
           this.observacoes.valid && 
           this.tecnico.valid && 
           this.cliente.valid
  }

}