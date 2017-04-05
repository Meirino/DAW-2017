import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule }   from '@angular/router';

import { AppComponent } from './app.component';
import { ComponentOne } from './component1.component';
import { ComponentTwo } from './component2.component';
import { LoginComponent } from './login.component';
import { SignUpComponent } from './signup.component';
import { listaVinetasComponent } from './listaVinetas.component';

import { AppRoutingModule } from './appRouting.module';

@NgModule({
  declarations: [
    AppComponent,
    ComponentOne,
    ComponentTwo,
    LoginComponent,
    SignUpComponent,
    listaVinetasComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
