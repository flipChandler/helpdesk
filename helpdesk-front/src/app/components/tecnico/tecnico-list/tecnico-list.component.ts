import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { TecnicoService } from 'src/app/services/tecnico.service';
import { Tecnico } from '../../../models/tecnico';

@Component({
  selector: 'app-tecnico-list',
  templateUrl: './tecnico-list.component.html',
  styleUrls: ['./tecnico-list.component.css']
})
export class TecnicoListComponent implements OnInit {

  ELEMENT_DATA: Tecnico[] = [];
  displayedColumns: string[] = ['id', 'nome', 'cpf', 'email', 'acoes'];
  dataSource: MatTableDataSource<Tecnico>;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private tecnicoService: TecnicoService) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.tecnicoService.findAll().subscribe(response => {
      this.ELEMENT_DATA = response;
      this.dataSource = new MatTableDataSource<Tecnico>(this.ELEMENT_DATA);
      this.dataSource.paginator = this.paginator;
    });
  } 

  applyFilter(event: Event) {
    // cada evento no html está sendo recebido nesse filtro
    const filterValue = (event.target as HTMLInputElement).value; 
    console.log(filterValue);
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}


