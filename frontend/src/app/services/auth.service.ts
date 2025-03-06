import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { Usuario } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/usuarios`;
  currentUser = signal<Usuario | null>(null);

  constructor(private http: HttpClient) {
    this.loadUserFromStorage();
  }

  private loadUserFromStorage(): void {
    const userData = localStorage.getItem('user');
    if (userData) {
      this.currentUser.set(JSON.parse(userData));
    }
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/iniciarSesion`, { username, password })
      .pipe(
        tap(response => {
          if (response && response.token) {
            const user: Usuario = {
              id: response.id,
              username: response.username,
              email: response.email,
              role: response.role
            };
            
            localStorage.setItem('token', response.token);
            localStorage.setItem('user', JSON.stringify(user));
            this.currentUser.set(user);
          }
        })
      );
  }

  logout(): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/cerrarSesion`, {})
      .pipe(
        tap(() => {
          localStorage.removeItem('token');
          localStorage.removeItem('user');
          this.currentUser.set(null);
        })
      );
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  getUserRole(): string | null {
    const user = this.currentUser();
    return user ? user.role : null;
  }
}