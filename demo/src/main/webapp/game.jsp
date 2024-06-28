<%--
  Created by IntelliJ IDEA.
  User: giova
  Date: 27/06/2024
  Time: 05:39 p. m.
  To change this template use File | Settings | File Templates.
--%><!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Juego</title>
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
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        .question-container {
            margin-bottom: 20px;
        }

        .question {
            font-size: 20px;
            margin-bottom: 10px;
        }

        .answer {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
            cursor: pointer;
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
    <h2>Juego Iniciado</h2>
    <div id="pregunta-container" class="question-container">
        <div id="pregunta" class="question"></div>
        <div id="respuestas" class="answers"></div>
    </div>
    <button id="btnSiguiente" class="btn" >Siguiente Pregunta</button>
    <button id="btnEnviarMensaje" class="btn">Enviar Mensaje al Servidor</button>
</div>
<script>
    var socket = new WebSocket("ws://localhost:8080/demo_war_exploded/JuegoWebSocket");
    var codigoSala = "<%= request.getParameter("codigoSala") %>";
    var usuarioId = "usuario_id";

    socket.onmessage = function(event) {
        var message = JSON.parse(event.data);
        console.log("Mensaje recibido:", message);

        if (message.accion === "redireccionar") {
            // Redirigir a la página especificada en el mensaje
            window.location.href = message.url;
        } else if (message.accion === "mostrarGanador") {
            // Mostrar el nombre del ganador en una alerta
            alert("¡Tenemos un ganador! " );
        } else if (message.pregunta) {
            mostrarPregunta(message.pregunta);
        }
    };





    function mostrarPregunta(pregunta) {
        console.log("Mostrando pregunta:", pregunta);

        if (pregunta) {
            document.getElementById("pregunta").textContent = pregunta.texto;
            var respuestasContainer = document.getElementById("respuestas");
            respuestasContainer.innerHTML = "";
            pregunta.respuestas.forEach(function(respuesta) {
                var answerElement = document.createElement("div");
                answerElement.textContent = respuesta;
                answerElement.className = "answer";
                answerElement.onclick = function() {
                    enviarRespuesta(respuesta);  // Enviar el texto de la respuesta seleccionada
                };
                respuestasContainer.appendChild(answerElement);
            });
        }
    }

    function mostrarGanador(ganador) {
        console.log("El ganador es:", ganador);
        // Actualiza el contenido en el DOM para mostrar el nombre del ganador
        var ganadorElement = document.getElementById("ganador");
        ganadorElement.textContent = "El ganador es: " + ganador;
    }

    function enviarRespuesta(respuestaSeleccionada) {
        var tiempoRespuesta = 0;
        socket.send(JSON.stringify({
            codigoSala: codigoSala,
            accion: "respuesta",
            usuarioId: usuarioId,
            respuestaSeleccionada: respuestaSeleccionada,
            tiempoRespuesta: tiempoRespuesta
        }));
    }

    document.getElementById("btnSiguiente").addEventListener("click", function() {
        socket.send(JSON.stringify({
            codigoSala: codigoSala,
            accion: "siguiente"
        }));
    });

    document.getElementById("btnEnviarMensaje").addEventListener("click", function() {
        var mensaje = {
            codigoSala: codigoSala,
            accion: "mensajeCliente",
            contenido: "Mensaje desde el cliente al servidor"
        };
        socket.send(JSON.stringify(mensaje));
        console.log("Mensaje enviado al servidor:", mensaje);
    });
</script>

</body>
</html>