import { TipoServicos } from './data/tipoServicos';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators, FormBuilder, FormGroup } from '@angular/forms';
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

  chamadoForm: FormGroup

  clientes: Cliente[] = [];
  tecnicos: Tecnico[] = [];

  constructor(
    private chamadoService: ChamadoService,
    private clienteService: ClienteService,
    private tecnicoService: TecnicoService,
    private toastService: ToastrService,
    private fb: FormBuilder,
    private router: Router
  ) {

    this.chamadoForm = this.fb.group({
      titulo:      ['', [Validators.required]],
      prioridade:  ['', [Validators.required]],
      status:      ['', [Validators.required]],
      observacoes: ['', [Validators.required]],
      tecnico:     ['', [Validators.required]],
      cliente:     ['', [Validators.required]],
      tipoServico: ['', [Validators.required]]
    })

    this.chamadoForm.valueChanges.subscribe(() => console.log(this.chamadoForm.value))
  }

  ngOnInit(): void {
    this.findAllClientes();
    this.findAllTecnicos();
  }

  get tipoServicos() {
    return TipoServicos
  }

  findAllClientes() {
    this.clienteService.findAll().subscribe(response => {
      this.clientes = response;
      console.log(this.clientes)
    });
  }

  findAllTecnicos() {
    this.tecnicoService.findAll().subscribe(response => {
      this.tecnicos =  response;
      console.log(this.tecnicos)
    })
  }

  create() {
    this.chamadoService.create(this.chamadoForm.value).subscribe(response => {
      this.toastService.success('Chamado criado com sucesso!', 'Novo Chamado');
      this.router.navigate(['chamados']);
    }, exception => {
      this.toastService.error(exception.error.error);
    })
  }

}
