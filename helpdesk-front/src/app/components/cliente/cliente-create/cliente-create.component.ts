import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-create',
  templateUrl: './cliente-create.component.html',
  styleUrls: ['./cliente-create.component.css']
})
export class ClienteCreateComponent implements OnInit {

  cliente: Cliente = {
    id: '',
    nome: '',
    cpf: '',
    email: '',
    senha: '',
    perfis: [],
    dataCriacao: ''
  }

  nome: FormControl = new FormControl(null, Validators.minLength(3));
  cpf: FormControl = new FormControl(null, Validators.required);
  email: FormControl = new FormControl(null, Validators.email);
  senha: FormControl = new FormControl(null, Validators.minLength(3));

  constructor(private clienteService: ClienteService,
              private toast: ToastrService,
              private router: Router) { }

  ngOnInit(): void {
  }

  create(): void {
    console.log(this.cliente);
    this.clienteService.create(this.cliente).subscribe(response => {
      this.toast.success('Cliente cadastrado com sucesso', 'Cadastro');
      this.router.navigate(['clientes']);
    }, exception => {
      if (exception.error.errors) {
        exception.error.errors.forEach(element => {
          this.toast.error(element.message);
        });
      } else {
        this.toast.error(exception.error.message);
      }
    });
  }

  addPerfil(perfilSelecionado: any): void {
    if (this.cliente.perfis.includes(perfilSelecionado)) { // se cliente já tiver esse perfil
      this.cliente.perfis.splice(this.cliente.perfis.indexOf(perfilSelecionado), 1); // tira esse perfil
    } else {
      this.cliente.perfis.push(perfilSelecionado); // add esse perfil
    }    
  }

  validaCampos(): boolean {
    return this.nome.valid && this.cpf.valid
      && this.email.valid && this.senha.valid;
  }


}
