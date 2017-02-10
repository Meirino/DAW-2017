#Introduccion
CuantoMeme (Nombre provisional) es una aplicación web donde los usuarios pueden subir viñetas, comentarlas y añadarlas a favoritos.
* Parte pública: Los usuarios podrán ver viñetas posteadas
* Parte privada: Los usuarios podrán registrarse para publicar viñetas, favoritearlas y dejar comentarios

Finalmente, los usuarios podrán comentar sus impresiones de las viñetas.

#Entidades
* Usuarios
* Viñetas
* Comentarios
* Favoritas
* Publicaciones

#Equipo

* Roberto	Brown Cabo
* Randall Barrientos Alva
* José Javier	Meiriño MIgens
* Omar Khanji Dobosh

#Diagrama de navegación
<img src="https://github.com/Meirino/DAW-2017/blob/master/img/diagrama/diagrama.png">

#Cambios respecto al template

Hemos usado el template SB-Admin (https://startbootstrap.com/template-overviews/sb-admin/) del que finalmente solo hemos mantenido la barra de navegación lateral y superior, aunque personalizada a nuestro diseño:

* Insertado el logo
* Modificado el botón de autenticación con Auth0
* Eliminado botones innecesarios
* Eliminado archivos superfluos de Javascript 
* Modificación para cambiar las categorías del dropdpwn lateral.
* Modificado la forma de autenticación y la hemos puesto basada en Auth0 para permitir una integración con Redes Sociales.
* Sobreescrito el CSS de todas la páginas para que se ajuste a nuestro diseño.

