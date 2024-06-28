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
            flex-direction: column;
        }

        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            width: 90%;
            max-width: 800px;
            margin-bottom: 20px;
            margin-top: 20px;
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

<div class="container">
    <h2>Bienvenido, Usuario</h2>
</div>

<div class="container">
    <h2>Unirse a una Sala</h2>
    <form id="unirseSalaForm">
        <div class="form-group">
            <label for="codigoSala">Código de la Sala:</label>
            <input type="text" id="codigoSala" name="codigoSala" required>
        </div>
        <button type="submit" id="btnUnirse" class="btn">Unirse a la Sala</button>
    </form>
</div>

<div style="height: 30px;"></div>

<div class="container">
    <h2>Estado de la Sala</h2>
    <div id="estadoSala">
        Listos: <span id="listos">0</span>/<span id="totales">0</span>
    </div>
    <button id="btnListo" class="btn">Estoy Listo</button>
</div>

<script>
    var socket = new WebSocket("ws://localhost:8080/demo_war_exploded/EstadoSalaWebSocket");
    var codigoSala = "";

    socket.onmessage = function(event) {
        var message = JSON.parse(event.data);
        if (message.codigoSala === codigoSala) {
            document.getElementById("listos").textContent = message.estado.listos;
            document.getElementById("totales").textContent = message.estado.totales;
            if (message.estado.isFull) {
                window.location.href = "/demo_war_exploded/game?codigoSala=" + codigoSala;
            }
        }
    };

    document.getElementById("btnUnirse").addEventListener("click", function(event) {
        codigoSala = document.getElementById("codigoSala").value;
        socket.send(JSON.stringify({codigoSala: codigoSala, accion: "unirse"}));
        event.preventDefault();
    });

    document.getElementById("btnListo").addEventListener("click", function() {
        var accion = document.getElementById("btnListo").textContent === "Estoy Listo" ? "listo" : "noListo";
        socket.send(JSON.stringify({codigoSala: codigoSala, accion: accion}));
    });
</script>

</body>
</html>
