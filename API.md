# CuantoMeme API

A continuación pasamos a describir la API de nuestra aplicación, CuantoMeme, de acuerdo a los métodos privados de cada entidad y los públicos.

## Métodos públicos

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
   
