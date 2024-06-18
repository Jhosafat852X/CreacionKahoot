<%--
  Created by IntelliJ IDEA.
  User: giova
  Date: 16/06/2024
  Time: 06:38 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Crear Pregunta</title>
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
        input[type="file"] {
            display: block;
            margin-top: 10px;
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
    <h2>Crear Pregunta</h2>
    <form id="preguntaForm" action="CrearPreguntaServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="salaId" value="${param.salaId}">

        <div class="form-group">
            <label for="pregunta">Pregunta:</label>
            <input type="text" id="pregunta" name="pregunta" required>
        </div>
        <div class="form-group">
            <label for="respuesta1">Respuesta 1:</label>
            <input type="text" id="respuesta1" name="respuesta1" required>
            <input type="radio" name="respuestaCorrecta" value="respuesta1" required>
        </div>
        <div class="form-group">
            <label for="respuesta2">Respuesta 2:</label>
            <input type="text" id="respuesta2" name="respuesta2" required>
            <input type="radio" name="respuestaCorrecta" value="respuesta2" required>
        </div>
        <div class="form-group">
            <label for="respuesta3">Respuesta 3:</label>
            <input type="text" id="respuesta3" name="respuesta3" required>
            <input type="radio" name="respuestaCorrecta" value="respuesta3" required>
        </div>
        <div class="form-group">
            <label for="respuesta4">Respuesta 4:</label>
            <input type="text" id="respuesta4" name="respuesta4" required>
            <input type="radio" name="respuestaCorrecta" value="respuesta4" required>
        </div>
        <div class="form-group">
            <label for="tiempo">Tiempo por pregunta (segundos):</label>
            <input type="number" id="tiempo" name="tiempo" min="5" max="120" value="30" required>
        </div>
        <div class="form-group">
            <label for="puntos">Puntos por pregunta:</label>
            <input type="number" id="puntos" name="puntos" min="1" max="1000" value="100" required>
        </div>
        <div class="form-group">
            <label for="archivo">Subir Archivo:</label>
            <input type="file" id="archivo" name="archivo">
        </div>
        <button type="submit" class="btn">Guardar Pregunta</button>
    </form>
</div>
</body>
</html>
