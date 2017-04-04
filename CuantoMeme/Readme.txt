Readme, Angular 2 |
------------------

Este archivo es para ir documentando el proyecto de Angular 2.

***********
/cuantomeme
***********

* Angular-cli.json tiene un atributo llamado "styles", que es un array de
archivos .css, que contiene los archivos CSS que se van a aplicar globalmente
a toda la aplicación.

* Package.json es básicamente el pom.xml de Angular 2. Contiene todas las dependencias.
Estas dependencias se instalan en "node_modules" al ejecutar el comando "npm install".

* La carpeta "e2e" es de testing. No nos sirve para nada.

***********
/src
***********

* La carpeta "app" contiene las clases de la aplicación.

* La carpeta "assets" es parecida a la "static" de Spring Boot.
Sirve para añadir todo el contenido estática que se empaquetará junto
a la app. Es decir: CSS, imágenes, etc...

* main.ts es la clase principal de la aplicación. Es el application.java de Angular 2.

* Index.html es el HTML principal de la página. No tocar.

* Styles.css es el CSS principal de la página y se aplica globalmente.
Está vacío.

***********
/app
***********

* App.component.ts es el componente principal de la aplicación y que contiene
la barra de navegación de la página. 

* App.module.ts es la clase donde se tienen que declarar los componentes para que funcionen.

####################################
Guías de estilo
####################################

* Las clases tipo "viñeta", "usuario", "comentario", "tag" irán en la carpeta "classes"
y se anotarán como "[loquesea].class.ts".

* Los componentes irán anotados como "[loquesea].component.ts", y luego su nombre de componente será
"[loquesea]Component".

* Los templates y CSS estarán en la carpeta "templates". Los templates se llamarán "[loquesea].template.html",
mientras que los archivos CSS son libres de llamarse como se quiera.

* Los servicios igualmente irán en la carpeta "services" y se llamarán "[loquesea].service.ts".