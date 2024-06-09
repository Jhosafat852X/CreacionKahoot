import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/login")
public class LoginServlet extends HttpServlet{

    private CapaDeNegocioMongoUsuarios CNMU;

    public void init(){
        CNMU = new CapaDeNegocioMongoUsuarios();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        boolean validacionExitosa = CNMU.validarUsuarios(correo, contrasena);

        if (validacionExitosa) {
            // Redirigir al usuario a la página Home.jsp
            response.sendRedirect(request.getContextPath() + "/Vistas/Home.jsp");
        } else {
            // Mantener al usuario en la misma página
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}