import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CircuitoService } from '../../../services/circuito.service';
import { Circuito } from '../../../models/circuito.model';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-circuito-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './circuito-detail.component.html',
  styleUrls: ['./circuito-detail.component.css']
})
export class CircuitoDetailComponent implements OnInit {
  circuito: Circuito | null = null;
  loading = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private circuitoService: CircuitoService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadCircuito(Number(id));
    } else {
      this.error = 'ID no proporcionado';
      this.loading = false;
    }
  }

  loadCircuito(id: number): void {
    this.loading = true;
    
    // Primero cargamos los datos básicos del circuito
    this.circuitoService.getCircuitoById(id).subscribe({
      next: (data) => {
        this.circuito = data;
        
        // Después cargamos los pilotos participantes
        this.loadParticipaciones(id);
      },
      error: (err) => {
        console.error('Error cargando circuito:', err);
        this.error = 'Error cargando datos del circuito';
        this.loading = false;
      }
    });
  }

  loadParticipaciones(id: number): void {
    this.circuitoService.getParticipantes().subscribe({
      next: (data) => {
        // Buscar el circuito actual en los datos de participantes
        const circuitoConParticipantes = data.find(c => c.id === id);
        
        if (circuitoConParticipantes && circuitoConParticipantes.pilotos) {
          // Actualizar el circuito con la lista de pilotos
          this.circuito = {
            ...this.circuito!,
            pilotos: circuitoConParticipantes.pilotos
          };
        }
        
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando participaciones:', err);
        // No mostramos error aquí para no interrumpir la visualización del circuito
        this.loading = false;
      }
    });
  }

  isAdmin(): boolean {
    const user = this.authService.currentUser();
    return user?.role === 'ADMIN';
  }
}