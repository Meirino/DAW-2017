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

@NgModule({
  declarations: [
    AppComponent,
    ComponentOne,
    ComponentTwo,
    LoginComponent,
    SignUpComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot([
      {
        path: '',
        redirectTo: '/comp1',
        pathMatch: 'full'
      },
      {
        path: 'comp1',
        component: ComponentOne
      }, 
      {
        path: 'comp2',
        component: ComponentTwo
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'signup',
        component: SignUpComponent
      }
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
