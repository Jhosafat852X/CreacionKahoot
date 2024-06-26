<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <title>Inicio de sesión Kahoot versión 2.0</title>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <br>
            <center>
                <h2>Bienvenido a Kahoot Version 2.1</h2>
            </center>
            <hr>
            <center>
                <h1>Inicio de sesión</h1>
            </center>

            <form action="login" method="post">
                <div class="mb-3">
                    <label for="email" class="form-label">Correo electrónico</label>
                    <input type="email" class="form-control" id="email" name="correo" aria-describedby="emailHelp">
                    <div id="emailHelp" class="form-text">Nunca compartiremos tu correo electrónico con nadie más.</div>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="contrasena">
                </div>
                <button type="submit" class="btn btn-primary">Iniciar sesión</button>
            </form>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJB55I+BZ7672tT1wbgapEEY/i6PD3Sw+9QdgpeLvth" crossorigin="anonymous"></script>
</body>
</html>
