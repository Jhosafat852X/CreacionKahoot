import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private CapaDeNegocioMongoUsuarios CNMU;

    public void init() {
        CNMU = new CapaDeNegocioMongoUsuarios();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        Document userDocument = CNMU.obtenerUsuario(correo);

        if (userDocument != null) {
            String id = userDocument.getString("_id");
            String nombre = userDocument.getString("nombre");
            String contraseña = userDocument.getString("contraseña"); // Debes evitar almacenar contraseñas en texto plano

            // Crear un objeto Usuario si es necesario
            Usuario usuario = new Usuario(id, correo, contraseña, nombre);

            // Guardar el objeto Usuario en la sesión
            request.getSession().setAttribute("usuario", usuario);

            // Redirigir al usuario a la página Home.jsp
            response.sendRedirect(request.getContextPath() + "/Vistas/Home.jsp");
        } else {
            // Mantener al usuario en la misma página de inicio de sesión
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

