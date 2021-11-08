import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Credenciais } from 'src/app/models/credenciais';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  credenciais: Credenciais = {
    email: '',
    senha: ''
  }

  email = new FormControl(null, Validators.email);
  senha = new FormControl(null, Validators.minLength(3));

  constructor(private toast: ToastrService,
              private authService: AuthService,
              private router: Router) { }

  ngOnInit(): void {
  }

   // não pega o 'Bearer ' que tem 7 caracteres, somente o token
  logar() {
    this.authService.authenticate(this.credenciais).subscribe((response) => {
      this.authService.successfulLogin(response.headers
                                               .get('Authorization')
                                               .substring(7));

      this.router.navigate(['']);
    }, () => {
      this.toast.error('Usuário e/ou senha inválidos!');
    })
  }

  validaCampos(): boolean {
    return this.email.valid && this.senha.valid;
  }  

}
