import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule }   from '@angular/router';

import { AppComponent } from './app.component';
import { IndexComponent } from './IndexComponent.component';
import { BusquedaComponent } from './busquedaComponent.component';
import { LoginComponent } from './login.component';
import { SignUpComponent } from './signup.component';
import { listaVinetasComponent } from './listaVinetas.component';
import { vinetasDetalleComponent } from './vinetasDetalle.component';
import { VinetasService } from './services/vinetas.service';
import { UsuarioService } from './services/usuarios.service';
import { AppRoutingModule } from './appRouting.module';

@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    BusquedaComponent,
    LoginComponent,
    SignUpComponent,
    listaVinetasComponent,
    vinetasDetalleComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [VinetasService, UsuarioService],
  bootstrap: [AppComponent]
})
export class AppModule { }
