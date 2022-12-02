import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ChamadoService } from 'src/app/services/chamado.service';
import { Chamado } from '../../../models/chamado';
import { first, map } from 'rxjs/operators'
import { TipoServicos } from '../chamado-create/data/tipoServicos';

@Component({
  selector: 'app-chamado-list',
  templateUrl: './chamado-list.component.html',
  styleUrls: ['./chamado-list.component.css']
})
export class ChamadoListComponent implements OnInit {

  ELEMENT_DATA: Chamado[] = [];
  FILTERED_DATA: Chamado[] = [];

  displayedColumns: string[] = [
    'id',
    'titulo',
    'cliente',
    'tecnico',
    'tipoServico',
    'dataAbertura',
    'prioridade',
    'status',
    'acoes'
  ];

  dataSource = new MatTableDataSource<Chamado>();
  @ViewChild(MatPaginator) paginator: MatPaginator;

  labels = TipoServicos.map(el => el.descricao)
  data = {
    // labels: this.labels,
    datasets: [
        {
          data: [0,0,0,0,0,0,0],
          backgroundColor: [
              "#00e297",
              "#009f6a",
              "#009ed8",
              "#00729c",
              "#0042cf",
              "#002d8e",
              "#8a00b7"
          ],
        }
    ]
  };

  options = {
    legend: { display: false},
  }

  constructor(private chamadoService: ChamadoService) { }

  ngOnInit() {
    this.findAll();
  }

  findAll() {
    return this.chamadoService.findAll()
      .pipe(
        first()
      ).subscribe(response => {

        let chartData = [0,0,0,0,0,0,0]

        response.forEach(el => chartData[+el.tipoServico]++)
        
        this.data.datasets[0].data = chartData
        this.data = {...this.data}

        this.ELEMENT_DATA = response.map(
          chamado => {
            chamado.tipoServico = TipoServicos.find(el => String(el.id) == chamado.tipoServico)!.descricao
            return chamado
          }
        );

        this.dataSource = new MatTableDataSource<Chamado>(response);
        this.dataSource.paginator = this.paginator;
        console.log(this.ELEMENT_DATA)
      })
  }

  getQtdChamados(id: number, filterBy: string) {
    return this.ELEMENT_DATA.filter(el => el[filterBy] === id).length
  }


  applyFilter(event: Event) {
    // cada evento no html está sendo recebido nesse filtro
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
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

  orderByStatus(status: any) {
    let list: Chamado[] = [];
    this.ELEMENT_DATA.forEach(element => {
      if (element.status === status) {
        list.push(element);
      }
    });
    this.FILTERED_DATA = list;
    this.dataSource = new MatTableDataSource<Chamado>(list);
    this.dataSource.paginator = this.paginator;
  }

}