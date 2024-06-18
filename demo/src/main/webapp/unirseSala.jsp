<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Unirse a una Sala</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            flex-direction: column; /* Alineación vertical de los contenedores */
        }

        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            width: 90%;
            max-width: 800px;
            margin-bottom: 20px; /* Espacio inferior entre contenedores */
            margin-top: 20px; /* Espacio superior entre contenedores */
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }

        input[type="text"],
        input[type="number"],
        textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .btn {
            display: block;
            width: 100%;
            padding: 15px;
            font-size: 16px;
            color: #fff;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            text-align: center;
            text-decoration: none;
            margin-top: 10px;
        }

        .btn:hover {
            background-color: #0056b3;
        }

    </style>
</head>
<body>

<!-- CONTENEDOR DE BIENVENIDA AL USUARIO -->
<div class="container">
    <h2>Bienvenido, ${usuario.nombre}</h2>
</div>

<!-- CONTENEDOR PARA EL FORMULARIO DE UNIRSE A UNA SALA -->
<div class="container">
    <h2>Unirse a una Sala</h2>
    <form id="unirseSalaForm" action="EstadoSalaServlet" method="post">
        <div class="form-group">
            <label for="codigoSala">Código de la Sala:</label>
            <input type="text" id="codigoSala" name="codigoSala" required>
        </div>
        <input type="hidden" name="accion" value="unirse">
        <button type="submit" class="btn">Unirse a la Sala</button>
    </form>
</div>

<!-- ESPACIO ENTRE SECCIONES -->
<div style="height: 30px;"></div>

<!-- CONTENEDOR PARA MOSTRAR EL ESTADO DE LA SALA -->
<div class="container">
    <h2>Estado de la Sala</h2>
    <div id="estadoSala">
        Listos: <span id="listos">0</span>/<span id="totales">0</span> <!-- Cambio de 'total' a 'totales' -->
    </div>
    <button id="btnListo" class="btn">Estoy Listo</button>
</div>

<!-- Script para actualizar el estado de la sala -->
<script>
    document.getElementById("btnListo").addEventListener("click", function() {
        var codigoSala = document.getElementById("codigoSala").value;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "EstadoSalaServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send("codigoSala=" + codigoSala + "&accion=listo");

        xhr.onload = function() {
            if (xhr.status == 200) {
                actualizarEstadoSala();
            }
        };
    });

    function actualizarEstadoSala() {
        var codigoSala = document.getElementById("codigoSala").value;
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "EstadoSalaServlet?codigoSala=" + codigoSala, true);
        xhr.onload = function() {
            if (xhr.status == 200) {
                var estado = JSON.parse(xhr.responseText);
                document.getElementById("listos").textContent = estado.listos;
                document.getElementById("totales").textContent = estado.totales;
            }
        };
        xhr.send();
    }

    // Actualizar el estado de la sala cada 5 segundos
    setInterval(actualizarEstadoSala, 5000);
</script>

</body>
</html>
