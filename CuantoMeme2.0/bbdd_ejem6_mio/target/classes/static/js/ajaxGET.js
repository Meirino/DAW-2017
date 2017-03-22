var paginaActual = 0;

$(document).ready(function () {
    cargarPagina(paginaActual);
})

function cargarPagina(num) {
    $.ajax({
        type: "GET"
        , beforeSend: function() {
            $('#loading').html("<img src='/spinner.gif'/>").show();
            $('#loading').html("<img src='/spinner.gif'/>");
        }
        , contentType: "application/json"
        , url: "/api/vinetaspage/?page="+num
        , success: function (data) {
            console.log(data);
            var toAppend = '';
            for (var i = 0; i < data.numberOfElements; i++) {
                toAppend += "<div class='jumbotron'>" + "<p>Autor: <small><a href='/perfil/" + "undefined" + "'>" + "undefined" + "</a></small></p>" + "<h1>" + data.content[i].titulo + "</h1><img class='img-responsive' src='" + data.content[i].url + "'>" + "<p>" + data.content[i].descripcion + "</p>" + "<div id='categoria'><small>Categoria: " + "<a href='/tag/" + "undefined" + "'>" + "undefined" + "</a>" + "</small></div>" + "<div id='social' class='row'>" + "<div class='col-md-1'>" + "<form action='/vineta/" + data.content[i].id + "'><button type='submit' class='btn btn-info btn-circle btn-lg'><i class='fa fa-comments' aria-hidden='true'></i></button><input type='hidden' name='_csrf' value='{{token}}'/></form></div>" + "<div class='col-md-1'><form action='/hacerfavorita/" + data.content[i].id + "'><button id='favs' type='submit' class='btn btn-warning btn-circle btn-lg'><span class='fa fa-star' aria-hidden='true'></span></button><input type='hidden' name='_csrf' value={{token}}/></form></div>" + "<div class='col-md-1'><form action='/likevineta/" + data.content[i].id + "'><button id='likes' type='submit' class='btn btn-success btn-circle btn-lg'><i class='fa fa-thumbs-up' aria-hidden='true'></i>" + "<div class='badge'>" + data.content[i].likes + "</div>" + "</button><input type='hidden' name='_csrf' value='{{token}}'/></form></div>" + "<div class='col-md-1'><form action='/dislikevineta/" + data.content[i].id + "'><button id='likes' type='submit' class='btn btn-danger btn-circle btn-lg'><i class='fa fa-thumbs-down' aria-hidden='true'></i>" + "<div class='badge'>" + data.content[i].dislikes + "</div>" + "</button><input type='hidden' name='_csrf' value='{{token}}'/></form></div>" + "</div>" + "</div>";
            }
            paginaActual = paginaActual+1;
            toAppend += "<div id='pagina" + paginaActual + "'></div>"
            $("#pagina" + (paginaActual-1)).append(toAppend);
            $('#loading').html("").hide();
        }
    });
}