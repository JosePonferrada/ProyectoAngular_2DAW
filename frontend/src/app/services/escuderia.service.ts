import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Escuderia } from '../models/escuderia.model';

@Injectable({
  providedIn: 'root'
})
export class EscuderiaService {
  private apiUrl = `${environment.apiUrl}/escuderias`;
  
  constructor(private http: HttpClient) { }
  
  getAllEscuderias(): Observable<Escuderia[]> {
    return this.http.get<Escuderia[]>(`${this.apiUrl}/obtenerTodas`);
  }
  
  getEscuderiaById(id: number): Observable<Escuderia> {
    return this.http.post<Escuderia>(`${this.apiUrl}/obtenerPorId`, { id });
  }
  
  getEscuderiaByNombre(nombre: string): Observable<Escuderia> {
    return this.http.post<Escuderia>(`${this.apiUrl}/obtenerPorNombre`, { nombre });
  }
  
  getEscuderiasByPais(pais: string): Observable<Escuderia[]> {
    return this.http.post<Escuderia[]>(`${this.apiUrl}/obtenerPorPais`, { pais });
  }
  
  addEscuderia(escuderia: Escuderia): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/anadirNueva`, escuderia);
  }
  
  updateEscuderia(escuderia: Escuderia): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/editar`, escuderia);
  }
  
  deleteEscuderia(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/eliminar`, { body: { id } });
  }
  
  getPilotosEscuderia(id: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/obtenerPilotos`, { id });
  }
}