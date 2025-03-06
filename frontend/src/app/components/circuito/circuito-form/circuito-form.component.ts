import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CircuitoService } from '../../../services/circuito.service';

@Component({
  selector: 'app-circuito-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './circuito-form.component.html',
  styleUrls: ['./circuito-form.component.css']
})
export class CircuitoFormComponent implements OnInit {
  circuitoForm!: FormGroup;
  isEditMode = false;
  circuitoId = 0;
  loading = false;
  submitted = false;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private circuitoService: CircuitoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Inicializar formulario primero
    this.initForm();
    
    // Verificar si estamos en modo edición revisando la URL
    if (this.router.url.includes('/edit/')) {
      const id = this.route.snapshot.paramMap.get('id');
      if (id) {
        this.isEditMode = true;
        this.circuitoId = parseInt(id);
        this.loadCircuito(this.circuitoId);
      }
    } else {
      // Estamos en modo creación
      console.log('Modo creación de nuevo circuito');
      this.isEditMode = false;
    }
  }

  // Getter para acceder a los controles del formulario
  get f() { return this.circuitoForm.controls; }

  initForm(): void {
    this.circuitoForm = this.formBuilder.group({
      nombre: ['', Validators.required],
      pais: ['', Validators.required]
    });
  }

  loadCircuito(id: number): void {
    this.loading = true;
    this.circuitoService.getCircuitoById(id).subscribe({
      next: (data) => {
        if (data) {
          this.circuitoForm.patchValue({
            nombre: data.nombre,
            pais: data.pais
          });
        }
        this.loading = false;
      },
      error: (error) => {
        console.error('Error cargando circuito:', error);
        this.errorMessage = 'Error al cargar los datos del circuito';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    this.submitted = true;
    
    if (this.circuitoForm.invalid) {
      return;
    }
    
    this.loading = true;
    // Limpiar mensajes anteriores
    this.errorMessage = null;
    this.successMessage = null;
    
    const circuitoData = {
      ...this.circuitoForm.value,
      id: this.isEditMode ? this.circuitoId : 0
    };
    
    if (this.isEditMode) {
      this.updateCircuito(circuitoData);
    } else {
      this.createCircuito(circuitoData);
    }
  }
  
  createCircuito(circuito: any): void {
    this.circuitoService.createCircuito(circuito).subscribe({
      next: (response) => {
        this.loading = false;
        console.log('Respuesta completa del servidor:', response);
        
        // Considerar siempre como éxito si el backend devuelve algo
        // Ya que sabemos que el circuito se crea correctamente
        this.successMessage = 'Circuito creado correctamente';
        this.errorMessage = null;
        
        // Redireccionar después de un breve retraso
        setTimeout(() => {
          this.router.navigate(['/circuitos']);
        }, 1500);
      },
      error: (error) => {
        console.error('Error en la solicitud HTTP:', error);
        
        // Si sabemos que realmente se creó, podemos aún mostrar éxito
        // Esto es opcional, depende de tu preferencia
        if (error.status === 200) {
          this.successMessage = 'Circuito creado correctamente';
          this.errorMessage = null;
          setTimeout(() => {
            this.router.navigate(['/circuitos']);
          }, 1500);
        } else {
          this.loading = false;
          this.errorMessage = 'Error en la comunicación con el servidor';
          this.successMessage = null;
        }
      }
    });
  }
  
  updateCircuito(circuito: any): void {
    this.circuitoService.updateCircuito(circuito).subscribe({
      next: (response) => {
        this.loading = false;
        console.log('Respuesta completa del servidor:', response);
        
        // Considerar siempre como éxito
        this.successMessage = 'Circuito actualizado correctamente';
        this.errorMessage = null;
        
        setTimeout(() => {
          this.router.navigate(['/circuitos']);
        }, 1500);
      },
      error: (error) => {
        console.error('Error en la solicitud HTTP:', error);
        
        // Si sabemos que realmente se actualizó, podemos aún mostrar éxito
        if (error.status === 200) {
          this.successMessage = 'Circuito actualizado correctamente';
          this.errorMessage = null;
          setTimeout(() => {
            this.router.navigate(['/circuitos']);
          }, 1500);
        } else {
          this.loading = false;
          this.errorMessage = 'Error en la comunicación con el servidor';
          this.successMessage = null;
        }
      }
    });
  }
}