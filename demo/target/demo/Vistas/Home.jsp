<%@ page import="Beans.Usuario" %><%--
  Created by IntelliJ IDEA.
  User: giova
  Date: 09/06/2024
  Time: 01:02 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio</title>
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
            text-align: center;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 90%; /* Ancho del contenedor */
            max-width: 600px; /* Ancho máximo para mantener el diseño */
            margin: 10px auto; /* Centrar y espacio entre contenedores */
        }
        .btn {
            display: inline-block;
            margin: 10px;
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            text-decoration: none;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>

</head>
<body>

<!-- CONTENEDOR DE BIENVENIDA AL USUARIO -->
<div class="container">
    <%-- Verificar si el usuario está en sesión --%>
    <% if (session.getAttribute("usuario") != null) { %>
    <%-- Obtener y mostrar el nombre del usuario --%>
    <h2>Bienvenido, <%= ((Usuario) session.getAttribute("usuario")).getNombre() %></h2>
    <% } %>
</div>

<!-- CONTENEDOR PARA LOS BOTONES -->
<div class="container">
    <a href="<%= request.getContextPath() %>/crearSala.jsp" class="btn">Crear Sala</a>
    <a href="<%= request.getContextPath() %>/unirseSala.jsp" class="btn">Unirse a una Sala</a>
</div>

</body>
</html>
