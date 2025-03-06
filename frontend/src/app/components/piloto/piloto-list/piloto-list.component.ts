import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PilotoService } from '../../../services/piloto.service';
import { Piloto } from '../../../models/piloto.model';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-piloto-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './piloto-list.component.html',
  styleUrl: './piloto-list.component.css'
})
export class PilotoListComponent implements OnInit {
  pilotos: Piloto[] = [];
  loading = true;
  error: string | null = null;

  constructor(
    private pilotoService: PilotoService,
    public authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadPilotos();
  }

  loadPilotos(): void {
    this.loading = true;
    this.pilotoService.getAllPilotos().subscribe({
      next: (data) => {
        this.pilotos = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error loading pilotos';
        this.loading = false;
        console.error(err);
      }
    });
  }

  deletePiloto(id: number): void {
    if(confirm('¿Estás seguro de que quieres eliminar este piloto?')) {
      this.pilotoService.deletePiloto(id).subscribe({
        next: () => {
          this.loadPilotos();
        },
        error: (err) => {
          console.error('Error deleting piloto:', err);
        }
      });
    }
  }
  
  isAdmin(): boolean {
    const user = this.authService.currentUser();
    return user?.role === 'ADMIN';
  }
}