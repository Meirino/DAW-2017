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
import { PerfilComponent } from './perfilComponent.component';
import { LikesComponent } from './likesComponent.component';
import { FavoritesComponent } from './favoritesComponent.component';
import { DislikesComponent } from './dislikesComponent.component';
const rutas: Routes = [
    { path: '', redirectTo: '/index', pathMatch: 'full' },
    { path: 'index', component: IndexComponent }, 
    { path: 'busqueda', component: BusquedaComponent },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignUpComponent },
    { path: 'vineta/:id', component: vinetasDetalleComponent },
    { path: 'tag/:nombre', component: TagViewComponent },
    { path: 'perfil/:id', component: PerfilComponent },
    { path: 'likes', component: LikesComponent },
    { path: 'favorites', component: FavoritesComponent },
    { path: 'dislikes', component: DislikesComponent }
]

export const routing = RouterModule.forRoot(rutas);