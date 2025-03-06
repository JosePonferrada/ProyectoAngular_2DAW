import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EscuderiaService } from '../../../services/escuderia.service';
import { Escuderia } from '../../../models/escuderia.model';
import { Piloto } from '../../../models/piloto.model';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-escuderia-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './escuderia-detail.component.html',
  styleUrl: './escuderia-detail.component.css'
})
export class EscuderiaDetailComponent implements OnInit {
  escuderia: Escuderia | null = null;
  pilotos: Piloto[] = [];
  loading = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private escuderiaService: EscuderiaService,
    public authService: AuthService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadEscuderia(Number(id));
    } else {
      this.error = 'ID no proporcionado';
      this.loading = false;
    }
  }

  loadEscuderia(id: number): void {
    this.escuderiaService.getEscuderiaById(id).subscribe({
      next: (data) => {
        this.escuderia = data;
        this.loadPilotos(id);
      },
      error: (err) => {
        this.error = 'Error loading escuderia';
        this.loading = false;
        console.error(err);
      }
    });
  }

  loadPilotos(id: number): void {
    this.escuderiaService.getPilotosEscuderia(id).subscribe({
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
  
  isAdmin(): boolean {
    const user = this.authService.currentUser();
    return user?.role === 'ADMIN';
  }
}