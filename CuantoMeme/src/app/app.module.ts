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
import { TagViewComponent } from './TagViewComponent.component';
import { routing }  from './appRouting.module';
import { TagService } from './services/tag.service';
import { sideMenuComponent } from './side-menu.component';
import { LoginService } from './services/login.service';
import { PerfilComponent } from './perfilComponent.component';
import { TabsetComponent } from 'ngx-bootstrap';
import { loggedUserService } from './services/logged-user.service';
import { LikesComponent } from './likesComponent.component';


@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    BusquedaComponent,
    LoginComponent,
    SignUpComponent,
    listaVinetasComponent,
    vinetasDetalleComponent,
    TagViewComponent,
    sideMenuComponent,
    PerfilComponent,
    LikesComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing
  ],
  providers: [VinetasService, UsuarioService, TagService, LoginService, loggedUserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
