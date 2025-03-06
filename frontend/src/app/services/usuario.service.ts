import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Usuario } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = `${environment.apiUrl}/usuarios`;
  
  constructor(private http: HttpClient) { }
  
  getAllUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/obtenerTodos`);
  }
  
  getUsuarioById(id: number): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.apiUrl}/obtenerPorId`, { id });
  }
  
  getUsuarioByEmail(email: string): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.apiUrl}/obtenerPorEmail`, { email });
  }
  
  getUsuarioByUsername(username: string): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.apiUrl}/obtenerPorUsername`, { username });
  }
  
  addUsuario(usuario: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/anadirNuevo`, usuario);
  }
  
  updateUsuario(usuario: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/editar`, usuario);
  }
  
  deleteUsuario(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/eliminar`, { body: { id } });
  }
}