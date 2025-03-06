import { CanDeactivateFn } from '@angular/router';

export interface ComponentWithUnsavedChanges {
  hasUnsavedChanges(): boolean;
}

export const unsavedChangesGuard: CanDeactivateFn<ComponentWithUnsavedChanges> = 
  (component) => {
    if (component.hasUnsavedChanges()) {
      return confirm('¿Tienes cambios sin guardar. ¿Estás seguro de que quieres salir?');
    }
    return true;
  };