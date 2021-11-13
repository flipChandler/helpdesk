import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-update',
  templateUrl: './cliente-update.component.html',
  styleUrls: ['./cliente-update.component.css']
})
export class ClienteUpdateComponent implements OnInit {

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
              private router: Router,
              private activatedRoute: ActivatedRoute,
              ) { }
              

  ngOnInit(): void {
    this.cliente.id = this.activatedRoute.snapshot.paramMap.get('id');
    this.findById();    
  }

  findById(): void {
    this.clienteService.findById(this.cliente.id).subscribe (response => {
      response.perfis = [];
      this.cliente = response;     
    })
  }

  update(): void {
    this.clienteService.update(this.cliente).subscribe(() => {
      this.toast.success('Cliente atualizado com sucesso', 'Update');
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
