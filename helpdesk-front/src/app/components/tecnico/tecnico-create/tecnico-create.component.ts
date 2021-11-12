import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Tecnico } from 'src/app/models/tecnico';
import { TecnicoService } from 'src/app/services/tecnico.service';

@Component({
  selector: 'app-tecnico-create',
  templateUrl: './tecnico-create.component.html',
  styleUrls: ['./tecnico-create.component.css']
})
export class TecnicoCreateComponent implements OnInit {

  tecnico: Tecnico = {
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

  constructor(private tecnicoService: TecnicoService,
              private toast: ToastrService,
              private router: Router) { }

  ngOnInit(): void {
  }

  create(): void {
    console.log(this.tecnico);
    this.tecnicoService.create(this.tecnico).subscribe(response => {
      this.toast.success('Técnico cadastrado com sucesso', 'Cadastro');
      this.router.navigate(['tecnicos']);
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
    if (this.tecnico.perfis.includes(perfilSelecionado)) { // se tecnico já tiver esse perfil
      this.tecnico.perfis.splice(this.tecnico.perfis.indexOf(perfilSelecionado), 1); // tira esse perfil
    } else {
      this.tecnico.perfis.push(perfilSelecionado); // add esse perfil
    }    
  }

  validaCampos(): boolean {
    return this.nome.valid && this.cpf.valid
      && this.email.valid && this.senha.valid;
  }


}
