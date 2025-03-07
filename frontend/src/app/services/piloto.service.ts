import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { Piloto } from '../models/piloto.model';

@Injectable({
  providedIn: 'root'
})
export class PilotoService {
  private apiUrl = `${environment.apiUrl}/pilotos`;

  constructor(private http: HttpClient) { }

  getAllPilotos(): Observable<Piloto[]> {
    return this.http.get<Piloto[]>(`${this.apiUrl}/obtenerTodos`);
  }

  getPilotoById(id: number): Observable<Piloto> {
    return this.http.post<Piloto>(`${this.apiUrl}/obtenerPorId`, { id });
  }

  getPilotosByEscuderia(escuderiaId: number): Observable<Piloto[]> {
    return this.http.post<Piloto[]>(`${this.apiUrl}/obtenerPorEscuderia`, { escuderia_id: escuderiaId });
  }

  addPiloto(piloto: Piloto): Observable<any> {
    console.log('Enviando datos para nuevo piloto:', piloto);
    return this.http.post<any>(`${this.apiUrl}/anadirNuevo`, piloto)
      .pipe(
        tap(response => console.log('Respuesta del servidor:', response)),
        catchError(error => {
          console.error('Error en createPiloto:', error);
          return throwError(() => error);
        })
      );
  }

  updatePiloto(piloto: Piloto): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/editar`, piloto);
  }

  deletePiloto(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/eliminar1`, {
      body: { id } 
    }).pipe(
      tap(response => console.log('Respuesta eliminar piloto:', response)),
      catchError(error => {
        console.error('Error al eliminar piloto:', error);
        return throwError(() => error);
      })
    );
  }
}