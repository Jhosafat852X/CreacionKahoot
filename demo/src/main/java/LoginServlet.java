import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

    private CapaDeNegocioMongoUsuarios CNMU;

    public void init(){
        CNMU = new CapaDeNegocioMongoUsuarios();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");


        if (CNMU.validarUsuarios(correo, contrasena)) {
            response.sendRedirect(request.getContextPath() + "/IngresoCorrectoo.jsp");
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
