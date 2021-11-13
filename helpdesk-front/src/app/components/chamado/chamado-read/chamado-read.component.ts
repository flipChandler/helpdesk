import { Component, OnInit } from '@angular/core';
import { ChamadoService } from 'src/app/services/chamado.service';
import { Chamado } from 'src/app/models/chamado';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-chamado-read',
  templateUrl: './chamado-read.component.html',
  styleUrls: ['./chamado-read.component.css']
})
export class ChamadoReadComponent implements OnInit {

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



  constructor(private chamadoService: ChamadoService,
              private toastService: ToastrService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.chamado.id = this.activatedRoute.snapshot.paramMap.get('id');
    this.findById();
  }

  findById() {
    this.chamadoService.findById(this.chamado.id).subscribe(response => {
      this.chamado = response;
    }, exception => {
      this.toastService.error(exception.error.error);
    });
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
