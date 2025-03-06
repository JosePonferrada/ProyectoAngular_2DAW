import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { PilotoService } from '../../../services/piloto.service';
import { EscuderiaService } from '../../../services/escuderia.service';
import { Piloto } from '../../../models/piloto.model';
import { Escuderia } from '../../../models/escuderia.model';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-piloto-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './piloto-detail.component.html',
  styleUrl: './piloto-detail.component.css'
})
export class PilotoDetailComponent implements OnInit {
  piloto: Piloto | null = null;
  escuderia: Escuderia | null = null;
  loading = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private pilotoService: PilotoService,
    private escuderiaService: EscuderiaService,
    public authService: AuthService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadPiloto(Number(id));
    } else {
      this.error = 'ID no proporcionado';
      this.loading = false;
    }
  }

  loadPiloto(id: number): void {
    this.pilotoService.getPilotoById(id).subscribe({
      next: (data) => {
        this.piloto = data;
        console.log('Piloto cargado:', this.piloto);
        
        // Si hay ID de escudería, cargar los datos completos de la escudería
        if (this.piloto && this.piloto.escuderia_id) {
          this.loadEscuderia(this.piloto.escuderia_id);
        } else {
          this.loading = false;
        }
      },
      error: (err) => {
        console.error('Error cargando piloto:', err);
        this.error = 'Error cargando datos del piloto';
        this.loading = false;
      }
    });
  }

  loadEscuderia(id: number): void {
    console.log('Cargando escudería con ID:', id);
    this.escuderiaService.getEscuderiaById(id).subscribe({
      next: (data) => {
        console.log('Escudería cargada:', data);
        this.escuderia = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando escudería:', err);
        // No mostrar error, solo registrarlo
        this.loading = false;
      }
    });
  }

  isAdmin(): boolean {
    const user = this.authService.currentUser();
    return user?.role === 'ADMIN';
  }
}