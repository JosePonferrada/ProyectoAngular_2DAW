import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CircuitoService } from '../../../services/circuito.service';
import { Circuito } from '../../../models/circuito.model';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-circuito-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './circuito-list.component.html',
  styleUrls: ['./circuito-list.component.css']
})
export class CircuitoListComponent implements OnInit {
  circuitos: Circuito[] = [];
  loading = true;
  error: string | null = null;

  constructor(
    private circuitoService: CircuitoService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadCircuitos();
  }

  loadCircuitos(): void {
    this.circuitoService.getCircuitos().subscribe({
      next: (data) => {
        this.circuitos = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando circuitos:', err);
        this.error = 'Error cargando los datos de circuitos';
        this.loading = false;
      }
    });
  }

  deleteCircuito(id: number): void {
    if (confirm('¿Estás seguro de que quieres eliminar este circuito?')) {
      this.circuitoService.deleteCircuito(id).subscribe({
        next: () => {
          this.circuitos = this.circuitos.filter(c => c.id !== id);
        },
        error: (err) => {
          console.error('Error eliminando circuito:', err);
          this.error = 'Error al eliminar el circuito';
        }
      });
    }
  }

  isAdmin(): boolean {
    const user = this.authService.currentUser();
    return user?.role === 'ADMIN';
  }
}