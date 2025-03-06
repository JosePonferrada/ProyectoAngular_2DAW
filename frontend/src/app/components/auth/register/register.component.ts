import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  loading = false;
  submitted = false;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, {
      validators: this.mustMatch('password', 'confirmPassword')
    });
  }

  // Getter para acceder fácilmente a los controles del formulario
  get f() { return this.registerForm.controls; }

  mustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];

      if (matchingControl.errors && !matchingControl.errors['mustMatch']) {
        // Devuelve si otro validador ya ha encontrado un error
        return;
      }

      // Establece error si las contraseñas no coinciden
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({ mustMatch: true });
      } else {
        matchingControl.setErrors(null);
      }
    };
  }

  onSubmit() {
    this.submitted = true;
    
    if (this.registerForm.invalid) {
      return;
    }
  
    this.loading = true;
    this.errorMessage = null;
    this.successMessage = null;
    
    const { username, email, password } = this.registerForm.value;
    
    this.authService.register(username, email, password).subscribe({
      next: (response) => {
        console.log('Respuesta del registro:', response);
        
        if (response.resultado === 'ok' || response.result === 'ok') {
          // Mostrar mensaje breve
          this.successMessage = 'Usuario registrado correctamente';
          
          // Hacer login automático
          this.loginAfterRegistration(username, password);
        } else {
          this.loading = false;
          this.errorMessage = response.mensaje || 'Error al registrar usuario';
        }
      },
      error: (error) => {
        this.loading = false;
        console.error('Error en la solicitud:', error);
        this.errorMessage = error.error?.mensaje || 'Error de conexión con el servidor';
      }
    });
  }
  
  // Método para hacer login automáticamente después del registro
  loginAfterRegistration(username: string, password: string) {
    this.authService.login(username, password).subscribe({
      next: (response) => {
        this.loading = false;
        
        if (response.resultado === 'ok' || response.token) {
          // Navegar a la página principal/dashboard
          this.router.navigate(['/']);
        } else {
          // Si hay algún problema con el login automático
          this.successMessage = 'Usuario registrado correctamente, pero no se pudo iniciar sesión automáticamente';
          this.errorMessage = 'Por favor, inicie sesión manualmente';
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        }
      },
      error: (error) => {
        this.loading = false;
        this.successMessage = 'Usuario registrado correctamente, pero no se pudo iniciar sesión automáticamente';
        this.errorMessage = 'Por favor, inicie sesión manualmente';
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      }
    });
  }
}