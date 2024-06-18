<%-- Created by IntelliJ IDEA. User: giova Date: 16/06/2024 Time: 10:59 p. m. To change this template use File | Settings | File Templates. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Estado de la Sala</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            width: 90%;
            max-width: 800px;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
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
    <h2>Estado de la Sala</h2>
    <div id="estadoSala">
        Listos: <span id="listos">0</span>/<span id="total">0</span>
    </div>
    <button id="btnUnirse" class="btn">Unirse</button>
    <button id="btnListo" class="btn" disabled>Estoy Listo</button>
</div>

<script>
    var codigoSala = prompt("Introduce el código de la sala:");
    var socket = new WebSocket("ws://localhost:8080/EstadoSalaServer");
    var listo = false;

    socket.onopen = function() {
        socket.send(codigoSala + ":unirse");
        document.getElementById("btnUnirse").disabled = true;
        document.getElementById("btnListo").disabled = false;
    };

    socket.onmessage = function(event) {
        var message = event.data.split(":");
        if (message[0] === codigoSala) {
            var estado = JSON.parse(message[1]);
            document.getElementById("listos").textContent = estado.listos;
            document.getElementById("total").textContent = estado.total;
        }
    };

    document.getElementById("btnListo").addEventListener("click", function() {
        if (listo) {
            socket.send(codigoSala + ":noListo");
        } else {
            socket.send(codigoSala + ":listo");
        }
        listo = !listo;
        document.getElementById("btnListo").textContent = listo ? "Cancelar Listo" : "Estoy Listo";
    });
</script>

</body>
</html>
