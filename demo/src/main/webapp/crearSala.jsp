<%--
  Created by IntelliJ IDEA.
  User: giova
  Date: 16/06/2024
  Time: 12:47 p. m.
  To change this template use File | Settings | File Templates.
--%><%@ page contentType="text/html;charset=UTF-8" language="java" %><!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Preguntas</title>
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
        .answers-group {
            margin-bottom: 10px;
            display: flex;
            align-items: center;
        }
        .answers-group input[type="text"] {
            flex: 1;
            padding: 10px;
            margin-right: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .answers-group input[type="radio"] {
            margin-left: 10px;
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
    <h2>Crear Sala</h2>
    <form id="salaForm" action="CrearSalaServlet" method="post">
        <div class="form-group">
            <label for="nombreSala">Nombre de la Sala:</label>
            <input type="text" id="nombreSala" name="nombreSala" required>
        </div>
        <div class="form-group">
            <label for="codigoSala">Código de la Sala:</label>
            <input type="text" id="codigoSala" name="codigoSala" required>
        </div>
        <input type="hidden" name="creadorId" value="5cbe857e3621c2c7a7e2d10f">
        <button type="submit" class="btn">Guardar Sala</button>
    </form>
</div>
</body>
</html>