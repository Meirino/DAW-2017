# CuantoMeme API

A continuación pasamos a describir la API de nuestra aplicación, CuantoMeme, de acuerdo a los métodos privados de cada entidad y los públicos.

## Métodos públicos

### Búsquedas ("/api/busqueda")

- **Método:** GET "/vinetasTitulo/{nombre}"
  - Si existen viñetas que en el título contenga "nombre": _200 OK_, si no existe ninguna: _404 NOT FOUND_
  - **Parámetros:** nombre, en la URL.
  - **Devuelve:** Todas las viñetas que contengan "nombre" en el título.

- **Método:** GET "/vinetasUsuario/{nombre}"
  - Si existen viñetas que, cuyo autor contenga "nombre" en su nombre de usuario: _200 OK_, si no existe ninguna: _404 NOT FOUND_
  - **Parámetros:** nombre, en la URL.
  - **Devuelve:** Todas las viñetas que contengan "nombre" en el nombre del autor.
  
- **Método:** GET "/vinetasTag/{nombre}"
  - Si existen viñetas que en el tag contenga "nombre": _200 OK_, si no existe ninguna: _404 NOT FOUND_
  - **Parámetros:** nombre, en la URL.
  - **Devuelve:** Todas las viñetas que contengan "nombre" en el tag.

### Viñetas ("/api/vinetas")

- **Método:** GET "/"
  - Si existen viñetas: _200 OK_, si no existe ninguna: _404 NOT FOUND_
  - **Parámetros:** Ninguno
  - **Devuelve:** La página 0 devuelve una lista con las 20 primeras viñetas, la página 1 devuelve las 20 siguientes, etc etc...

- **Método:** GET "/{id}" 
    - Si existe: _200 OK_, si no existe: _404 NOT FOUND_  
    - **Parámetros:** ID de la viñeta
    - **Devuelve:** Si existe, la viñeta con ese ID, sino un objeto null
    
### Comentarios ("/api/comentarios")

- **Método:** GET "/"
    - Si existen comentarios: _200 OK_, si no hay comentarios _404 NOT FOUND_
    - **Parámetros:** Ninguno
    - **Devuelve:** Una lista con todos los comentarios
    
- **Método:** GET "/{id}"
    - Si existe: _200 OK_, si no existe: _404 NOT FOUND_
    - **Parámetros:** ID del comentario
    - **Devuelve:** El comentario con ese mismo ID
    
### Tags ("/api/tags")

- **Método:** GET "/"
    - Si existen tags: _200 OK_, si no hay tags _404 NOT FOUND_
    - **Parámetros:** Ninguno
    - **Devuelve:** Una lista con todos los tags
    
- **Método:** GET "/{id}"
    - Si existe: _200 OK_, si no existe: _404 NOT FOUND_
    - **Parámetros:** ID del tag
    - **Devuelve:** El tag con ese mismo ID
    
    
### Usuarios ("/api/users")

- **Método:** GET "/"
    - Si existen usuarios: _200 OK_, si no: _404 NOT FOUND_
    - **Parámetros:** Ninguno
    - **Devuelve:** Una lista con todos los usuarios
    
- **Método:** GET "/{id}"
    - Si existe: _200 OK_, si no existe: _404 NOT FOUND_
    - **Parámetros:** ID del usuario
    - **Devuelve:** El usuario con ese mismo ID
    
- **Método:** POST "/signup"
    - Registra a un usuario
    - **Parámetros:** El nombre de usuario (Campo "username"), email (Campo "email"), contraseña (Campo "pass")
    - **Devuelve:** El usuario registrado
    
- **Método:** GET "/logIn"
    - Si existe: _200 OK_, si no existe: _UNAUTHORIZED_
    - **Parámetros:** Las credenciales del usuario (Nombre de usuario y contraseña)
    - **Devuelve:** Un booleano "true"
    
- **Método:** GET "/logOut"
    - Si existe: _200 OK_, si no existe: _UNAUTHORIZED_
    - **Parámetros:** Las credenciales del usuario (Nombre de usuario y contraseña)
    - **Devuelve:** El usuario loggeado
    
## Métodos privados

### Viñetas ("/api/vinetas")

- **Método:** POST "/"
   - **Privilegios necesarios:** USUARIO o ADMIN
   - En éxito _201 CREATED_
   - **Parámetros:** Título (En un campo "titulo"), descripción (Campo "desc"), tag (Campo "tags") y archivo (Campo "file")
   - **Devuelve:** La viñeta creada

- **Método:** DELETE "/{id}"
   - **Privilegios necesarios:** USUARIO o ADMIN (El único usuario capaz de borrarla es su autor. Esto se comprueba en el método)
   - Si existe, y el usuario es el autor: _200 OK_, en caso contrario: _403 FORBIDDEN_
   - **Parámetros:** ID de la viñeta a borrar
   - **Devuelve:** Si ha tenido éxito, la viñeta borrada, sino, nada
   
- **Métodos** PUT "like/{id}" / PUT "dislike/{id}" / PUT "favorite/{id}"
   - **Privilegios necesarios:** USUARIO o ADMIN
   - Si la viñeta con el id existe: _200 OK_, en caso contrario: _403 FORBIDDEN_
   - **Parámetros:** ID de la viñeta
   - **Devuelve:** Si ha tenido éxito, la viñeta, sino, nada
   
### Comentarios ("/api/comentarios")  
   
- **Método:** PUT "/{ID}"
   - **Privilegios necesarios:** USUARIO o ADMIN (El único usuario capaz de borrarla es su autor. Esto se comprueba en el método)
   - Si existe, y el usuario es el autor: _200 OK_, en caso contrario: _304 NOT MODIFIED_
   - **Parámetros:** El nuevo texto (En un campo "texto")
   - **Devuelve:** Si ha tenido éxito, el comentario modificado
   
- **Método:** POST "vineta/{ID}"
   - **Privilegios necesarios:** USUARIO o ADMIN
   - Si existe la viñeta: _201 CREATED_, en caso contrario: _404 NOT FOUND_
   - **Parámetros:** El texto del comentario (En un campo "texto") y el ID de la viñeta
   - **Devuelve:** Si ha tenido éxito, el comentario creado
   
- **Método:** DELETE "/{id}"
   - **Privilegios necesarios:** USUARIO o ADMIN (El único usuario capaz de borrarla es su autor. Esto se comprueba en el método)
   - Si existe, y el usuario es el autor: _200 OK_, en caso contrario: _404 NOT FOUND_
   - **Parámetros:** El ID del comentario
   - **Devuelve:** Si ha tenido éxito, el comentario borrado
   
   ### Usuarios ("/api/users")
   
- **Método:** DELETE "/{id}"
   - **Privilegios necesarios:** ADMIN
   - Si existe: _200 OK_, en caso contrario: _404 NOT FOUND_
   - **Parámetros:** El ID del usuario
   - **Devuelve:** Nada
   
   
- **Método:** PUT "/follow/{id}"
   - **Privilegios necesarios:** USUARIO o ADMIN
   - Si el usuario existe: _200 OK_, en caso contrario: _404 NOT FOUND_
   - **Parámetros:** El ID del usuario
   - **Devuelve:** El usuario que está haciendo la petición
   
- **Método:** PUT "/{id}"
   - **Privilegios necesarios:** USUARIO o ADMIN
   - Si el usuario existe: _200 OK_, en caso contrario: _UNAUTHORIZED_
   - **Parámetros:** El ID del usuario en la URL, un nombre (Campo "nombre") y un email (Campo "email")
   - **Devuelve:** El usuario modificado
   
- **Método:** PUT "/unfollow/{id}"
   - **Privilegios necesarios:** USUARIO o ADMIN
   - Si el usuario existe: _200 OK_, en caso contrario: _404 NOT FOUND_
   - **Parámetros:** El ID del usuario
   - **Devuelve:** El usuario que está haciendo la petición
   
- **Método:** GET "/timline}"
   - **Privilegios necesarios:** USUARIO
   - Si el usuario tiene viñetas en su timeline: _200 OK_, en caso contrario: _404 NOT FOUND_
   - **Parámetros:** Ninguno
   - **Devuelve:** Las viñetas de los usuarios a los que sigue
   
- **Método:** PUT "/avatar"
   - **Privilegios necesarios:** USUARIO o ADMIN
   - Si se ha podido subir el avatar: _200 OK_, si el avatar pesa más de 1MB: _BAD REQUEST_, si el usuario no se ha logeado: _UNAUTHORIZED_
   - **Parámetros:** Una imagen (Camnpo "file")
   - **Devuelve:** El usuario que está haciendo la petición
