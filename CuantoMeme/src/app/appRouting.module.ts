import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { IndexComponent } from './IndexComponent.component';
import { BusquedaComponent } from './busquedaComponent.component';
import { LoginComponent } from './login.component';
import { SignUpComponent } from './signup.component';
import { listaVinetasComponent } from './listaVinetas.component';
import { vinetasDetalleComponent } from './vinetasDetalle.component';
import { TagViewComponent } from './TagViewComponent.component';

const rutas: Routes = [
    { path: '', redirectTo: '/index', pathMatch: 'full' },
    { path: 'index', component: IndexComponent }, 
    { path: 'busqueda', component: BusquedaComponent },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignUpComponent },
    { path: 'vineta/:id', component: vinetasDetalleComponent },
    { path: 'tag/:nombre', component: TagViewComponent }
]

@NgModule({
  imports: [ RouterModule.forRoot(rutas) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}