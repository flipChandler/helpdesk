import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Tecnico } from 'src/app/models/tecnico';
import { TecnicoService } from 'src/app/services/tecnico.service';

@Component({
  selector: 'app-tecnico-update',
  templateUrl: './tecnico-update.component.html',
  styleUrls: ['./tecnico-update.component.css']
})
export class TecnicoUpdateComponent implements OnInit {

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
              private router: Router,
              private activatedRoute: ActivatedRoute,
              ) { }
              

  ngOnInit(): void {
    this.tecnico.id = this.activatedRoute.snapshot.paramMap.get('id');
    this.findById();    
  }

  findById(): void {
    this.tecnicoService.findById(this.tecnico.id).subscribe (response => {
      response.perfis = [];
      this.tecnico = response;     
    })
  }

  update(): void {
    this.tecnicoService.update(this.tecnico).subscribe(() => {
      this.toast.success('Técnico atualizado com sucesso', 'Update');
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
