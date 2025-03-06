import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { PilotoService } from '../../../services/piloto.service';
import { EscuderiaService } from '../../../services/escuderia.service';
import { Piloto } from '../../../models/piloto.model';
import { Escuderia } from '../../../models/escuderia.model';
import { ComponentWithUnsavedChanges } from '../../../guards/unsaved-changes.guard';

@Component({
  selector: 'app-piloto-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './piloto-form.component.html',
  styleUrls: ['./piloto-form.component.css']
})
export class PilotoFormComponent implements OnInit, ComponentWithUnsavedChanges {
  pilotoForm: FormGroup;
  escuderias: Escuderia[] = [];
  editMode = false;
  pilotoId: number | null = null;
  loading = true;
  submitting = false;
  error: string | null = null;
  success: string | null = null;

  constructor(
    private fb: FormBuilder,
    private pilotoService: PilotoService,
    private escuderiaService: EscuderiaService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.pilotoForm = this.fb.group({
      dorsal: ['', [Validators.required, Validators.min(1), Validators.max(99)]],
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      escuderia_id: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadEscuderias();

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editMode = true;
      this.pilotoId = Number(id);
      this.loadPilotoData(Number(id));
    } else {
      this.loading = false;
    }
  }

  loadEscuderias(): void {
    this.escuderiaService.getAllEscuderias().subscribe({
      next: (data) => {
        this.escuderias = data;
      },
      error: (err) => {
        this.error = 'Error loading escuderías';
        console.error(err);
      }
    });
  }

  loadPilotoData(id: number): void {
    this.pilotoService.getPilotoById(id).subscribe({
      next: (piloto) => {
        this.pilotoForm.patchValue({
          dorsal: piloto.dorsal,
          nombre: piloto.nombre,
          escuderia_id: piloto.escuderia_id
        });
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error loading piloto data';
        this.loading = false;
        console.error(err);
      }
    });
  }

  onSubmit(): void {
    if (this.pilotoForm.invalid) {
      this.pilotoForm.markAllAsTouched();
      return;
    }

    this.submitting = true;
    this.error = null;
    this.success = null;

    const formData = this.pilotoForm.value;

    if (this.editMode && this.pilotoId) {
      const piloto: Piloto = {
        id: this.pilotoId,
        dorsal: formData.dorsal,
        nombre: formData.nombre,
        escuderia_id: Number(formData.escuderia_id)
      };

      console.log(piloto);

      this.pilotoService.updatePiloto(piloto).subscribe({
        next: (response) => {
          console.log('Respuesta del servidor:', JSON.stringify(response));
          this.submitting = false;
          if (response?.result === 'ok' || response?.resultado === 'ok') {
            this.success = 'Piloto actualizado correctamente';
            this.pilotoForm.markAsPristine();
            // Navigate after a short delay to show success message
            setTimeout(() => this.router.navigate(['/pilotos', this.pilotoId]), 1500);
          } else {
            // Mostrar mensaje de error desde el servidor o mensaje predeterminado
            this.error = response.mensaje || response.error || 'Error al actualizar el piloto';
          }
        },
        error: (err) => {
          this.submitting = false;
          this.error = 'Error al actualizar el piloto';
          console.error(err);
        }
      });
    } else {
      // Generate a temporary ID (backend will assign the real one)
      const piloto: Piloto = {
        id: 0, // Temporary, will be assigned by backend
        dorsal: formData.dorsal,
        nombre: formData.nombre,
        escuderia_id: formData.escuderia_id
      };

      this.pilotoService.addPiloto(piloto).subscribe({
        next: (response) => {
          console.log('Respuesta completa del servidor:', response);

          // Verificar ambos posibles campos de éxito/error
          if (response.resultado === 'ok' || response.result === 'ok') {
            // Éxito - navegar o mostrar mensaje
            this.router.navigate(['/pilotos']);
          } else {
            // Error desde el servidor
            console.error('Error del servidor:', response.mensaje || response.error || 'Error desconocido');
            // Mostrar mensaje de error
          }
          this.loading = false;
        },
        error: (err) => {
          console.error('Error HTTP:', err);
          this.loading = false;
          // Mostrar mensaje de error
        }
      });
    }
  }

  hasUnsavedChanges(): boolean {
    return this.pilotoForm.dirty;
  }

  // Field validation helpers
  isInvalidField(fieldName: string): boolean {
    const field = this.pilotoForm.get(fieldName);
    return field !== null && field.invalid && (field.dirty || field.touched);
  }

  getErrorMessage(fieldName: string): string {
    const field = this.pilotoForm.get(fieldName);
    if (!field) return '';

    if (field.hasError('required')) {
      return 'Este campo es obligatorio';
    }
    if (field.hasError('minlength')) {
      return `Debe tener al menos ${field.getError('minlength').requiredLength} caracteres`;
    }
    if (field.hasError('min')) {
      return `El valor debe ser mayor o igual a ${field.getError('min').min}`;
    }
    if (field.hasError('max')) {
      return `El valor debe ser menor o igual a ${field.getError('max').max}`;
    }

    return 'Campo inválido';
  }
}