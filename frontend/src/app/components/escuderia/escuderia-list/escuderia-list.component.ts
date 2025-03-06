import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { EscuderiaService } from '../../../services/escuderia.service';
import { Escuderia } from '../../../models/escuderia.model';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-escuderia-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './escuderia-list.component.html',
  styleUrl: './escuderia-list.component.css'
})
export class EscuderiaListComponent implements OnInit {
  escuderias: Escuderia[] = [];
  loading = true;
  error: string | null = null;

  constructor(
    private escuderiaService: EscuderiaService,
    public authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadEscuderias();
  }

  loadEscuderias(): void {
    this.loading = true;
    this.escuderiaService.getAllEscuderias().subscribe({
      next: (data) => {
        this.escuderias = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error loading escuderias';
        this.loading = false;
        console.error(err);
      }
    });
  }

  deleteEscuderia(id: number): void {
    if(confirm('¿Estás seguro de que quieres eliminar esta escudería?')) {
      this.escuderiaService.deleteEscuderia(id).subscribe({
        next: () => {
          this.loadEscuderias();
        },
        error: (err) => {
          console.error('Error deleting escuderia:', err);
        }
      });
    }
  }
  
  isAdmin(): boolean {
    const user = this.authService.currentUser();
    return user?.role === 'ADMIN';
  }
}