import Beans.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
            String contraseñaAlmacenada = userDocument.getString("contraseña");

            if (contrasena.equals(contraseñaAlmacenada)) {
                Usuario usuario = new Usuario(id, correo, contraseñaAlmacenada, nombre);

                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);

                response.sendRedirect(request.getContextPath() + "/Vistas/Home.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
