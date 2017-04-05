import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { ComponentOne } from './component1.component';
import { ComponentTwo } from './component2.component';
import { LoginComponent } from './login.component';
import { SignUpComponent } from './signup.component';
import { listaVinetasComponent } from './listaVinetas.component';
import { vinetasDetalleComponent } from './vinetasDetalle.component';

const rutas: Routes = [
    { path: '', redirectTo: '/comp1', pathMatch: 'full' },
    { path: 'comp1', component: ComponentOne }, 
    { path: 'comp2', component: ComponentTwo },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignUpComponent },
    { path: 'vineta/:id', component: vinetasDetalleComponent }
]

@NgModule({
  imports: [ RouterModule.forRoot(rutas) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}