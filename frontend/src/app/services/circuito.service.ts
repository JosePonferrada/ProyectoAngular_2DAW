import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Circuito } from '../models/circuito.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CircuitoService {
  private apiUrl = `${environment.apiUrl}/circuitos`;

  constructor(private http: HttpClient) { }

  getCircuitos(): Observable<Circuito[]> {
    return this.http.get<Circuito[]>(`${this.apiUrl}/obtenerTodos`);
  }

  getCircuitoById(id: number): Observable<Circuito> {
    return this.http.post<Circuito>(`${this.apiUrl}/obtenerPorId`, { id });
  }

  getParticipantes(): Observable<Circuito[]> {
    return this.http.get<Circuito[]>(`${this.apiUrl}/obtenerParticipantes`);
  }

  createCircuito(circuito: Circuito): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/anadirNuevo`, circuito);
  }

  updateCircuito(circuito: Circuito): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/editar`, circuito);
  }

  deleteCircuito(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/eliminar1`, { body: { id } });
  }
}