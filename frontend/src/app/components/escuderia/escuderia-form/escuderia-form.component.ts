import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router'; // Añade RouterLink aquí
import { EscuderiaService } from '../../../services/escuderia.service';
import { ComponentWithUnsavedChanges } from '../../../guards/unsaved-changes.guard';

@Component({
  selector: 'app-escuderia-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink], // Añade RouterLink aquí
  templateUrl: './escuderia-form.component.html',
  styleUrl: './escuderia-form.component.css'
})
export class EscuderiaFormComponent implements OnInit, ComponentWithUnsavedChanges {
  form!: FormGroup;
  isEditMode = false;
  escuderiaId!: number;
  loading = false;
  submitted = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private escuderiaService: EscuderiaService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: ['', [Validators.required]],
      pais: ['', [Validators.required]]
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.escuderiaId = Number(id);
      this.loadEscuderiaData(this.escuderiaId);
    }
  }

  loadEscuderiaData(id: number): void {
    this.loading = true;
    this.escuderiaService.getEscuderiaById(id).subscribe({
      next: (escuderia) => {
        this.form.patchValue({
          nombre: escuderia.nombre,
          pais: escuderia.pais
        });
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error loading escuderia data';
        this.loading = false;
        console.error(err);
      }
    });
  }

  onSubmit(): void {
    this.submitted = true;
    
    if (this.form.invalid) {
      return;
    }
    
    this.loading = true;
    
    const escuderiaData = {
      ...this.form.value,
      id: this.isEditMode ? this.escuderiaId : 0
    };
    
    if (this.isEditMode) {
      this.updateEscuderia(escuderiaData);
    } else {
      this.createEscuderia(escuderiaData);
    }
  }
  
  createEscuderia(data: any): void {
    this.escuderiaService.addEscuderia(data).subscribe({
      next: (result) => {
        if (result.result === 'ok') {
          this.router.navigate(['/escuderias']);
        } else {
          this.error = result.mensaje || 'Error al crear la escudería';
          this.loading = false;
        }
      },
      error: (err) => {
        this.error = 'Error al crear la escudería';
        this.loading = false;
        console.error(err);
      }
    });
  }
  
  updateEscuderia(data: any): void {
    this.escuderiaService.updateEscuderia(data).subscribe({
      next: (result) => {
        if (result.result === 'ok') {
          this.router.navigate(['/escuderias']);
        } else {
          this.error = result.mensaje || 'Error al actualizar la escudería';
          this.loading = false;
        }
      },
      error: (err) => {
        this.error = 'Error al actualizar la escudería';
        this.loading = false;
        console.error(err);
      }
    });
  }
  
  // Implement the method required by the ComponentWithUnsavedChanges interface
  hasUnsavedChanges(): boolean {
    return this.form.dirty && !this.submitted;
  }
}