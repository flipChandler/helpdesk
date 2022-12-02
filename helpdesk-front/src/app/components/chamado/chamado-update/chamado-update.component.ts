import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Cliente } from 'src/app/models/cliente';
import { Tecnico } from 'src/app/models/tecnico';
import { ClienteService } from 'src/app/services/cliente.service';
import { TecnicoService } from 'src/app/services/tecnico.service';
import { ChamadoService } from 'src/app/services/chamado.service';
import { Chamado } from 'src/app/models/chamado';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-chamado-update',
  templateUrl: './chamado-update.component.html',
  styleUrls: ['./chamado-update.component.css']
})
export class ChamadoUpdateComponent implements OnInit {

  chamado: Chamado = {
    prioridade:  '',
    status:      '',
    titulo:      '',
    observacoes: '',
    tecnico:     '',
    cliente:     '',
    nomeCliente: '',
    nomeTecnico: '',
    tipoServico: '',
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
              private router: Router,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.chamado.id = this.activatedRoute.snapshot.paramMap.get('id');
    this.findById();
    this.findAllClientes();
    this.findAllTecnicos();
  }

  findById() {
    this.chamadoService.findById(this.chamado.id).subscribe(response => {
      this.chamado = response;
    }, exception => {
      this.toastService.error(exception.error.error);
    });
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

  update() {
    this.chamadoService.update(this.chamado).subscribe(response => {
      this.toastService.success('Chamado atualizado com sucesso!', 'Update Chamado');
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
           this.cliente.valid;
  }

  retornaStatus(valor: any): string {
    let status = '';
    switch (valor) {
      case 0:
        status = 'ABERTO';
        break;
      case 1:
        status = 'EM ANDAMENTO';
        break;
      case 2:
        status = 'ENCERRADO';
        break;
    }
    return status;
  }

  retornaPrioridade(valor: any): string {
    let prioridade = '';
    switch (valor) {
      case 0:
        prioridade = 'BAIXA';
        break;
      case 1:
        prioridade = 'MÉDIA';
        break;
      case 2:
        prioridade = 'ALTA';
        break;
    }
    return prioridade;
  }
}
