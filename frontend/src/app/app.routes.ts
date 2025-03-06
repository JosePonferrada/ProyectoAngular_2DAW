import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';
import { unsavedChangesGuard } from './guards/unsaved-changes.guard';

// Escuderia components
import { EscuderiaListComponent } from './components/escuderia/escuderia-list/escuderia-list.component';
import { EscuderiaDetailComponent } from './components/escuderia/escuderia-detail/escuderia-detail.component';
import { EscuderiaFormComponent } from './components/escuderia/escuderia-form/escuderia-form.component';

// Piloto components
import { PilotoListComponent } from './components/piloto/piloto-list/piloto-list.component';
import { PilotoDetailComponent } from './components/piloto/piloto-detail/piloto-detail.component';
import { PilotoFormComponent } from './components/piloto/piloto-form/piloto-form.component';

// Auth components
import { LoginComponent } from './components/auth/login/login.component';

export const routes: Routes = [
  { path: '', redirectTo: '/escuderias', pathMatch: 'full' },
  
  // Escuderia routes
  { path: 'escuderias', component: EscuderiaListComponent },
  { 
    path: 'escuderias/new', 
    component: EscuderiaFormComponent,
    canActivate: [authGuard, adminGuard],
    canDeactivate: [unsavedChangesGuard]
  },
  { 
    path: 'escuderias/edit/:id', 
    component: EscuderiaFormComponent,
    canActivate: [authGuard, adminGuard],
    canDeactivate: [unsavedChangesGuard]
  },
  { path: 'escuderias/:id', component: EscuderiaDetailComponent },
  
  // Piloto routes
  { path: 'pilotos', component: PilotoListComponent },
  { 
    path: 'pilotos/new', 
    component: PilotoFormComponent,
    canActivate: [authGuard, adminGuard],
    canDeactivate: [unsavedChangesGuard]
  },
  { 
    path: 'pilotos/edit/:id', 
    component: PilotoFormComponent,
    canActivate: [authGuard, adminGuard],
    canDeactivate: [unsavedChangesGuard]
  },
  { path: 'pilotos/:id', component: PilotoDetailComponent },
  
  // Auth routes
  { path: 'login', component: LoginComponent },
  
  // Wildcard route
  { path: '**', redirectTo: '/escuderias' }
];