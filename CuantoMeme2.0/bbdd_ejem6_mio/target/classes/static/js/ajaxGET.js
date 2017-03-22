$(document).ready(function () {
    $.ajax({
        type: "GET"
        , contentType: "application/json"
        , url: "/api/vinetas2/"
        , success: function (data) {
            console.log(data);
            var toAppend = '';
            for (var i = data.length - 1; i > 0; i--) {
                if (!data[i].tags) {
                    data[i].tags = [{
                        "id": 0
                        , "nombre": ' No existe tag '
                    }];
                }
                toAppend += "<div class='jumbotron'>" + "<p>Autor: <small><a href='/perfil/" + data[i].autor.id + "'>" + data[i].autor.username + "</a></small></p>" + "<h1>" + data[i].titulo + "</h1><img class='img-responsive' src='" + data[i].URL + "'>" + "<p>" + data[i].descripcion + "</p>" + "<div id='categoria'><small>Categoria: " + "<a href='/tag/" + data[i].tags.nombre + "'>" + data[i].tags.nombre + "</a>" + "</small></div>" + "<div id='social' class='row'>" + "<div class='col-md-1'>" + "<form action='/vineta/" + data[i].id + "'><button type='submit' class='btn btn-info btn-circle btn-lg'><i class='fa fa-comments' aria-hidden='true'></i></button><input type='hidden' name='_csrf' value='{{token}}'/></form></div>" + "<div class='col-md-1'><form action='/hacerfavorita/" + data[i].id + "'><button id='favs' type='submit' class='btn btn-warning btn-circle btn-lg'><span class='fa fa-star' aria-hidden='true'></span></button><input type='hidden' name='_csrf' value={{token}}/></form></div>" + "<div class='col-md-1'><form action='/likevineta/" + data[i].id + "'><button id='likes' type='submit' class='btn btn-success btn-circle btn-lg'><i class='fa fa-thumbs-up' aria-hidden='true'></i>" + "<div class='badge'>" + data[i].likes + "</div>" + "</button><input type='hidden' name='_csrf' value='{{token}}'/></form></div>" + "<div class='col-md-1'><form action='/dislikevineta/" + data[i].id + "'><button id='likes' type='submit' class='btn btn-danger btn-circle btn-lg'><i class='fa fa-thumbs-down' aria-hidden='true'></i>" + "<div class='badge'>" + data[i].dislikes + "</div>" + "</button><input type='hidden' name='_csrf' value='{{token}}'/></form></div>" + "</div>" + "</div>";
            }
            $("#anclaje").append(toAppend);
        }
    });
})