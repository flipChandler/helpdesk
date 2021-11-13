import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ChamadoService } from 'src/app/services/chamado.service';
import { Chamado } from '../../../models/chamado';

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
    'dataAbertura',
    'prioridade',
    'status',
    'acoes'
  ];

  dataSource = new MatTableDataSource<Chamado>();
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private chamadoService: ChamadoService) { }

  ngOnInit() {
    this.findAll();
  }

  findAll() {
    return this.chamadoService.findAll().subscribe(response => {
      this.ELEMENT_DATA = response;
      this.dataSource = new MatTableDataSource<Chamado>(response);
      this.dataSource.paginator = this.paginator;
    })
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